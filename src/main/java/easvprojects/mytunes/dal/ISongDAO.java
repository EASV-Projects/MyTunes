package easvprojects.mytunes.dal;

import easvprojects.mytunes.be.Song;
import java.util.List;
public interface ISongDAO {

    List<Song> getSongs() throws Exception;
    Song getSongById() throws Exception;

    public enum SongFilter {
        TITLE, ARTIST, GENRE, ALBUM;
    }

    void searchSongs(String query, SongFilter filter) throws Exception;
    List<Song> getSongsByArtist() throws Exception;
    List<Song> getSongsByAlbum() throws Exception;
    List<Song> getSongsByGenre() throws Exception;

    void insertSong() throws Exception;
    void updateSong() throws Exception;
    void deleteSong() throws Exception;

}
