
package easvprojects.mytunes.bll;

import easvprojects.mytunes.be.*;
import easvprojects.mytunes.dal.ISongDAO;
import easvprojects.mytunes.dal.IDBConnector;
import easvprojects.mytunes.dal.db.SongDAO;
import easvprojects.mytunes.be.SongFilter;

import java.nio.file.Path;
import java.time.Duration;
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
    public void createSong(String title, Artist artist, Album album, List<Genre> genres, Duration length, Path filePath) throws Exception {

            Validation.requireText(title, "Title");
            Validation.requireNonNull(artist, "Artist");
            Validation.requireNonNull(album, "Album");
            Validation.requireNonNull(genres, "Genres");
            Validation.requireNonNull(length, "Length");
            Validation.requireNonNull(filePath, "File path");

            Song song = new Song(0, title, artist, album, genres, length, filePath);
            songDAO.insertSong(song);

    }

    // Update an existing song
    public void updateSong(Song song) throws Exception {

        Validation.requireText(song.getName(), "Song title");

        songDAO.updateSong(song);
    }

    // Delete a song by song
    public void deleteSong(Song song) throws Exception {

        Validation.requireNonNull(song, "Song");

        songDAO.deleteSong(song);
    }

    //Get song
    public Song getSongById(int id) throws Exception{
        Validation.requireNonNull(id, "ID");
        return songDAO.getSongById(id);
    }
}
