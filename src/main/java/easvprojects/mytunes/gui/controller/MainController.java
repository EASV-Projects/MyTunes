package easvprojects.mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {
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



    public void handleFilter(ActionEvent actionEvent) {
    }

    public void handlePrevious(ActionEvent actionEvent) {
    }

    public void handlePlayPause(ActionEvent actionEvent) {
    }

    public void handleNext(ActionEvent actionEvent) {
    }

    public void handleNewPlaylist(ActionEvent actionEvent) {
    }

    public void handleEditPlaylist(ActionEvent actionEvent) {
    }

    public void handleDeletePlaylist(ActionEvent actionEvent) {
    }

    public void handleMoveSongUp(ActionEvent actionEvent) {
    }

    public void handleMoveSongDown(ActionEvent actionEvent) {
    }

    public void handleRemoveSongFromPlaylist(ActionEvent actionEvent) {
    }

    public void handleAddSongToPlaylist(ActionEvent actionEvent) {
        
    }

    public void handleNewSong(ActionEvent actionEvent) {
    }

    public void handleEditSong(ActionEvent actionEvent) {
    }

    public void handleDeleteSong(ActionEvent actionEvent) {
        
    }
}
