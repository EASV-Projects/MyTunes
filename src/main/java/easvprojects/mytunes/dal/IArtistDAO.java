package easvprojects.mytunes.dal;

import easvprojects.mytunes.be.Artist;
import java.util.List;

public interface IArtistDAO {

    // Get all artists
    List<Artist> getArtists();

    // Add a new artist
    void insertArtist (Artist artist);

    // Update an existing artist
    void updateArtist(Artist artist);

    // Delete an artist
    void deleteArtist(int id);
}
