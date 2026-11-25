package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.Artist;
import easvprojects.mytunes.dal.IArtistDAO;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.db.ArtistDAO;

import java.util.List;

/**
 * Business logic for {@link Artist}s.
 * Validates input before handing it to the data access layer.
 */
public class ArtistManager {
    private final IArtistDAO artistDAO;

    public ArtistManager(IDBConnector db) {
        this(new ArtistDAO(db));
    }

    /** Creates a manager on top of the given DAO (handy for tests). */
    public ArtistManager(IArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    // CREATE

    /**
     * Creates and saves a new artist.
     *
     * @param name the artist's name; must not be blank
     * @throws IllegalArgumentException if the name is blank
     * @throws Exception                if the artist could not be saved
     */
    public void createArtist(String name) throws Exception {
        artistDAO.insertArtist(new Artist(0, Validation.requireText(name, "Artist name")));
    }

    // READ

    public List<Artist> getAllArtists() throws Exception {
        return artistDAO.getArtists();
    }

    /**
     * Returns the artists whose name contains {@code query}.
     * A blank query returns every artist.
     */
    public List<Artist> searchArtists(String query) throws Exception {
        if (Validation.isBlank(query)) {
            return artistDAO.getArtists();
        }
        return artistDAO.getArtistsByName(query.trim());
    }

    /** Returns the artist with the given id, or {@code null} if there is none. */
    public Artist getArtistById(int id) throws Exception {
        return artistDAO.getArtistById(id);
    }

    // UPDATE

    /**
     * Saves changes to an existing artist.
     *
     * @throws IllegalArgumentException if the artist is null or its name is blank
     */
    public void updateArtist(Artist artist) throws Exception {
        Validation.requireNonNull(artist, "Artist");
        Validation.requireText(artist.getName(), "Artist name");
        artistDAO.updateArtist(artist);
    }

    // DELETE

    /** Deletes an artist. Their songs are kept but no longer have an artist. */
    public void deleteArtist(Artist artist) throws Exception {
        Validation.requireNonNull(artist, "Artist");
        artistDAO.deleteArtist(artist.getId());
    }
}
