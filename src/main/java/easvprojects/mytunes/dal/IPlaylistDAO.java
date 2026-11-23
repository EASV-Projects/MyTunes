package easvprojects.mytunes.dal;



import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.be.Playlist;
import easvprojects.mytunes.be.Song;

import java.io.IOException;
import java.util.List;

public interface IPlaylistDAO {
    void insertPlaylist(Playlist playlist) throws Exception;;
    Song getSongById(int id) throws Exception;
    void removeSongFromPlaylist(Song song) throws Exception;;
    void addSongToPlaylist(Song song)throws Exception;;
    void deletePlaylist(Playlist playlist) throws Exception;;
    void updatePlaylist(Playlist playlist) throws Exception;;
    List<Playlist> getPlaylistByName(String name) throws Exception;
    List<Playlist> getPlaylists(Playlist playlist);
    List<Playlist> getPlaylistsByGenre(Genre genre) throws Exception;
    List<Playlist> getPlaylistBySong(Song song) throws Exception;
    List<Playlist> getSongsOnPlaylist(Playlist playlist) throws Exception;
    void moveSongByPosition(Song song, int fromPosition, int toPosition) throws Exception;

}
