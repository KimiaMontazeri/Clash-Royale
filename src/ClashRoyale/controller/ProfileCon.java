package ClashRoyale.controller;

import ClashRoyale.model.elements.PlayersArchieve;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileCon {
    private Stage stage;
    private Scene scene;
    private Parent root;
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();


    @FXML
    private Label cupNumProfile;

    @FXML
    private Label playerLevelProfile;

    @FXML
    private Button menubuttonInprofile;

    @FXML
    private Label leagueNameProfile;

    @FXML
    private Label usernameProfile;
    @FXML
    private void initialize() {
        usernameProfile.setText(playersArchieve.getCurrentPlayer().getUsername());
        playerLevelProfile.setText(playersArchieve.getCurrentPlayer().getLevelstr());

    }

    @FXML
    void menuInprofile(ActionEvent event) throws IOException {
        changeScene(event,"../View/MenuView.fxml");

    }
    public void changeScene(ActionEvent event,String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}