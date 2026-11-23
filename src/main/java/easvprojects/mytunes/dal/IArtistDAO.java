package easvprojects.mytunes.dal;

import easvprojects.mytunes.be.Artist;
import java.util.List;

public interface IArtistDAO {

    // Get all artists
    List<Artist> getAllArtists();

    // Add a new artist
    void addArtist(Artist artist);

    // Update an existing artist
    void updateArtist(Artist artist);

    // Delete an artist
    void deleteArtist(int id);
}