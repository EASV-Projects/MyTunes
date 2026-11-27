package easvprojects.mytunes.dal.db;
import easvprojects.mytunes.dal.DataAccessException;

import easvprojects.mytunes.dal.IDBConnector;

import easvprojects.mytunes.be.*;
import easvprojects.mytunes.dal.ISongDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.nio.file.Path;

public class SongDAO implements ISongDAO {

    // SQL queries against the song and song_genre tables (see schema.sql)
    // Every song query starts from SELECT_SONG, which joins in the artist and album names.
    private static final String SELECT_SONG =
            "SELECT s.id, s.title, s.artist_id, ar.name AS artist_name, s.album_id, al.name AS album_name, " +
                    "s.length_seconds, s.file_path " +
                    "FROM song s " +
                    "LEFT JOIN artist ar ON ar.id = s.artist_id " +
                    "LEFT JOIN album al ON al.id = s.album_id";

    private static final String INSERT_SONG = "INSERT INTO song (title, artist_id, album_id, length_seconds, file_path) VALUES (?, ?, ?, ?, ?)";


    private static final String INSERT_SONG_GENRE = "INSERT INTO song_genre (song_id, genre_id) VALUES (?, ?)";

    private static final String SELECT_ALL_SONGS = SELECT_SONG + " ORDER BY s.title";
    private static final String SELECT_SONGS_BY_TITLE = SELECT_SONG + " WHERE s.title LIKE '%' || ? || '%' ORDER BY s.title";
    private static final String SELECT_SONGS_BY_ARTIST_NAME = SELECT_SONG + " WHERE ar.name LIKE '%' || ? || '%' ORDER BY s.title";
    private static final String SELECT_SONGS_BY_ALBUM_NAME = SELECT_SONG + " WHERE al.name LIKE '%' || ? || '%' ORDER BY s.title";
    private static final String SELECT_SONGS_BY_GENRE_NAME = SELECT_SONG +
            " WHERE s.id IN (SELECT sg.song_id FROM song_genre sg JOIN genre g ON g.id = sg.genre_id WHERE g.name LIKE '%' || ? || '%')" +
            " ORDER BY s.title";
    private static final String SELECT_SONGS_BY_ARTIST = SELECT_SONG + " WHERE s.artist_id = ? ORDER BY s.title";
    private static final String SELECT_SONGS_BY_ALBUM = SELECT_SONG + " WHERE s.album_id = ? ORDER BY s.title";
    private static final String SELECT_SONGS_BY_GENRE = SELECT_SONG +
            " WHERE s.id IN (SELECT song_id FROM song_genre WHERE genre_id = ?) ORDER BY s.title";
    private static final String SELECT_SONG_BY_ID = SELECT_SONG + " WHERE s.id = ?";
    private static final String SELECT_SONGS_ON_PLAYLIST = SELECT_SONG +
            " JOIN playlist_song ps ON ps.song_id = s.id WHERE ps.playlist_id = ? ORDER BY ps.position";
    private static final String SELECT_FAVORITE_SONGS_ON_PLAYLIST = SELECT_SONG + "JOIN favorite_songs fs ON fs.song_id = s.id";
    private static final String SELECT_GENRES_FOR_SONG =
            "SELECT g.id, g.name FROM genre g JOIN song_genre sg ON sg.genre_id = g.id WHERE sg.song_id = ? ORDER BY g.name";

    private static final String UPDATE_SONG = "UPDATE song SET title = ?, artist_id = ?, album_id = ?, length_seconds = ?, file_path = ? WHERE id = ?";
    private static final String DELETE_SONG_GENRES = "DELETE FROM song_genre WHERE song_id = ?";

    private static final String DELETE_SONG = "DELETE FROM song WHERE id = ?";

    private final IDBConnector dbConnector;

    /**
     * Creates a SongDAO that uses the given database connector.
     *
     * @param dbConnector the connector used to open database connections
     */
    public SongDAO(IDBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void insertSong(Song song) throws Exception {
        try (Connection c = dbConnector.getConnection()) {
            c.setAutoCommit(false);
            try {
                int songId;
                try (PreparedStatement ps = c.prepareStatement(INSERT_SONG, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, song.getName());
                    setArtistAndAlbum(ps, 2, song);
                    setLength(ps, 4, song);
                    setFilePath(ps, 5, song);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        keys.next();
                        songId = keys.getInt(1);
                    }
                }
                insertGenres(c, songId, song.getGenres());
                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw e;
            }
        }
    }

    @Override
    public List<Song> getSongs() throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL_SONGS)) {
            return readSongs(c, ps);
        } catch (SQLException e) {
        throw new DataAccessException("Could not retrieve songs.", e);
    }
    }

    @Override
    public List<Song> searchSongs(String query, SongFilter filter) throws Exception {
        String sql = switch (filter) {
            case TITLE -> SELECT_SONGS_BY_TITLE;
            case ARTIST -> SELECT_SONGS_BY_ARTIST_NAME;
            case ALBUM -> SELECT_SONGS_BY_ALBUM_NAME;
            case GENRE -> SELECT_SONGS_BY_GENRE_NAME;
        };
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, query);
            return readSongs(c, ps);
        } catch (SQLException e) {
            throw new DataAccessException("Could not search for songs.", e);
        }
    }

    @Override
    public List<Song> getSongsByArtist(Artist artist) throws Exception {
        return getSongsWhereIdIs(SELECT_SONGS_BY_ARTIST, artist.getId());
    }

    @Override
    public List<Song> getSongsByAlbum(Album album) throws Exception {
        return getSongsWhereIdIs(SELECT_SONGS_BY_ALBUM, album.getId());
    }

    @Override
    public List<Song> getSongsByGenre(Genre genre) throws Exception {
        return getSongsWhereIdIs(SELECT_SONGS_BY_GENRE, genre.getId());
    }

    @Override
    public Song getSongById(int id) throws Exception {
        List<Song> songs = getSongsWhereIdIs(SELECT_SONG_BY_ID, id);
        return songs.isEmpty() ? null : songs.get(0);
    }

    /**
     * Returns the songs on a playlist in playlist order.
     *
     * @param playlistId the id of the playlist
     * @return the songs in order, or an empty list if the playlist is empty
     * @throws Exception if the songs could not be read
     */
    List<Song> getSongsOnPlaylist(int playlistId) throws Exception {
        return getSongsWhereIdIs(SELECT_SONGS_ON_PLAYLIST, playlistId);
    }

    List<Song> getFavoriteSongs() throws Exception{
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_FAVORITE_SONGS_ON_PLAYLIST)) {
            return readSongs(c, ps);
        }
    }

    @Override
    public void updateSong(Song song) throws Exception {
        try (Connection c = dbConnector.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(UPDATE_SONG)) {
                    ps.setString(1, song.getName());
                    setArtistAndAlbum(ps, 2, song);
                    setLength(ps, 4, song);
                    setFilePath(ps, 5, song);
                    ps.setInt(6, song.getId());
                    ps.executeUpdate();
                }
                // Replace the song's genres with the ones it has now
                try (PreparedStatement ps = c.prepareStatement(DELETE_SONG_GENRES)) {
                    ps.setInt(1, song.getId());
                    ps.executeUpdate();
                }
                insertGenres(c, song.getId(), song.getGenres());
                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw new DataAccessException("Could not update the song.", e);
            }
        }
    }

    @Override
    public void deleteSong(Song song) throws Exception {
        // song_genre and playlist_song rows are removed by ON DELETE CASCADE
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE_SONG)) {
            ps.setInt(1, song.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Could not delete the song.", e);
        }
    }

    /**
     * Runs a song query that takes one int parameter and returns the songs.
     */
    private List<Song> getSongsWhereIdIs(String sql, int id) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return readSongs(c, ps);
        } catch (SQLException e) {
            throw new DataAccessException("Could not retrieve songs.", e);
        }
    }

    /**
     * Runs {@code ps} and turns every row into a Song, including its genres.
     */
    private List<Song> readSongs(Connection c, PreparedStatement ps) throws SQLException {
        List<Song> songs = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");

                Artist artist = null;
                int artistId = rs.getInt("artist_id");
                if (!rs.wasNull()) {
                    artist = new Artist(artistId, rs.getString("artist_name"));
                }

                // The album is loaded without its songs and genres, to avoid loading the whole library
                Album album = null;
                int albumId = rs.getInt("album_id");
                if (!rs.wasNull()) {
                    album = new Album(albumId, rs.getString("album_name"), new ArrayList<>(), new ArrayList<>());
                }
                Duration length = null;
                long seconds = rs.getLong("length_seconds");
                if (!rs.wasNull()) {
                    length = Duration.ofSeconds(seconds);
                }

                Path filePath = null;
                String path = rs.getString("file_path");
                if (path != null) {
                    filePath = Path.of(path);
                }

                songs.add(new Song(id, rs.getString("title"), artist, album, readGenres(c, id), length, filePath));

            }
        }
        return songs;
    }

    /**
     * Returns the genres of one song.
     */
    private List<Genre> readGenres(Connection c, int songId) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(SELECT_GENRES_FOR_SONG)) {
            ps.setInt(1, songId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(rs.getInt("id"), rs.getString("name")));
                }
            }
        }
        return genres;
    }

    /**
     * Links a song to each of the given genres.
     */
    private void insertGenres(Connection c, int songId, List<Genre> genres) throws SQLException {
        if (genres == null) return;
        try (PreparedStatement ps = c.prepareStatement(INSERT_SONG_GENRE)) {
            for (Genre genre : genres) {
                ps.setInt(1, songId);
                ps.setInt(2, genre.getId());
                ps.executeUpdate();
            }
        }
    }

    /**
     * Sets the artist id and album id parameters, or NULL when the song has none.
     */
    private void setArtistAndAlbum(PreparedStatement ps, int firstIndex, Song song) throws SQLException {
        if (song.getArtist() != null) ps.setInt(firstIndex, song.getArtist().getId());
        else ps.setNull(firstIndex, Types.INTEGER);

        if (song.getAlbum() != null) ps.setInt(firstIndex + 1, song.getAlbum().getId());
        else ps.setNull(firstIndex + 1, Types.INTEGER);
    }

    private void setLength(PreparedStatement ps, int index, Song song) throws SQLException {
        if (song.getLength() != null) ps.setLong(index, song.getLength().getSeconds());
        else ps.setNull(index, Types.INTEGER);
    }

    private void setFilePath(PreparedStatement ps, int index, Song song) throws SQLException {
        if (song.getFilePath() != null) ps.setString(index, song.getFilePath().toString());
        else ps.setNull(index, Types.VARCHAR);
    }
}
