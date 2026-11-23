package easvprojects.mytunes.dal;



public interface IPlaylistDAO {
    void insertPlaylist();
    ISongDAO getSongById(int id);
    void removeSongFromPlaylist();
    void addSongToPlaylist();
    void deletePlaylist();
    void editPlaylistName();
}
