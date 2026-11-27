package easvprojects.mytunes.dal.db;
import easvprojects.mytunes.dal.DataAccessException;
import easvprojects.mytunes.dal.IDBConnector;

import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.be.Playlist;
import easvprojects.mytunes.be.Song;
import easvprojects.mytunes.dal.IPlaylistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Positions on a playlist start at 0, so they match the index of a song
 * in the list returned by {@link #getSongsOnPlaylist(Playlist)}.
 */
public class PlaylistDAO implements IPlaylistDAO {

    // SQL queries against the playlist and playlist_song tables (see schema.sql)
    private static final String INSERT_PLAYLIST = "INSERT INTO playlist (name, description) VALUES (?, ?)";
    private static final String INSERT_SONG_AT_END =
            "INSERT INTO playlist_song (playlist_id, song_id, position) " +
            "SELECT ?, ?, COALESCE(MAX(position) + 1, 0) FROM playlist_song WHERE playlist_id = ?";

    private static final String SELECT_ALL_PLAYLISTS = "SELECT id, name, description FROM playlist ORDER BY name";
    private static final String SELECT_PLAYLISTS_BY_NAME =
            "SELECT id, name, description FROM playlist WHERE name LIKE '%' || ? || '%' ORDER BY name";
    private static final String SELECT_PLAYLISTS_BY_GENRE =
            "SELECT id, name, description FROM playlist WHERE id IN (" +
            "SELECT ps.playlist_id FROM playlist_song ps JOIN song_genre sg ON sg.song_id = ps.song_id WHERE sg.genre_id = ?" +
            ") ORDER BY name";
    private static final String SELECT_PLAYLISTS_BY_SONG =
            "SELECT id, name, description FROM playlist WHERE id IN (" +
            "SELECT playlist_id FROM playlist_song WHERE song_id = ?" +
            ") ORDER BY name";
    private static final String SELECT_ENTRY_AT_POSITION =
            "SELECT id FROM playlist_song WHERE playlist_id = ? AND position = ?";

    private static final String UPDATE_PLAYLIST = "UPDATE playlist SET name = ?, description = ? WHERE id = ?";
    private static final String UPDATE_ENTRY_POSITION = "UPDATE playlist_song SET position = ? WHERE id = ?";
    // Moves entries in a range one place towards the start (fills a gap)
    private static final String SHIFT_ENTRIES_UP =
            "UPDATE playlist_song SET position = position - 1 WHERE playlist_id = ? AND position > ? AND position <= ?";
    // Moves entries in a range one place towards the end (makes a gap)
    private static final String SHIFT_ENTRIES_DOWN =
            "UPDATE playlist_song SET position = position + 1 WHERE playlist_id = ? AND position >= ? AND position < ?";

    private static final String DELETE_ENTRY = "DELETE FROM playlist_song WHERE id = ?";

    private final IDBConnector dbConnector;
    private final SongDAO songDAO;

    /**
     * Creates a PlaylistDAO that uses the given database connector.
     *
     * @param dbConnector the connector used to open database connections
     */
    public PlaylistDAO(IDBConnector dbConnector) {
        this.dbConnector = dbConnector;
        this.songDAO = new SongDAO(dbConnector);
    }

    @Override
    public void insertPlaylist(Playlist playlist) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_PLAYLIST)) {
            ps.setString(1, playlist.getName());
            ps.setString(2, playlist.getDescription());
            ps.executeUpdate();
        }
    }

    @Override
    public void addSongToPlaylist(Playlist playlist, Song song) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_SONG_AT_END)) {
            ps.setInt(1, playlist.getId());
            ps.setInt(2, song.getId());
            ps.setInt(3, playlist.getId());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Could not add the song to the playlist", e);
        }
    }

    @Override
    public List<Playlist> getPlaylists() throws Exception {
        return readPlaylists(SELECT_ALL_PLAYLISTS, null);
    }

    @Override
    public List<Playlist> getPlaylistByName(String name) throws Exception {
        return readPlaylists(SELECT_PLAYLISTS_BY_NAME, name);
    }

    @Override
    public List<Playlist> getPlaylistsByGenre(Genre genre) throws Exception {
        return readPlaylists(SELECT_PLAYLISTS_BY_GENRE, genre.getId());
    }

    @Override
    public List<Playlist> getPlaylistsBySong(Song song) throws Exception {
        return readPlaylists(SELECT_PLAYLISTS_BY_SONG, song.getId());
    }

    @Override
    public List<Song> getSongsOnPlaylist(Playlist playlist) throws Exception {
        return songDAO.getSongsOnPlaylist(playlist.getId());
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE_PLAYLIST)) {
            ps.setString(1, playlist.getName());
            ps.setString(2, playlist.getDescription());
            ps.setInt(3, playlist.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not udpate the playlist", e);
        }
    }

    @Override
    public void removeSongFromPlaylist(Playlist playlist, int position) throws Exception {
        try (Connection c = dbConnector.getConnection()) {
            c.setAutoCommit(false);
            try {
                int entryId = findEntry(c, playlist.getId(), position);
                try (PreparedStatement ps = c.prepareStatement(DELETE_ENTRY)) {
                    ps.setInt(1, entryId);
                    ps.executeUpdate();
                }
                // Close the gap: everything after the removed entry moves up one place
                shift(c, SHIFT_ENTRIES_UP, playlist.getId(), position, Integer.MAX_VALUE);
                c.commit();
            }catch (Exception e) {
                c.rollback();
                throw new DataAccessException("Could not remove the song from the playlist", e);
            }
        }
    }

    @Override
    public void moveSong(Playlist playlist, int fromPosition, int toPosition) throws Exception {
        if (fromPosition == toPosition) return;

        try (Connection c = dbConnector.getConnection()) {
            c.setAutoCommit(false);
            try {
                int entryId = findEntry(c, playlist.getId(), fromPosition);
                if (fromPosition < toPosition) {
                    // Moving towards the end: the entries it passes move up one place
                    shift(c, SHIFT_ENTRIES_UP, playlist.getId(), fromPosition, toPosition);
                } else {
                    // Moving towards the start: the entries it passes move down one place
                    shift(c, SHIFT_ENTRIES_DOWN, playlist.getId(), toPosition, fromPosition);
                }
                try (PreparedStatement ps = c.prepareStatement(UPDATE_ENTRY_POSITION)) {
                    ps.setInt(1, toPosition);
                    ps.setInt(2, entryId);
                    ps.executeUpdate();
                }
                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw new DataAccessException("Could not move the song", e);
            }
        }
    }

    /**
     * Runs a playlist query and loads each playlist's songs.
     *
     * @param sql       one of the SELECT_PLAYLISTS queries
     * @param parameter the value for the query's {@code ?}, or {@code null} if it has none
     */
    private List<Playlist> readPlaylists(String sql, Object parameter) throws Exception {
        List<Playlist> playlists = new ArrayList<>();
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (parameter != null) ps.setObject(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    playlists.add(new Playlist(id, rs.getString("name"), rs.getString("description"),
                            songDAO.getSongsOnPlaylist(id)));
                }
            }
        }
        return playlists;
    }

    /** Returns the playlist_song id of the entry at {@code position}, or throws if there is none. */
    private int findEntry(Connection c, int playlistId, int position) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(SELECT_ENTRY_AT_POSITION)) {
            ps.setInt(1, playlistId);
            ps.setInt(2, position);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalArgumentException("No song at position " + position + " on playlist " + playlistId);
                }
                return rs.getInt("id");
            } catch (SQLException e) {
                throw new DataAccessException("Could not find the entry", e);
            }
        }
    }

    /** Runs SHIFT_ENTRIES_UP or SHIFT_ENTRIES_DOWN for one playlist and range. */
    private void shift(Connection c, String sql, int playlistId, int from, int to) throws Exception {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, playlistId);
            ps.setInt(2, from);
            ps.setInt(3, to);
            ps.executeUpdate();
        }
    }
}
