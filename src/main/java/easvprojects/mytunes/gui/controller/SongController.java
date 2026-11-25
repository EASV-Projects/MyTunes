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
    private Label errorLabel;
    @FXML
    private BorderPane dialogRoot;
    @FXML
    private Label headerLabel;
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



    @FXML
    private void onBtnCancel(ActionEvent actionEvent) {

    }


    @FXML
    private void onBtnSave(ActionEvent actionEvent) {

    }




    @FXML
    private void onBtnChooseFile(ActionEvent actionEvent) {
    }
}
