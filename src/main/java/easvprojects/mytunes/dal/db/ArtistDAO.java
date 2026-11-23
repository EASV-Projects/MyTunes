package easvprojects.mytunes.dal.db;

import easvprojects.mytunes.dal.IDBConnector;

import easvprojects.mytunes.be.Artist;
import easvprojects.mytunes.dal.IArtistDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistDAO implements IArtistDAO {

    // SQL queries against the artist table (see schema.sql)
    private static final String INSERT_ARTIST = "INSERT INTO artist (name) VALUES (?)";
    private static final String SELECT_ALL_ARTISTS = "SELECT id, name FROM artist ORDER BY name";
    private static final String SELECT_ARTISTS_BY_NAME = "SELECT id, name FROM artist WHERE name LIKE '%' || ? || '%' ORDER BY name";
    private static final String SELECT_ARTIST_BY_ID = "SELECT id, name FROM artist WHERE id = ?";
    private static final String UPDATE_ARTIST = "UPDATE artist SET name = ? WHERE id = ?";
    private static final String DELETE_ARTIST = "DELETE FROM artist WHERE id = ?";

    private final IDBConnector dbConnector;

    /**
     * Creates an ArtistDAO that uses the given database connector.
     *
     * @param dbConnector the connector used to open database connections
     */
    public ArtistDAO(IDBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    /**
     * Saves a new artist.
     * The storage assigns the id, so the id on {@code artist} is ignored.
     *
     * @param artist the artist to save
     * @throws Exception if the artist could not be saved
     */
    @Override
    public void insertArtist(Artist artist) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_ARTIST)) {
            ps.setString(1, artist.getName());
            ps.executeUpdate();
        }
    }

    /**
     * Returns every artist.
     *
     * @return all artists, or an empty list if there are none
     * @throws Exception if the artists could not be read
     */
    @Override
    public List<Artist> getArtists() throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL_ARTISTS);
             ResultSet rs = ps.executeQuery()) {
            return readArtists(rs);
        }
    }

    /**
     * Returns the artists whose name contains {@code name}.
     *
     * @param name the text to look for in artist names
     * @return the matching artists, or an empty list if none match
     * @throws Exception if the artists could not be read
     */
    @Override
    public List<Artist> getArtistsByName(String name) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ARTISTS_BY_NAME)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return readArtists(rs);
            }
        }
    }

    /**
     * Returns the artist with the given id.
     *
     * @param id the id of the artist
     * @return the artist, or {@code null} if no artist has that id
     * @throws Exception if the artist could not be read
     */
    @Override
    public Artist getArtistById(int id) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ARTIST_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? readArtist(rs) : null;
            }
        }
    }

    /**
     * Saves changes to an existing artist, matched by its id.
     *
     * @param artist the artist with its new values
     * @throws Exception if the artist could not be updated
     */
    @Override
    public void updateArtist(Artist artist) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE_ARTIST)) {
            ps.setString(1, artist.getName());
            ps.setInt(2, artist.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Deletes the artist with the given id.
     * Songs by this artist are kept, but no longer have an artist.
     *
     * @param id the id of the artist to delete
     * @throws Exception if the artist could not be deleted
     */
    @Override
    public void deleteArtist(int id) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE_ARTIST)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /** Turns the current row of {@code rs} into an Artist. */
    private Artist readArtist(ResultSet rs) throws SQLException {
        return new Artist(rs.getInt("id"), rs.getString("name"));
    }

    /** Turns every remaining row of {@code rs} into an Artist. */
    private List<Artist> readArtists(ResultSet rs) throws SQLException {
        List<Artist> artists = new ArrayList<>();
        while (rs.next()) {
            artists.add(readArtist(rs));
        }
        return artists;
    }
}
