package ClashRoyale.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuCon {

    @FXML
    private Button profile;

    @FXML
    private Button battleDeck;

    @FXML
    private Button battleHistory;

    @FXML
    private Button trainingCamp;

    @FXML
    void battleDeckButtonPressed(ActionEvent event) {
        changeScene("../ClashRoyale/View/BattleDeckView.fxml");

    }

    @FXML
    void battleHistoryButtonPressed(ActionEvent event) {
        changeScene("../ClashRoyale/View/BattleHistoryView.fxml");

    }

    @FXML
    void profileButtonPressed(ActionEvent event) {
        changeScene("../ClashRoyale/View/ProfileView.fxml");

    }

    @FXML
    void trainingCampButtonPressed(ActionEvent event) {
        changeScene("../ClashRoyale/View/TrainingCampView.fxml");

    }
    public void changeScene(String address) {
        Stage stage;
        stage = (Stage) trainingCamp.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(address));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
