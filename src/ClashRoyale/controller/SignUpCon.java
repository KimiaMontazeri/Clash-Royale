package ClashRoyale.controller;

import ClashRoyale.model.elements.PlayersArchieve;
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

public class SignUpCon {
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();
    @FXML
    private Label signUpError;

    @FXML
    private TextField signUpUsername;

    @FXML
    private Button signInbuttonInSignup;

    @FXML
    private Button logInbuttonInSignUp;

    @FXML
    private PasswordField signUpPassword1;

    @FXML
    private PasswordField signUpPassword2;

    @FXML
    void logInInSignUpPressed(ActionEvent event) {
        changeScene("../ClashRoyale/View/SignInView.fxml");

    }
    public void changeScene(String address) {
        Stage stage;
        stage = (Stage) signUpUsername.getScene().getWindow();
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

    @FXML
    void signUpInSignUpPressed(ActionEvent event) {
        if (signUpUsername.getText() == null ||
                signUpPassword1.getText() == null||
                signUpPassword2.getText() == null) {
            signUpError.setText("Fill in the blanks");
            return;
        } else if (playersArchieve.isAvailable(signUpUsername.getText())) {
            signUpError.setText("Username is taken");
            return;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        playersArchieve.getPlayersArchieve().add(playersArchieve.getPlayerByUserName(signUpUsername.getText()));
        playersArchieve.setCurrentPlayer(playersArchieve.getPlayerByUserName(signUpUsername.getText()));
        changeScene("../ClashRoyale/View/MenuView.fxml");
    }

}
