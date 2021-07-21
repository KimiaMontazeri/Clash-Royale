package ClashRoyale.controller;

import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.PlayersArchieve;
//import ClashRoyale.model.gamelogic.GameSetup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    private Stage stage;
    private Scene scene;
    private Parent root;

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
    void logIninLogInPressed(ActionEvent event) throws IOException {

        if (logInUsername.getText().equals("") ||
                logInPassword.getText().equals("")) {
            SignInError.setText("Fill in the blanks");

        } else if (playersArchieve.isAvailable(logInUsername.getText()) &&
                !playersArchieve.getPlayerByUserName(logInUsername.getText()).getPassword().equals(logInPassword.getText())) {
            SignInError.setText("Incorrect password");

        } else if (!playersArchieve.isAvailable(logInUsername.getText())) {
            SignInError.setText("Username not found");

        } else {
            playersArchieve.setCurrentPlayer(playersArchieve.getPlayerByUserName(logInUsername.getText()));
            changeScene(event, "../View/MenuView.fxml");
        }
    }

    @FXML
    void signUpinLogInPressed(ActionEvent event) throws IOException {
        changeScene(event, "../View/SignUpView.fxml");
    }

    public void changeScene(ActionEvent event, String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
