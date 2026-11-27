package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.be.Playlist;
import easvprojects.mytunes.be.Song;

import java.util.List;

/**
 * Business logic for {@link Playlist}s and the ordered songs on them.
 * Implementations validate input before handing it to the data access layer.
 * <p>
 * A playlist holds its songs in order, and the same song can appear on it
 * more than once. Positions are 0-based and match the order returned by
 * {@link #getSongsOnPlaylist(Playlist)}.
 * <p>
 * Methods throw {@link IllegalArgumentException} for invalid input, and
 * {@link Exception} when the underlying storage operation fails.
 */
public interface IPlaylistManager {

    // CREATE

    /**
     * Creates and saves a new, empty playlist.
     *
     * @param name        the playlist's name; must not be blank
     * @param description the playlist's description; may be null or empty
     * @throws IllegalArgumentException if the name is blank
     * @throws Exception                if the playlist could not be saved
     */
    void createPlaylist(String name, String description) throws Exception;

    /**
     * Adds a song to the end of a playlist. A song already on it is added again.
     *
     * @throws IllegalArgumentException if the playlist or song is null
     */
    void addSongToPlaylist(Playlist playlist, Song song) throws Exception;

    // READ

    /** Returns every playlist. */
    List<Playlist> getAllPlaylists() throws Exception;

    /**
     * Returns the playlists whose name contains {@code query}.
     * A blank query returns every playlist.
     */
    List<Playlist> searchPlaylists(String query) throws Exception;

    /**
     * Returns the playlists that contain at least one song of the given genre.
     *
     * @throws IllegalArgumentException if the genre is null
     */
    List<Playlist> getPlaylistsByGenre(Genre genre) throws Exception;

    /**
     * Returns the playlists that contain the given song.
     *
     * @throws IllegalArgumentException if the song is null
     */
    List<Playlist> getPlaylistsBySong(Song song) throws Exception;

    /**
     * Returns the songs on a playlist in playlist order.
     *
     * @throws IllegalArgumentException if the playlist is null
     */
    List<Song> getSongsOnPlaylist(Playlist playlist) throws Exception;

    // UPDATE

    /**
     * Saves changes to an existing playlist's name and description.
     * The songs on it are not changed.
     *
     * @throws IllegalArgumentException if the playlist is null or its name is blank
     */
    void updatePlaylist(Playlist playlist) throws Exception;

    /**
     * Moves the entry at {@code fromPosition} to {@code toPosition}.
     * Moving an entry to where it already is does nothing.
     *
     * @throws IllegalArgumentException if the playlist is null or a position is not on the playlist
     */
    void moveSong(Playlist playlist, int fromPosition, int toPosition) throws Exception;

    /**
     * Moves the entry at {@code position} one place towards the start.
     * Does nothing if it is already first.
     *
     * @throws IllegalArgumentException if the playlist is null or the position is not on the playlist
     */
    void moveSongUp(Playlist playlist, int position) throws Exception;

    /**
     * Moves the entry at {@code position} one place towards the end.
     * Does nothing if it is already last.
     *
     * @throws IllegalArgumentException if the playlist is null or the position is not on the playlist
     */
    void moveSongDown(Playlist playlist, int position) throws Exception;

    // DELETE

    /**
     * Removes the entry at {@code position} from a playlist.
     * The song itself is not deleted.
     *
     * @throws IllegalArgumentException if the playlist is null or the position is not on the playlist
     */
    void removeSongFromPlaylist(Playlist playlist, int position) throws Exception;

}
