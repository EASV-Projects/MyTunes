package easvprojects.mytunes.dal.db;

import easvprojects.mytunes.dal.IDBConnector;

import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.dal.IGenreDAO;
import easvprojects.mytunes.dal.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO implements IGenreDAO {

    // SQL queries against the genre table (see schema.sql)
    private static final String INSERT_GENRE = "INSERT INTO genre (name) VALUES (?)";
    private static final String SELECT_ALL_GENRES = "SELECT id, name FROM genre ORDER BY name";
    private static final String SELECT_GENRES_BY_NAME = "SELECT id, name FROM genre WHERE name LIKE '%' || ? || '%' ORDER BY name";
    private static final String UPDATE_GENRE = "UPDATE genre SET name = ? WHERE id = ?";
    private static final String DELETE_GENRE = "DELETE FROM genre WHERE id = ?";

    private final IDBConnector dbConnector;

    /**
     * Creates a GenreDAO that uses the given database connector.
     *
     * @param dbConnector the connector used to open database connections
     */
    public GenreDAO(IDBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public void insertGenre(Genre genre) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(INSERT_GENRE)) {
            ps.setString(1, genre.getName());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not insert the genre.", e);
        }

    }

    @Override
    public List<Genre> getGenres() throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL_GENRES);
             ResultSet rs = ps.executeQuery()) {
            return readGenres(rs);
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not retrieve genres.", e);
        }
    }

    @Override
    public List<Genre> searchGenres(String query) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_GENRES_BY_NAME)) {
            ps.setString(1, query);
            try (ResultSet rs = ps.executeQuery()) {
                return readGenres(rs);
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not search for genres.", e);
        }
    }

    @Override
    public void updateGenre(Genre genre) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(UPDATE_GENRE)) {
            ps.setString(1, genre.getName());
            ps.setInt(2, genre.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not update the genre.", e);
        }
    }

    @Override
    public void deleteGenre(int id) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE_GENRE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not remove the genre.", e);
        }
    }

    /** Turns every remaining row of {@code rs} into a Genre. */
    private List<Genre> readGenres(ResultSet rs) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        while (rs.next()) {
            genres.add(new Genre(rs.getInt("id"), rs.getString("name")));
        }
        return genres;
    }
}
