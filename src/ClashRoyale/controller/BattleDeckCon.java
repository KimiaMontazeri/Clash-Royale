package ClashRoyale.controller;
import ClashRoyale.model.elements.PlayersArchieve;
import ClashRoyale.model.elements.entities.Card;
import ClashRoyale.model.elements.entities.Entity;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class BattleDeckCon {
    private Stage stage;
    private Scene scene;
    private Parent root;
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();
    int numOfSelected = 0;
    boolean se11=false,sel2=false, se13=false,sel4=false,
            se21=false,se22=false, se23=false,se24=false,
            se31=false,se32=false,se33=false,se34=false;


    @FXML
    private ImageView im31;

    @FXML
    private ImageView im32;

    @FXML
    private ImageView im34;

    @FXML
    private ImageView im23;

    @FXML
    private ImageView im33;

    @FXML
    private ImageView im21;

    @FXML
    private ImageView im22;

    @FXML
    private ImageView im24;

    @FXML
    private Button menubuttonInBattleDeck;

    @FXML
    private ImageView im12;

    @FXML
    private ImageView im13;

    @FXML
    private ImageView im11;

    @FXML
    private ImageView im14;

    @FXML
    void cardPressed(MouseEvent event) {
        ImageView cardImageView = (ImageView)event.getSource();
        switch (cardImageView.getId()) {
            case "im11":
                if (se11==true) {
                    se11 = false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.FIRE);
                }
                else if (numOfSelected<8){
                    se11=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.FIRE);
                }
                return;
            case "im12":
                if (sel2==true){
                    sel2=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.INFERNO_TOWER);
                }
                else if (numOfSelected<8){
                    sel2=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.INFERNO_TOWER);
                }

                return;
            case "im13":
                if (se13==true){
                    se13=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.CANNON);
                }
                else if (numOfSelected<8){
                    se13=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.CANNON);

                }

                return;
            case "im14":
                if (sel4==true){
                    sel4=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.RAGE);

                }
                else if (numOfSelected<8){
                    sel4=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.RAGE);
                }

                return;
            case "im21":
                if (se21==true){
                    se21=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.GIANT);
                }

                else if (numOfSelected<8){
                    se21=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.GIANT);
                }

                return;
            case "im22":
                if (se22==true){
                    se22=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.WIZARD);
                }
                else if (numOfSelected<8){
                    se22=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.WIZARD);
                }

                return;
            case "im23":
                if (se23==true){
                    se23=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.BARBARIANS);
                }
                else if (numOfSelected<8){
                    se23=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.BARBARIANS);
                }

                return;
            case "im24":
                if (se24==true){
                    se24=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.MINI_PEKKA);
                }
                else if (numOfSelected<8){
                    se24=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.MINI_PEKKA);
                }

                return;
            case "im31":
                if (se31==true){
                    se31=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.ARCHER);
                }
                else if (numOfSelected<8){
                    se31=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.ARCHER);
                }

                return;
            case "im32":
                if (se32==true){
                    se32=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.ARROWS);
                }
                else if (numOfSelected<8){
                    se32=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.ARROWS);
                }

                return;
            case "im33":
                if (se33==true){
                    se33=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.VALKYRIE);
                }
                else if (numOfSelected<8){
                    se33=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.VALKYRIE);
                }

                return;
            case "im34":
                if (se34==true){
                    se34=false;
                    numOfSelected--;
                    cardImageView.setOpacity(0.2);
                    playersArchieve.getCurrentPlayer().removeCard(Entity.Type.BABY_DRAGON);
                }
                else if (numOfSelected<8){
                    se34=true;
                    numOfSelected++;
                    cardImageView.setOpacity(1);
                    playersArchieve.getCurrentPlayer().addCard(Entity.Type.BABY_DRAGON);
                }

                return;


        }


    }

    @FXML
    void menuInBattleDeck(ActionEvent event) throws IOException {
        changeScene(event, "../View/MenuView.fxml");

    }
    @FXML
    void scaleDown(MouseEvent event) {
        ImageView cardImageView = (ImageView)event.getSource();
        var scaleTrans = new ScaleTransition(Duration.millis(250), cardImageView);
        scaleTrans.setFromX(1.0);
        scaleTrans.setFromY(1.0);
        scaleTrans.setToX(1.1);
        scaleTrans.setToY(1.1);
        scaleTrans.stop();
        scaleTrans.setRate(-1.0);
        cardImageView.setViewOrder(0.0);
        scaleTrans.play();
        cardImageView.setCursor(Cursor.HAND);

    }


    @FXML
    void scaleUp(MouseEvent event) {
        ImageView cardImageView = (ImageView)event.getSource();
        var scaleTrans = new ScaleTransition(Duration.millis(250), cardImageView);
        scaleTrans.setFromX(1.0);
        scaleTrans.setFromY(1.0);
        scaleTrans.setToX(1.1);
        scaleTrans.setToY(1.1);
        scaleTrans.stop();
        scaleTrans.setRate(1.0);
        cardImageView.setViewOrder(-1.0);
        scaleTrans.play();
        cardImageView.setCursor(Cursor.HAND);

    }

    @FXML
    public void initialize(){
        im11.setOpacity(0.2);
        im12.setOpacity(0.2);
        im13.setOpacity(0.2);
        im14.setOpacity(0.2);
        im21.setOpacity(0.2);
        im22.setOpacity(0.2);
        im23.setOpacity(0.2);
        im24.setOpacity(0.2);
        im31.setOpacity(0.2);
        im32.setOpacity(0.2);
        im33.setOpacity(0.2);
        im34.setOpacity(0.2);
    }


    public void changeScene(ActionEvent event,String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}

