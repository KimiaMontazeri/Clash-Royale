package ClashRoyale.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuCon {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private Button profile;

    @FXML
    private Button battleDeck;

    @FXML
    private Button battleHistory;

    @FXML
    private Button trainingCamp;

    @FXML
    void battleDeckButtonPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/BattleDeckView.fxml");

    }

    @FXML
    void battleHistoryButtonPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/BattleHistoryView.fxml");

    }

    @FXML
    void profileButtonPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/ProfileView.fxml");

    }

    @FXML
    void trainingCampButtonPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/TrainingCampView.fxml");

    }
    public void changeScene(ActionEvent event,String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
