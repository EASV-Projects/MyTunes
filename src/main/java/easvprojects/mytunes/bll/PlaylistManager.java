package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.be.Playlist;
import easvprojects.mytunes.be.Song;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.IPlaylistDAO;
import easvprojects.mytunes.dal.ISongDAO;
import easvprojects.mytunes.dal.db.PlaylistDAO;
import easvprojects.mytunes.dal.db.SongDAO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistManager implements IPlaylistManager {

    private IPlaylistDAO playlistDAO;

    // Constructor using database connector
    public PlaylistManager(IDBConnector db) {
        this.playlistDAO = new PlaylistDAO(db);
    }

    // Constructor using DAO
    public PlaylistManager(IPlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    /**
     * Creates and saves a new, empty playlist.
     *
     * @param name        the playlist's name; must not be blank
     * @param description the playlist's description; may be null or empty
     * @throws IllegalArgumentException if the name is blank
     * @throws Exception                if the playlist could not be saved
     */
    @Override
    public void createPlaylist(String name, String description) throws Exception {
        String validName = Validation.requireText(name, "Playlist name");
        String validDescription = description == null ? "": description.trim();
        playlistDAO.insertPlaylist(new Playlist(0, validName, validDescription, new ArrayList<>()));
    }

    /**
     * Adds a song to the end of a playlist. A song already on it is added again.
     *
     * @param playlist
     * @param song
     * @throws IllegalArgumentException if the playlist or song is null
     */
    @Override
    public void addSongToPlaylist(Playlist playlist, Song song) throws Exception {
        Validation.requireNonNull(playlist, "Playlist");
        Validation.requireNonNull(song, "Song");
        playlistDAO.addSongToPlaylist(playlist, song);
    }

    /**
     * Returns every playlist.
     */
    @Override
    public List<Playlist> getAllPlaylists() throws Exception {
        return List.of();
        //playlistDAO.getPlaylists();
    }

    /**
     * Returns the playlists whose name contains {@code query}.
     * A blank query returns every playlist.
     *
     * @param query
     */
    @Override
    public List<Playlist> searchPlaylists(String query) throws Exception {
        return List.of();
    }

    /**
     * Returns the playlists that contain at least one song of the given genre.
     *
     * @param genre
     * @throws IllegalArgumentException if the genre is null
     */
    @Override
    public List<Playlist> getPlaylistsByGenre(Genre genre) throws Exception {
        return List.of();
    }

    /**
     * Returns the playlists that contain the given song.
     *
     * @param song
     * @throws IllegalArgumentException if the song is null
     */
    @Override
    public List<Playlist> getPlaylistsBySong(Song song) throws Exception {
        return List.of();
    }

    /**
     * Returns the songs on a playlist in playlist order.
     *
     * @param playlist
     * @throws IllegalArgumentException if the playlist is null
     */
    @Override
    public List<Song> getSongsOnPlaylist(Playlist playlist) throws Exception {
        return List.of();
    }

    /**
     * Saves changes to an existing playlist's name and description.
     * The songs on it are not changed.
     *
     * @param playlist
     * @throws IllegalArgumentException if the playlist is null or its name is blank
     */
    @Override
    public void updatePlaylist(Playlist playlist) throws Exception {

    }

    /**
     * Moves the entry at {@code fromPosition} to {@code toPosition}.
     * Moving an entry to where it already is does nothing.
     *
     * @param playlist
     * @param fromPosition
     * @param toPosition
     * @throws IllegalArgumentException if the playlist is null or a position is not on the playlist
     */
    @Override
    public void moveSong(Playlist playlist, int fromPosition, int toPosition) throws Exception {

    }

    /**
     * Moves the entry at {@code position} one place towards the start.
     * Does nothing if it is already first.
     *
     * @param playlist
     * @param position
     * @throws IllegalArgumentException if the playlist is null or the position is not on the playlist
     */
    @Override
    public void moveSongUp(Playlist playlist, int position) throws Exception {

    }

    /**
     * Moves the entry at {@code position} one place towards the end.
     * Does nothing if it is already last.
     *
     * @param playlist
     * @param position
     * @throws IllegalArgumentException if the playlist is null or the position is not on the playlist
     */
    @Override
    public void moveSongDown(Playlist playlist, int position) throws Exception {

    }

    /**
     * Removes the entry at {@code position} from a playlist.
     * The song itself is not deleted.
     *
     * @param playlist
     * @param position
     * @throws IllegalArgumentException if the playlist is null or the position is not on the playlist
     */
    @Override
    public void removeSongFromPlaylist(Playlist playlist, int position) throws Exception {

    }
}
