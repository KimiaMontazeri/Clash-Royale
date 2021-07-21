package ClashRoyale.controller;

import com.sun.prism.paint.Color;
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
    private Button menubuttonInTrainingCamp;

    @FXML
    private Button easybuttonInTrainingCamp;

    @FXML
    private Button mediumbuttonInTrainingCamp;

    @FXML
    void easyInTrainingCamp(ActionEvent event) throws IOException{
        easybuttonInTrainingCamp.setStyle("-fx-background-color: #ff0000; ");
        //changeScene(event, "../View/GameCon.fxml");

    }

    @FXML
    void mediumInTrainingCamp(ActionEvent event) throws IOException{
        mediumbuttonInTrainingCamp.setStyle("-fx-background-color: #ff0000; ");

        //changeScene(event, "../View/GameCon.fxml");

    }

    @FXML
    void menuInTrainingCamp(ActionEvent event) throws IOException {
        changeScene(event, "../View/MenuView.fxml");

    }
    public void changeScene(ActionEvent event,String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
