package ClashRoyale.controller;

import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.PlayersArchieve;
import ClashRoyale.model.gamelogic.GameSetup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignInCon {
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();
    @FXML
    private Label SignInError;

    @FXML
    private TextField logInUsername;

    @FXML
    private PasswordField logInPassword;

    @FXML
    private Button logInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void logIninLogInPressed(ActionEvent event) {

        if (logInUsername.getText() == null ||
                logInPassword.getText() == null) {
            SignInError.setText("Fill in the blanks");
            return;
        } else if (playersArchieve.isAvailable(logInUsername.getText())&&
                !playersArchieve.getPlayerByUserName(logInUsername.getText()).getPassword().equals(logInPassword.getText())) {
            SignInError.setText("Incorrect password");
            return;
        }else if(!playersArchieve.isAvailable(logInUsername.getText())){
            SignInError.setText("Username not found");
            return;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playersArchieve.setCurrentPlayer(playersArchieve.getPlayerByUserName(logInUsername.getText()));
        changeScene("../ClashRoyale/View/MenuView.fxml");


    }

    @FXML
    void signUpinLogInPressed(ActionEvent event) {
        changeScene("../ClashRoyale/View/SignUpView.fxml");


        /*Stage stage;//
        Parent root;

        stage = (Stage) logInUsername.getScene().getWindow();//
        try {
            root = FXMLLoader.load(getClass().getResource("../ClashRoyale/View/SignUpView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
    }

    public void changeScene(String address) {
        Stage stage;
        stage = (Stage) logInUsername.getScene().getWindow();
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
