package easvprojects.mytunes.gui.controller;

import easvprojects.mytunes.be.Playlist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PlaylistController {
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField nameField;

    private boolean saved = false;
    private String playlistName;


    public void setPlaylist(Playlist playlist) {
        if (playlist != null) {
            if (titleLabel != null)
                titleLabel.setText("Edit Playlist");
            if (nameField != null)
                nameField.setText(nullToEmpty(playlist.getName()));
        } else {
            if (titleLabel != null)
                titleLabel.setText("New Playlist");
            if (nameField != null)
                nameField.clear();
        }

    }

    public boolean isSaved() {
        return saved;
    }

    public String getPlaylistName() {
        return playlistName;
    }


    public void onBtnCancel(ActionEvent actionEvent) {
        saved = false;
        closeDialog();
    }

    private void closeDialog() {
        if (nameField != null && nameField.getScene() != null) {
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        }

    }

    public void onBtnSave(ActionEvent actionEvent) {
        String name = nameField != null && nameField.getText() != null
                ? nameField.getText().trim() : "";
    }


    private String nullToEmpty(String text) {
        return text == null ? "" : text;
    }

}
