package ClashRoyale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../ClashRoyale/View/SignInView.fxml"));

        primaryStage.setTitle("Clash Royale");
        Image icon = new Image("/ClashRoyale/resources/icon.jpg");
        primaryStage.getIcons().add(icon);

        primaryStage.setResizable (false);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        primaryStage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            logout(primaryStage);
        });
    }

    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to leave the game :(");
        alert.setContentText("Do you want to save before exiting?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            // TODO save EVERYTHING
            stage.close();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
