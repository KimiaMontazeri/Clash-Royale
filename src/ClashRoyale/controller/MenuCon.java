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

/**
 * Controller class for the main menu
 * @author NEGAR
 * @since 7-22-2021
 * @version 1.0
 */
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

    /**
     * handling battle deck button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void battleDeckButtonPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/BattleDeckView.fxml");

    }

    /**
     * handling battle history button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void battleHistoryButtonPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/BattleHistoryView.fxml");

    }

    /**
     * handling profile button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void profileButtonPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/ProfileView.fxml");

    }

    /**
     * handling training camp button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void trainingCampButtonPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/TrainingCampView.fxml");

    }

    /**
     * Changes the scene to the given address
     * @param event event
     * @param address fxml file address
     * @throws IOException I/O exception may occur in file loading
     */
    public void changeScene(ActionEvent event,String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
