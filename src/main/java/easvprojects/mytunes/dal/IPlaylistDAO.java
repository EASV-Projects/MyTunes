package easvprojects.mytunes.dal;


import java.io.IOException;

public interface IPlaylistDAO {
    void insertPlaylist();
    ISongDAO getSongById(int id) throws Exception;
    void removeSongFromPlaylist();
    void addSongToPlaylist();
    void deletePlaylist();
    void editPlaylistName();
}
