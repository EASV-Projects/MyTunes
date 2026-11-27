package easvprojects.mytunes.dal.db;

import easvprojects.mytunes.be.Song;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.IFavoriteSongsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FavoriteSongsDAO implements IFavoriteSongsDAO {
    // SQL queries against the favorite_songs table (see schema.sql)
    private static final String INSERT_SONG = "INSERT INTO favorite_songs(song_id) VALUES(?)";
    private static final String DELETE_SONG = "DELETE FROM favorite_songs WHERE song_id = ?";
    private static final String GET_ALL = "SELECT song_id FROM favorite_songs";
    private static final String IS_FAVORITE = "SELECT song_id FROM favorite_songs WHERE song_id =?";

    private final IDBConnector dbConnector;
    private SongDAO songDAO;

    /**
     * @param dbConnector the connector used to open database connections
    */
    public FavoriteSongsDAO(IDBConnector dbConnector) {
        this.dbConnector = dbConnector;
        this.songDAO = new SongDAO(dbConnector);
    }


    @Override
    public void addFavoriteSong(Song song) throws Exception {
        try(Connection c = dbConnector.getConnection();
            PreparedStatement ps = c.prepareStatement(INSERT_SONG)){
            ps.setInt(1, song.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void removeFavoriteSong(int id) throws Exception {
        try(Connection c = dbConnector.getConnection();  // connecting sql
            PreparedStatement ps = c.prepareStatement(DELETE_SONG)){  //adding prepare command
            ps.setInt(1, id); // replace ? with song id
            ps.executeUpdate();
        }
    }

    @Override
    public List<Song> getFavoriteSongs() throws Exception {
        List<Song> allSongs = new ArrayList<>();
        try(Connection c = dbConnector.getConnection();
        PreparedStatement ps = c.prepareStatement(GET_ALL);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                int songId = rs.getInt("song_id");
                allSongs.add(songDAO.getSongById(songId));
            }
                return allSongs;
        }
    }

    @Override
    public boolean isFavoriteSong(Song song) throws Exception {
        try(Connection c = dbConnector.getConnection();
        PreparedStatement ps  = c.prepareStatement(IS_FAVORITE)){
            ps.setInt(1, song.getId()); //replace ? with song id
            try(ResultSet rs = ps.executeQuery()){
                return rs.next(); // return try if song is found
            }
        }
    }
}
