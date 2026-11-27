package easvprojects.mytunes.dal.db;

import easvprojects.mytunes.be.Album;
import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.dal.IAlbumDAO;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.ISongDAO;
import easvprojects.mytunes.dal.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO implements IAlbumDAO {

    // SQL queries against the album and album_genre tables (see schema.sql)
    private static final String INSERT_ALBUM = "INSERT INTO album (name) VALUES (?)";
    private static final String INSERT_ALBUM_GENRE = "INSERT INTO album_genre (album_id, genre_id) VALUES (?, ?)";

    private static final String SELECT_ALL_ALBUMS = "SELECT id, name FROM album ORDER BY name";
    private static final String SELECT_ALBUMS_BY_NAME =
            "SELECT id, name FROM album WHERE name LIKE '%' || ? || '%' ORDER BY name";
    private static final String SELECT_ALBUMS_BY_GENRE =
            "SELECT a.id, a.name FROM album a JOIN album_genre ag ON ag.album_id = a.id " +
            "WHERE ag.genre_id = ? ORDER BY a.name";
    private static final String SELECT_ALBUM_BY_ID = "SELECT id, name FROM album WHERE id = ?";
    private static final String SELECT_GENRES_FOR_ALBUM =
            "SELECT g.id, g.name FROM genre g JOIN album_genre ag ON ag.genre_id = g.id " +
            "WHERE ag.album_id = ? ORDER BY g.name";

    private static final String UPDATE_ALBUM = "UPDATE album SET name = ? WHERE id = ?";
    private static final String DELETE_ALBUM_GENRES = "DELETE FROM album_genre WHERE album_id = ?";

    // album_genre rows are removed by ON DELETE CASCADE,
    // songs keep existing with album_id set to NULL (ON DELETE SET NULL)
    private static final String DELETE_ALBUM = "DELETE FROM album WHERE id = ?";

    private final IDBConnector dbConnector;
    private final ISongDAO songDAO;

    /**
     * Creates an AlbumDAO that uses the given database connector.
     *
     * @param dbConnector the connector used to open database connections
     */
    public AlbumDAO(IDBConnector dbConnector) {
        this.dbConnector = dbConnector;
        this.songDAO = new SongDAO(dbConnector);
    }

    @Override
    public void insertAlbum(Album album) throws Exception {
        // The album row and its genre links are saved together, or not at all
        try (Connection c = dbConnector.getConnection()) {
            c.setAutoCommit(false);
            try {
                int albumId;
                try (PreparedStatement ps = c.prepareStatement(INSERT_ALBUM, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, album.getName());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        keys.next();
                        albumId = keys.getInt(1);
                    }
                }
                insertGenres(c, albumId, album.getGenres());
                c.commit();
            }  catch (Exception e) {
                c.rollback();
                throw new DataAccessException("Could not insert the album.", e);

            }
        }
    }

    @Override
    public List<Album> getAlbums() throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALL_ALBUMS)) {
            return readAlbums(c, ps);
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not retrieve albums.", e);
        }
    }

    @Override
    public List<Album> getAlbumsByName(String name) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALBUMS_BY_NAME)) {
            ps.setString(1, name);
            return readAlbums(c, ps);
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not retrieve albums.", e);
        }
    }

    @Override
    public List<Album> getAlbumsByGenre(Genre genre) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALBUMS_BY_GENRE)) {
            ps.setInt(1, genre.getId());
            return readAlbums(c, ps);
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not retrieve albums.", e);
        }
    }

    @Override
    public Album getAlbumById(int id) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(SELECT_ALBUM_BY_ID)) {
            ps.setInt(1, id);
            List<Album> albums = readAlbums(c, ps);
            return albums.isEmpty() ? null : albums.get(0);
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not retrieve the album.", e);
        }
    }

    @Override
    public void updateAlbum(Album album) throws Exception {
        try (Connection c = dbConnector.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement(UPDATE_ALBUM)) {
                    ps.setString(1, album.getName());
                    ps.setInt(2, album.getId());
                    ps.executeUpdate();
                }
                // Replace the album's genres with the ones it has now
                try (PreparedStatement ps = c.prepareStatement(DELETE_ALBUM_GENRES)) {
                    ps.setInt(1, album.getId());
                    ps.executeUpdate();
                }
                insertGenres(c, album.getId(), album.getGenres());
                c.commit();
            } catch (Exception e) {
                c.rollback();
                throw e;
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not update the album.", e);
        }
    }

    @Override
    public void deleteAlbum(int id) throws Exception {
        try (Connection c = dbConnector.getConnection();
             PreparedStatement ps = c.prepareStatement(DELETE_ALBUM)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new DataAccessException("Could not remove the album.", e);
        }
    }

    /**
     * Runs {@code ps} and turns every row into an Album, including its genres and songs.
     * The rows are read first and the ResultSet closed before the extra lookups run.
     */
    private List<Album> readAlbums(Connection c, PreparedStatement ps) throws Exception {
        List<Album> albums = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                albums.add(new Album(rs.getInt("id"), rs.getString("name"), new ArrayList<>(), new ArrayList<>()));
            }
        }
        for (Album album : albums) {
            album.getGenres().addAll(readGenres(c, album.getId()));
            album.getSongs().addAll(songDAO.getSongsByAlbum(album));
        }
        return albums;
    }

    /** Returns the genres of one album. */
    private List<Genre> readGenres(Connection c, int albumId) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(SELECT_GENRES_FOR_ALBUM)) {
            ps.setInt(1, albumId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    genres.add(new Genre(rs.getInt("id"), rs.getString("name")));
                }
            }
        }
        return genres;
    }

    /** Links an album to each of the given genres. */
    private void insertGenres(Connection c, int albumId, List<Genre> genres) throws SQLException {
        if (genres == null) return;
        try (PreparedStatement ps = c.prepareStatement(INSERT_ALBUM_GENRE)) {
            for (Genre genre : genres) {
                ps.setInt(1, albumId);
                ps.setInt(2, genre.getId());
                ps.executeUpdate();
            }
        }


    }
}
