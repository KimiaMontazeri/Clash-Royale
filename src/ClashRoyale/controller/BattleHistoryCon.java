package ClashRoyale.controller;
import ClashRoyale.model.elements.History;
import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.PlayersArchieve;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class BattleHistoryCon {
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void menuInBattleHistory(ActionEvent event) throws IOException {
        changeScene(event, "../View/MenuView.fxml");

    }

    @FXML
    private ListView<String> listBox;
    final ObservableList<String> listItems = FXCollections.observableArrayList("Add Items here");


    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        playersArchieve.getCurrentPlayer().addHistory(
                new History((new Player("nene","no")),playersArchieve.getCurrentPlayer().getUsername()));
        System.out.println(playersArchieve.getCurrentPlayer().getHistories().get(0).toString());
        playersArchieve.getCurrentPlayer().addHistory(
                new History((new Player("nana","no")),"nana"));
        System.out.println(playersArchieve.getCurrentPlayer().getHistories().get(1).toString());
        playersArchieve.getCurrentPlayer().addHistory(
                new History((new Player("nono","no")),playersArchieve.getCurrentPlayer().getUsername()));
        System.out.println(playersArchieve.getCurrentPlayer().getHistories().get(2).toString());
        ArrayList<History> arrayList = playersArchieve.getCurrentPlayer().getHistories();
        for (History h : arrayList){
            listItems.add(h.toString());
            listBox.setItems(listItems);
            System.out.println(h.toString());
        }
        listBox.setItems(listItems);

    }






    public void changeScene(ActionEvent event, String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
