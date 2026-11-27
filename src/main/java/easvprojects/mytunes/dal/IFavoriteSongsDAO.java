package easvprojects.mytunes.dal;


import easvprojects.mytunes.be.Song;

import java.util.List;

public interface IFavoriteSongsDAO {

    void addFavoriteSong(Song song) throws Exception;

    void removeFavoriteSong(int id) throws Exception;

    List<Song> getFavoriteSongs() throws Exception;

    boolean isFavoriteSong(Song song) throws Exception;



}
