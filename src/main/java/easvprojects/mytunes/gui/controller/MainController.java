package easvprojects.mytunes.gui.controller;

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


    @FXML private TableView playlistTable;
    @FXML private TableColumn playlistNameCol;
    @FXML private TableColumn playlistSongsCol;
    @FXML private TableColumn playlistTimeCol;
    @FXML private Button newPlaylistButton;
    @FXML private Button editPlaylistButton;
    @FXML private Button deletePlaylistButton;


    @FXML private Label selectedPlaylistLabel;
    @FXML private ListView playlistSongsListView;
    @FXML private Button moveSongUpButton;
    @FXML private Button moveSongDownButton;
    @FXML private Button removeSongFromPlaylistButton;

    // Transfer button
    @FXML private Button addSongToPlaylistButton;

    // Column 3: All Songs
    @FXML private Label songCountLabel;
    @FXML private TableView songTable;
    @FXML private TableColumn songTitleCol;
    @FXML private TableColumn songArtistCol;
    @FXML private TableColumn songCategoryCol;
    @FXML private TableColumn songTimeCol;
    @FXML private Button newSongButton;
    @FXML private Button editSongButton;
    @FXML private Button deleteSongButton;



    

    public void onBtnPrevious(ActionEvent actionEvent) {
    }

    public void onBtnPlayPause(ActionEvent actionEvent) {
    }

    public void onBtnNext(ActionEvent actionEvent) {
    }

    public void onBtnFilter(ActionEvent actionEvent) {
    }

    public void onBtnNewPlaylist(ActionEvent actionEvent) {
    }

    public void onBtnPlaylist(ActionEvent actionEvent) {
    }

    public void onBtnDeletePlaylist(ActionEvent actionEvent) {
    }

    public void onBtnMoveSongUp(ActionEvent actionEvent) {
    }

    public void onBtnMoveSongDown(ActionEvent actionEvent) {
    }

    public void onBtnRemoveSongFromPlaylist(ActionEvent actionEvent) {
    }

    public void onBtnAddSongToPlaylist(ActionEvent actionEvent) {
    }

    public void onBtnNewSong(ActionEvent actionEvent) {
    }

    public void onBtnEditSong(ActionEvent actionEvent) {
    }

    public void onBtnDeleteSong(ActionEvent actionEvent) {
    }
}
