package easvprojects.mytunes.dal;

import easvprojects.mytunes.be.Song;
import java.util.List;
public interface ISongDAO {

    List<Song> getSongs() throws Exception;
    List<Song> getSongsById() throws Exception;
    List<Song> getSongsByName() throws Exception;
    List<Song> getSongsByArtist() throws Exception;
    List<Song> getSongsByAlbum() throws Exception;

}
