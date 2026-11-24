package easvprojects.mytunes.gui.controller;

import easvprojects.mytunes.be.Playlist;
import easvprojects.mytunes.be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class MainController {
    @FXML private Region coverArtBox;
    @FXML private BorderPane rootPane;
    @FXML private Button prevButton;
    @FXML private Button playPauseButton;
    @FXML private Button nextButton;
    @FXML private Label currentTitleLabel;
    @FXML private Label currentArtistLabel;
    @FXML private Label currentTimeLabel;
    @FXML private Slider seekSlider;
    @FXML private Label totalTimeLabel;
    @FXML private Slider volumeSlider;
    @FXML private TextField filterField;
    @FXML private Button filterButton;


    @FXML private TableView<Playlist> playlistTable;
    @FXML private TableColumn<Playlist, String> playlistNameCol;
    @FXML private TableColumn<Playlist, String> playlistSongsCol;
    @FXML private TableColumn<Playlist, Integer> playlistTimeCol;
    @FXML private Button newPlaylistButton;
    @FXML private Button editPlaylistButton;
    @FXML private Button deletePlaylistButton;


    @FXML private Label selectedPlaylistLabel;
    @FXML private ListView<Song> playlistSongsListView;
    @FXML private Button moveSongUpButton;
    @FXML private Button moveSongDownButton;
    @FXML private Button removeSongFromPlaylistButton;

    // Transfer button
    @FXML private Button addSongToPlaylistButton;

    // Column 3: All Songs
    @FXML private Label songCountLabel;
    @FXML private TableView<Song> songTable;
    @FXML private TableColumn<Song, String> songTitleCol;
    @FXML private TableColumn<Song, String> songArtistCol;
    @FXML private TableColumn<Song, String> songCategoryCol;
    @FXML private TableColumn<Song, Integer> songTimeCol;
    @FXML private Button newSongButton;
    @FXML private Button editSongButton;
    @FXML private Button deleteSongButton;



    @FXML
    private void onBtnPrevious(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnPlayPause(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnNext(ActionEvent actionEvent) {
        
    }

    @FXML
    private void onBtnFilter(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnNewPlaylist(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnEditPlaylist(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnDeletePlaylist(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnMoveSongUp(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnMoveSongDown(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnRemoveSongFromPlaylist(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnAddSongToPlaylist(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnNewSong(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnEditSong(ActionEvent actionEvent) {
    }

    @FXML
    private void onBtnDeleteSong(ActionEvent actionEvent) {
    }
}
