package ClashRoyale.controller;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.EasyBot;
import ClashRoyale.model.elements.MediumBot;
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

/**
 * Controller class for training camp menu
 * @author NEGAR
 * @since 7-22-2021
 * @version 1.0
 */
public class TrainingCampCon {
    private Stage stage;
    private Scene scene;
    private Parent root;
    GameData gameData = GameData.getInstance();
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();
    @FXML
    private Label ErrorLabel;

    @FXML
    private Button menubuttonInTrainingCamp;

    @FXML
    private Button easybuttonInTrainingCamp;

    @FXML
    private Button mediumbuttonInTrainingCamp;

    /**
     * handling easy button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void easyInTrainingCamp(ActionEvent event) throws IOException {
        //easybuttonInTrainingCamp.setStyle("-fx-background-color: #ff0000; ");
        if (playersArchieve.getCurrentPlayer().getCards().size() == 8) {
            gameData.setBot(new EasyBot(playersArchieve.getCurrentPlayer().getLevel()));
            changeScene(event, "/ClashRoyale/view/GameView.fxml");
        } else
            ErrorLabel.setText("Cards Not Selected");

    }

    /**
     * handling medium button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void mediumInTrainingCamp(ActionEvent event) throws IOException {
        // mediumbuttonInTrainingCamp.setStyle("-fx-background-color: #ff0000; ");
        if (playersArchieve.getCurrentPlayer().getCards().size() == 8) {
            gameData.setBot(new MediumBot(playersArchieve.getCurrentPlayer().getLevel()));
            changeScene(event, "/ClashRoyale/view/GameView.fxml");
        } else
            ErrorLabel.setText("Cards Not Selected");

    }

    /**
     * handling menu button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void menuInTrainingCamp(ActionEvent event) throws IOException {
        changeScene(event, "/ClashRoyale/view/MenuView.fxml");

    }

    /**
     * Changes the scene to the given address
     * @param event event
     * @param address fxml file address
     * @throws IOException I/O exception may occur in file loading
     */
    public void changeScene(ActionEvent event, String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
