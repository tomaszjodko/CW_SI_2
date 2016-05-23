package knn.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import knn.common.GeneratorkNN;
import knn.common.SystemDecyzyjny;

import java.io.File;

public class knnController {
    private final GeneratorkNN generator = new GeneratorkNN();

    @FXML
    private BorderPane rootNode;

    @FXML
    private TextField tfSystemTreningowyPath;

    @FXML
    private TextField tfSystemTestowyPath;

    @FXML
    private Button btnChooseSystemTestowy;

    @FXML
    private Button btnChooseSystemTreningowy;

    @FXML
    private TextArea taOutput;

    @FXML
    private TextArea taSystemTreningowy;

    @FXML
    private TextArea taSystemTestowy;

    @FXML
    private RadioButton rbMetrykaEuklidesowa;

    @FXML
    private RadioButton rbMetrykaManhattan;

    @FXML
    private RadioButton rbMetrykaCanberra;

    @FXML
    private RadioButton rbMetrykaCzebyszewa;

    @FXML
    private RadioButton rbMetrykaPearsona;

    @FXML
    private ComboBox cbWartoscK;

    @FXML
    private Button btnKlasyfikuj;


    @FXML
    private void initialize() {
        btnChooseSystemTreningowy.setOnAction(this::wybierzSystemTreningowy);
        btnChooseSystemTestowy.setOnAction(this::wybierzSystemTestowy);
        btnKlasyfikuj.setOnAction(this::klasyfikujSystemTestowy);

    }

    @FXML
    private void klasyfikujSystemTestowy(ActionEvent event){
        if(rbMetrykaEuklidesowa.isSelected()){
            this.generator.klasyfikujEuklidesowa((Integer) cbWartoscK.getSelectionModel().getSelectedItem());
            this.taOutput.setText(this.generator.toString());
            this.taOutput.setDisable(false);
        }
        if(rbMetrykaManhattan.isSelected()){
            this.generator.klasyfikujManhattan((Integer) cbWartoscK.getSelectionModel().getSelectedItem());
            this.taOutput.setText(this.generator.toString());
            this.taOutput.setDisable(false);
        }
        if(rbMetrykaCanberra.isSelected()){
            this.generator.klasyfikujCanberra((Integer) cbWartoscK.getSelectionModel().getSelectedItem());
            this.taOutput.setText(this.generator.toString());
            this.taOutput.setDisable(false);
        }
        if(rbMetrykaCzebyszewa.isSelected()){
            this.generator.klasyfikujCzebyszewa((Integer) cbWartoscK.getSelectionModel().getSelectedItem());
            this.taOutput.setText(this.generator.toString());
            this.taOutput.setDisable(false);
        }
        if(rbMetrykaPearsona.isSelected()){
            this.generator.klasyfikujPearsona((Integer) cbWartoscK.getSelectionModel().getSelectedItem());
            this.taOutput.setText(this.generator.toString());
            this.taOutput.setDisable(false);
        }
    }



    @FXML
    private void wybierzSystemTreningowy(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz system treningowy");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        if(selectedFile != null){
            this.generator.setSystemTreningowy(new SystemDecyzyjny(selectedFile));
            taSystemTreningowy.setText(generator.getSystemTreningowy().toString());
            taSystemTreningowy.setDisable(false);
            tfSystemTreningowyPath.setText(selectedFile.getPath());
            btnChooseSystemTestowy.setDisable(false);
            tfSystemTestowyPath.setDisable(false);
            this.taOutput.clear();
            this.cbWartoscK.getItems().clear();
            for (int i = 1; i<= generator.getMaxK(); i++){
                this.cbWartoscK.getItems().add(i);
            }
            cbWartoscK.setValue(generator.getMaxK());
        }
    }

    @FXML
    private void wybierzSystemTestowy(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz system testowy");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        if(selectedFile != null){
            this.generator.setSystemTestowy(new SystemDecyzyjny(selectedFile));
            this.generator.setSystemRoboczy(new SystemDecyzyjny(selectedFile));
            this.taSystemTestowy.setDisable(false);
            this.taSystemTestowy.setText(generator.getSystemTestowy().toString());
            this.tfSystemTestowyPath.setText(selectedFile.getPath());
            this.rbMetrykaEuklidesowa.setDisable(false);
            this.rbMetrykaManhattan.setDisable(false);
            this.rbMetrykaCanberra.setDisable(false);
            this.rbMetrykaCzebyszewa.setDisable(false);
            this.rbMetrykaPearsona.setDisable(false);
            this.cbWartoscK.setDisable(false);
            this.btnKlasyfikuj.setDisable(false);
            this.taOutput.clear();
        }
    }
}