package ClashRoyale.controller;
import ClashRoyale.model.elements.History;
import ClashRoyale.model.elements.PlayersArchieve;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;

/**
 * Controller class for the battle history menu
 * @author NEGAR
 * @since 7-22-2021
 * @version 1.0
 */
public class BattleHistoryCon implements Initializable {
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Moves back to the menu scene
     * @param event event
     * @throws IOException I/O exception may occur
     */
    @FXML
    void menuInBattleHistory(ActionEvent event) throws IOException {
        changeScene(event, "../View/MenuView.fxml");

    }

    @FXML
    private ListView<String> listBox;


    final ObservableList<String> listItems = FXCollections.observableArrayList("opponent-date-winner");

    /**
     * initializes the whole scene and all the nodes inside
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        ArrayList<History> arrayList = playersArchieve.getCurrentPlayer().getHistories();
        for (History h : arrayList){
            listItems.add(h.toString());
            listBox.setItems(listItems);
            System.out.println(h.toString());
        }
        listBox.setItems(listItems);

    }


    /**
     * Changes the scene to the given address
     * @param event event
     * @param address fxml file address
     * @throws IOException I/O exception may occur in file loading
     */
    public void changeScene(ActionEvent event, String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
