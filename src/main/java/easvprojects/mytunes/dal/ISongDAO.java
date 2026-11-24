package easvprojects.mytunes.dal;

import easvprojects.mytunes.be.*;

import java.util.List;
public interface ISongDAO {

    List<Song> getSongs() throws Exception;
    Song getSongById(int id) throws Exception;

    List<Song> searchSongs(String query, SongFilter filter) throws Exception;
    List<Song> getSongsByArtist(Artist artist) throws Exception;
    List<Song> getSongsByAlbum(Album album) throws Exception;
    List<Song> getSongsByGenre(Genre genre) throws Exception;

    void insertSong(Song song) throws Exception;
    void updateSong(Song song) throws Exception;
    void deleteSong(Song song) throws Exception;

}
