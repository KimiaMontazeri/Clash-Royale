package ClashRoyale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class TrainingCampCon {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button mediumbuttonInBattleDeck;

    @FXML
    private Button easybuttonInBattleDeck;

    @FXML
    private Button menubuttonInTrainingCamp;

    @FXML
    void easyInTrainingCamp(ActionEvent event) {

    }

    @FXML
    void mediumInTrainingCamp(ActionEvent event) {

    }

    @FXML
    void menuInTrainingCamp(ActionEvent event) {

    }
    public void changeScene(ActionEvent event,String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
