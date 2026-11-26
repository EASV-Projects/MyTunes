package easvprojects.mytunes.dal;


import easvprojects.mytunes.be.Song;

import java.util.List;

public interface IFavoriteSongsDAO {

    void addSongToFavorite(Song song) throws Exception;

    void removeSongFromFavorite(int id) throws Exception;

    List<Song> getSongsOnFavoritePlaylist() throws Exception;

    boolean isFavorite(Song song) throws Exception;



}
