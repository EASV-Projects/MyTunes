package easvprojects.mytunes.dal;

import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.be.Playlist;
import easvprojects.mytunes.be.Song;

import java.util.List;

/**
 * Data access for {@link Playlist} records and the ordered songs on them.
 * <p>
 * A playlist holds its songs in order, and the same song can appear on it
 * more than once. A <em>position</em> identifies one entry on a playlist,
 * matching the order returned by {@link #getSongsOnPlaylist(Playlist)}.
 * <p>
 * Every method throws {@link Exception} when the underlying storage
 * operation fails.
 */
public interface IPlaylistDAO {

    // CREATE

    /**
     * Saves a new, empty playlist.
     * The storage assigns the id, so the id on {@code playlist} is ignored.
     *
     * @param playlist the playlist to save
     * @throws Exception if the playlist could not be saved
     */
    void insertPlaylist(Playlist playlist) throws Exception;

    /**
     * Adds a song to the end of a playlist.
     * A song that is already on the playlist is added again.
     *
     * @param playlist the playlist to add to
     * @param song     the song to add
     * @throws Exception if the song could not be added
     */
    void addSongToPlaylist(Playlist playlist, Song song) throws Exception;

    // READ

    /**
     * Returns every playlist.
     *
     * @return all playlists, or an empty list if there are none
     * @throws Exception if the playlists could not be read
     */
    List<Playlist> getPlaylists() throws Exception;

    /**
     * Returns the playlists whose name contains {@code Name}.
     *
     * @param name the text to look for in playlist names
     * @return the matching playlists, or an empty list if none match
     * @throws Exception if the playlists could not be read
     */
    List<Playlist> getPlaylistByName(String name) throws Exception;

    /**
     * Returns the playlists that contain at least one song of the given genre.
     *
     * @param genre the genre to look for
     * @return the matching playlists, or an empty list if none match
     * @throws Exception if the playlists could not be read
     */
    List<Playlist> getPlaylistsByGenre(Genre genre) throws Exception;

    /**
     * Returns the playlists that contain the given song.
     *
     * @param song the song to look for
     * @return the playlists the song is on, or an empty list if it is on none
     * @throws Exception if the playlists could not be read
     */
    List<Playlist> getPlaylistsBySong(Song song) throws Exception;

    /**
     * Returns the songs on a playlist in playlist order.
     * A song appears once for each time it was added.
     *
     * @param playlist the playlist to read
     * @return the songs in order, or an empty list if the playlist is empty
     * @throws Exception if the songs could not be read
     */
    List<Song> getSongsOnPlaylist(Playlist playlist) throws Exception;

    // UPDATE

    /**
     * Saves changes to an existing playlist's name and description,
     * matched by its id. The songs on it are not changed.
     *
     * @param playlist the playlist with its new values
     * @throws Exception if the playlist could not be updated
     */
    void updatePlaylist(Playlist playlist) throws Exception;

    // DELETE

    /**
     * Removes the entry at {@code position} from a playlist.
     * The songs after it move up one place. The song itself is not deleted.
     *
     * @param playlist the playlist to remove from
     * @param position the position of the entry to remove
     * @throws Exception if the entry could not be removed
     */
    void removeSongFromPlaylist(Playlist playlist, int position) throws Exception;

    /**
     * Moves the entry at {@code fromPosition} to {@code toPosition}.
     * The songs in between shift one place to fill the gap.
     *
     * @param playlist     the playlist to reorder
     * @param fromPosition the current position of the entry
     * @param toPosition   the position the entry should end up at
     * @throws Exception if the entry could not be moved
     */
    void moveSong(Playlist playlist, int fromPosition, int toPosition) throws Exception;

    void deletePlaylistfromPlaylists(int id) throws Exception;

}
