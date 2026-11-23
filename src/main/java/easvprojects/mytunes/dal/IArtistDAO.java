package easvprojects.mytunes.dal;

import easvprojects.mytunes.be.Artist;
import java.util.List;

public interface IArtistDAO {

    // Get all artists
    List<Artist> getArtists() throws Exception;
    //Get artists by ID
    Artist getArtistById(int id)  throws Exception;
    //Get artist by Name
    List<Artist> getArtistsByName(String name) throws Exception;

    // Add a new artist
    void insertArtist (Artist artist) throws Exception;

    // Update an existing artist
    void updateArtist(Artist artist) throws Exception;

    // Delete an artist
    void deleteArtist(int id) throws Exception;


}
