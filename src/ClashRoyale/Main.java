package ClashRoyale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ///comment from negar
        Parent root = FXMLLoader.load(getClass().getResource("../ClashRoyale/View/SignInView.fxml"));
        primaryStage.setTitle("Clash Royale");
        primaryStage.setResizable (false);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
