package ClashRoyale.controller;

import ClashRoyale.model.elements.PlayersArchieve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.util.ResourceBundle;

public class BattleHistoryCon {
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();

    private Stage stage;
    private Scene scene;
    private Parent root;


    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private ListView<String> HistoryList;
    @override
    public void initialize(URL url, ResourceBundle rb){
        loadData();
    }
    private void loadData(){
        list.removeAll();
        list.addAll(playersArchieve.getCurrentPlayer().getHistories());
        HistoryList.getItems
    }



    public void changeScene(ActionEvent event, String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
