
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


    // Song search
    public List<Song> searchSongs(String query, SongFilter filter) throws Exception {

        Validation.requireNonNull(query, "Query");

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
    public void createSong(String title, Artist artist, Album album, List<Genre> genres) throws Exception {

            Validation.requireText(title, "Title");
            Validation.requireNonNull(artist, "Artist");
            Validation.requireNonNull(album, "Album");
            Validation.requireNonNull(genres, "Genres");

            Song song = new Song(0, title, artist, album, genres);
            songDAO.insertSong(song);

    }

    // Update an existing song
    public void updateSong(Song song) throws Exception {

        Validation.requireNonNull(song, "Song");

        songDAO.updateSong(song);
    }

    // Delete a song by ID
    public void deleteSong(Song song) throws Exception {

        Validation.requireNonNull(song, "Song");

        songDAO.deleteSong(song);
    }
}
