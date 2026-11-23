package easvprojects.mytunes.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class SongController {
    @FXML private BorderPane dialogRoot;
    @FXML private Label headerLabel;
    @FXML private TextField titleField;
    @FXML private TextField artistField;
    @FXML private ComboBox categoryComboBox;
    @FXML private TextField timeField;
    @FXML private TextField filePathField;
    @FXML private Button chooseFileButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;


    public void onBtnCancel(ActionEvent actionEvent) {
    }

    public void onBtnSave(ActionEvent actionEvent) {
    }
}
