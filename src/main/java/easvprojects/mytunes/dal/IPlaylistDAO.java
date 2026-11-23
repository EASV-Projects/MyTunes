package easvprojects.mytunes.dal;



public interface IPlaylistDAO {
    void insertPlaylist();
    ISongDAO getSong();
    void removeSongFromPlaylist();
    void addSongtoPlaylist();
    void deletePlaylist();
    void editPlaylistName();

}
