package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Song;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.IFavoriteSongsDAO;
import easvprojects.mytunes.dal.db.FavoriteSongsDAO;
import org.controlsfx.validation.ValidateEvent;

import java.util.List;

public class FavoriteSongsManager {
    private IFavoriteSongsDAO favoriteSongsDAO;

    // Constructor using database connector
    public FavoriteSongsManager(IDBConnector db){
        this.favoriteSongsDAO = new FavoriteSongsDAO(db);
    }
    // Constructor using DAO
    public FavoriteSongsManager(FavoriteSongsDAO favoriteSongsDAO){
        this.favoriteSongsDAO = favoriteSongsDAO;
    }

    public void addFavoriteSong(Song song) throws Exception{
        Validation.requireNonNull(song, "Song");
        favoriteSongsDAO.addFavoriteSong(song);
    }

    public void removeFavoriteSong(int id) throws Exception{
        Validation.requireNonNull(id, "Song id");
        favoriteSongsDAO.removeFavoriteSong(id);
    }

    public List<Song> getFavotiteSongs() throws Exception{
        return favoriteSongsDAO.getFavoriteSongs();
    }
    private boolean isFavoriteSong(Song song) throws Exception{
        Validation.requireNonNull(song,"Song");
        return favoriteSongsDAO.isFavoriteSong(song);
    }
}
