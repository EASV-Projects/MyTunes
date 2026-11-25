package easvprojects.mytunes.gui.model;

import easvprojects.mytunes.be.Album;
import easvprojects.mytunes.be.Artist;
import easvprojects.mytunes.be.Genre;
import easvprojects.mytunes.be.Song;
import easvprojects.mytunes.bll.SongManager;
import easvprojects.mytunes.dal.ISongDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SongModel {
    // Observable list so UI components automatically update when songs change
    private final ObservableList<Song> songs = FXCollections.observableArrayList();
    private final SongManager songManager;

    public SongModel(ISongDAO songDAO) {
        this.songManager = new SongManager(songDAO);
    }

    public ObservableList<Song> getSongs(){
        return songs;
    }

    // load all songs from BLL and replaces the current list
    public void loadSongs() throws Exception {
        List<Song> loaded = songManager.getSongs();
        songs.setAll(loaded);
    }

    // Creates a new song via SongManager
    public void createSong(String title, Artist artist, Album album, List<Genre> genres) throws Exception {
        songManager.createSong(title, artist, album, genres);
        loadSongs(); // keeps songs in DB (including generated ID)
    }


}
