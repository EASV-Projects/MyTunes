package easvprojects.mytunes.gui.controller;

import easvprojects.mytunes.be.Artist;
import easvprojects.mytunes.be.Song;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SongController {
    @FXML
    private BorderPane dialogRoot;
    @FXML
    private Label titleLabel;
    @FXML
    private TextField titleField;
    @FXML
    private TextField artistField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField timeField;
    @FXML
    private TextField filePathField;
    @FXML
    private Button chooseFileButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    private Song editingSong;
    private Song resultSong;
    private boolean saved = false;

    public void setSong(Song song) {
        if (song != null) {
            if (titleLabel != null)
                titleLabel.setText("Edit Song");
            if (titleField != null)
                titleField.setText(nullToEmpty(song.getName()));
            ;
            //setComboValue(song.getGenres());

        } else {
            if (titleLabel != null)
                titleLabel.setText("New Song");
            if (titleField != null)
                titleField.clear();
            if (artistField != null)
                artistField.clear();
            if (timeField != null)
                timeField.clear();
        }
    }

    public void setArtist(Artist artist) {
        if (artistField != null)
            artistField.setText(nullToEmpty(artist.getName()));
        else {
            if (artistField != null)
                artistField.clear();
        }
    }

    public boolean isSaved(){
        return saved;
    }

    public Song getResultSong(){
        return resultSong;
    }


    public void onBtnCancel(ActionEvent actionEvent) {
        saved=false;
        resultSong= null;
        closeDialog();
    }

    private void closeDialog(){
        if(titleField != null&& titleField.getScene() !=null){
            Stage stage =(Stage) titleField.getScene().getWindow();
            stage.close();
        }

    }

    private String nullToEmpty(String text){
        return text == null? "":text;
    }

    public void onBtnSave(ActionEvent actionEvent) {
        String title = getText(titleField);
        String artist = getText(artistField);
        String time = getText(timeField);
        if (title.isEmpty()){
            showError("Title is required");
            return;
        }
        if (artist.isEmpty()){
            showError("Artist is required");
        }
    }

    private String getText(TextField field){
        return field == null || field.getText()==null
                ? field.getText().trim() : "";
    }

    private void showError(String msg){
    }


    public void onBtnChooseFile(ActionEvent actionEvent) {
    }
}
