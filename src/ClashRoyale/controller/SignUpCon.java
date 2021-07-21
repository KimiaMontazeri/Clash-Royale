package ClashRoyale.controller;

import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.PlayersArchieve;
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

public class SignUpCon {
    private Stage stage;
    private Scene scene;
    private Parent root;

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
    void logInInSignUpPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/SignInView.fxml");

    }

    @FXML
    void signUpInSignUpPressed(ActionEvent event) throws IOException {
        if (signUpUsername.getText().equals("") ||
                signUpPassword1.getText().equals("")||
                signUpPassword2.getText().equals("")) {
            signUpError.setText("Fill in the blanks");

        } else if (playersArchieve.isAvailable(signUpUsername.getText())) {
            signUpError.setText("Username is taken");

        }else if (!signUpPassword1.getText().equals(signUpPassword2.getText())){
            signUpError.setText("Passwords do not match");


        } else {
            playersArchieve.getPlayersArchieve().add(new Player(signUpUsername.getText(),signUpPassword1.getText()));
            playersArchieve.setCurrentPlayer(playersArchieve.getPlayerByUserName(signUpUsername.getText()));
            changeScene(event,"../View/MenuView.fxml");
        }

    }
    public void changeScene(ActionEvent event,String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
