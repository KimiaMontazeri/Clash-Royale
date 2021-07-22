package ClashRoyale.controller;

import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.PlayersArchieve;
import ClashRoyale.utils.FileUtil;
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

/**
 * Controller class for the signup menu
 * @author NEGAR
 * @since 7-22-2021
 * @version 1.0
 */
public class SignUpCon {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private PlayersArchieve playersArchieve;

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

    /**
     * initializes the whole scene and all the nodes inside
     */
    public void initialize() {

        // load the database
        FileUtil fileUtil = new FileUtil();
        PlayersArchieve.setInstance(fileUtil.readFromFile());
        playersArchieve = PlayersArchieve.getInstance();
    }

    /**
     * handling login button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void logInInSignUpPressed(ActionEvent event) throws IOException {
        changeScene(event,"../View/SignInView.fxml");

    }

    /**
     * handling signup button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
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
            playersArchieve.getPlayersArchieve().add(new Player(signUpUsername.getText(),signUpPassword1.getText(), 1, 300));
            playersArchieve.setCurrentPlayer(playersArchieve.getPlayerByUserName(signUpUsername.getText()));
            changeScene(event,"../View/MenuView.fxml");
        }

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
