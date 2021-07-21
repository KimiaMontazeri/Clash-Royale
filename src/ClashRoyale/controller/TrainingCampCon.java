package ClashRoyale.controller;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.EasyBot;
import ClashRoyale.model.elements.MediumBot;
import ClashRoyale.model.elements.PlayersArchieve;
import com.sun.prism.paint.Color;
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

    @FXML
    void easyInTrainingCamp(ActionEvent event) throws IOException {
        //easybuttonInTrainingCamp.setStyle("-fx-background-color: #ff0000; ");
        if (playersArchieve.getCurrentPlayer().getCards().size() == 8) {
            gameData.setBot(new EasyBot());
            changeScene(event, "/ClashRoyale/view/GameView.fxmll");
        } else
            ErrorLabel.setText("Cards Not Selected");

    }

    @FXML
    void mediumInTrainingCamp(ActionEvent event) throws IOException {
        // mediumbuttonInTrainingCamp.setStyle("-fx-background-color: #ff0000; ");
        if (playersArchieve.getCurrentPlayer().getCards().size() == 8) {
            gameData.setBot(new MediumBot());
            changeScene(event, "/ClashRoyale/view/GameView.fxml");
        } else
            ErrorLabel.setText("Cards Not Selected");

    }

    @FXML
    void menuInTrainingCamp(ActionEvent event) throws IOException {
        changeScene(event, "/ClashRoyale/view/MenuView.fxml");

    }

    public void changeScene(ActionEvent event, String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
