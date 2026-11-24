
package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.*;
import easvprojects.mytunes.dal.ISongDAO;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.db.SongDAO;
import easvprojects.mytunes.be.SongFilter;

import java.util.List;

public class SongManager {

    private ISongDAO songDAO;

    // Constructor using database connector
    public SongManager(IDBConnector db) {
        this.songDAO = new SongDAO(db);
    }

    // Constructor using DAO
    public SongManager(ISongDAO songDAO) {
        this.songDAO = songDAO;
    }

    // Get all songs
    public List<Song> getSongs() throws Exception {
        return songDAO.getSongs();
    }

    // Get song by ID
    public Song getSongById(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid song ID");
        }

        return songDAO.getSongById(id);
    }

    // Song search
    public List<Song> searchSongs(String query, SongFilter filter)
            throws Exception {

        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query cannot be empty");
        }

        return songDAO.searchSongs(query, filter);
    }

    // Get songs by artist
    public List<Song> getSongsByArtist(Artist artist) throws Exception {
        return songDAO.getSongsByArtist(artist);
    }

    // Get songs by album
    public List<Song> getSongsByAlbum(Album album) throws Exception {
        return songDAO.getSongsByAlbum(album);
    }

    // Get songs by genre
    public List<Song> getSongsByGenre(Genre genre) throws Exception {
        return songDAO.getSongsByGenre(genre);
    }

    // Insert a new song
    public void insertSong(Song song) throws Exception {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null");
        }

        songDAO.insertSong(song);
    }

    // Update an existing song
    public void updateSong(Song song) throws Exception {
        if (song == null) {
            throw new IllegalArgumentException("Song cannot be null");
        }

        songDAO.updateSong(song);
    }

    // Delete a song by ID
    public void deleteSong(int id) throws Exception {
        Song song = songDAO.getSongById(id);

        if (song == null) {
            throw new IllegalArgumentException("Song not found");
        }

        songDAO.deleteSong(song);
    }
}
