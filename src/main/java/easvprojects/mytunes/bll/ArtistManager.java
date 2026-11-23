package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Artist;
import easvprojects.mytunes.dal.IArtistDAO;
import easvprojects.mytunes.dal.IDBConnector;

/**
 * Business logic for {@link Artist}s.
 * Validates input before handing it to the data access layer.
 */
public class ArtistManager {
    private IArtistDAO artistDAO;

    public ArtistManager(IDBConnector db) {
        // this(new ArtistDAO(db));
        // TODO: Add this after DAOs are implemented
    }

    /** Creates a manager on top of the given DAO (handy for tests). */
    public ArtistManager(IArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

}
