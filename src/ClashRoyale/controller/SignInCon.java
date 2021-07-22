package ClashRoyale.controller;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.PlayersArchieve;
import ClashRoyale.utils.AudioPlayer;
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
 * Controller class for sing in menu
 */
public class SignInCon {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private PlayersArchieve playersArchieve;

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

    /**
     * initializes the whole scene and all the nodes inside
     */
    public void initialize() {

        // load the database
        FileUtil fileUtil = new FileUtil();
        PlayersArchieve.setInstance(fileUtil.readFromFile());
        playersArchieve = PlayersArchieve.getInstance();

        // play the game's soundtrack
        String path = "src/ClashRoyale/resources/sound-effects/Clash-Royale-Soundtrack.wav";
        GameData gameData = GameData.getInstance();
        if (gameData.audioPlayer == null) {
            gameData.audioPlayer = new AudioPlayer(path);
            gameData.audioPlayer.play();
        }
    }

    /**
     * handling login button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
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

    /**
     * handling signup button pressed
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void signUpinLogInPressed(ActionEvent event) throws IOException {
        changeScene(event, "../View/SignUpView.fxml");
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
