package ClashRoyale.controller;

import ClashRoyale.model.elements.PlayersArchieve;
import ClashRoyale.model.elements.entities.Entity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ProfileCon {
    private Stage stage;
    private Scene scene;
    private Parent root;
    PlayersArchieve playersArchieve = PlayersArchieve.getInstance();
    HashMap<Entity.Type,String> cardsImageMap = new HashMap<>();

    @FXML
    private ImageView im1;

    @FXML
    private ImageView im2;

    @FXML
    private ImageView im6;

    @FXML
    private ImageView im3;

    @FXML
    private ImageView im5;

    @FXML
    private ImageView im7;

    @FXML
    private ImageView im4;

    @FXML
    private ImageView im8;


    @FXML
    private Label cupNumProfile;

    @FXML
    private Label playerLevelProfile;

    @FXML
    private Button menubuttonInprofile;

    @FXML
    private Label leagueNameProfile;

    @FXML
    private Label usernameProfile;
    @FXML
    private void initialize() {
        im1.setVisible(false);
        im2.setVisible(false);
        im3.setVisible(false);
        im4.setVisible(false);
        im5.setVisible(false);
        im6.setVisible(false);
        im7.setVisible(false);
        im8.setVisible(false);
        mappingHashmap();
        usernameProfile.setText(playersArchieve.getCurrentPlayer().getUsername());
        playerLevelProfile.setText(playersArchieve.getCurrentPlayer().getLevelstr());
        int index=1;

        for (int p=1;p<=8;p++){
                switch (p){
                    case 1:
                        if(p<=playersArchieve.getCurrentPlayer().getCards().size()) {
                            im1.setVisible(true);
                            im1.setImage(new Image(getClass().getResourceAsStream(
                                    "/ClashRoyale/resources/pics/cards/" +
                                            cardsImageMap.get(playersArchieve.getCurrentPlayer().getCards().get(p-1)))));
                        }
                        else
                            im1.setVisible(false);
                        break;
                    case 2:
                        if(p<=playersArchieve.getCurrentPlayer().getCards().size()) {
                            im2.setVisible(true);
                            im2.setImage(new Image(getClass().getResourceAsStream(
                                    "/ClashRoyale/resources/pics/cards/" +
                                            cardsImageMap.get(playersArchieve.getCurrentPlayer().getCards().get(p-1)))));
                        }
                        else
                            im2.setVisible(false);
                        break;
                    case 3:
                        if(p<=playersArchieve.getCurrentPlayer().getCards().size()){
                            im3.setVisible(true);
                        im3.setImage(new Image(getClass().getResourceAsStream(
                                "/ClashRoyale/resources/pics/cards/"+
                                        cardsImageMap.get(playersArchieve.getCurrentPlayer().getCards().get(p-1)))));}
                        else
                            im3.setVisible(false);
                        break;
                    case 4:
                        if(p<=playersArchieve.getCurrentPlayer().getCards().size()){
                            im4.setVisible(true);
                            im4.setImage(new Image(getClass().getResourceAsStream(
                                    "/ClashRoyale/resources/pics/cards/"+
                                            cardsImageMap.get(playersArchieve.getCurrentPlayer().getCards().get(p-1)))));
                        }

                        else
                            im4.setVisible(false);
                        break;
                    case 5:
                        if(p<=playersArchieve.getCurrentPlayer().getCards().size()) {
                            im5.setVisible(true);
                            im5.setImage(new Image(getClass().getResourceAsStream(
                                    "/ClashRoyale/resources/pics/cards/" +
                                            cardsImageMap.get(playersArchieve.getCurrentPlayer().getCards().get(p-1)))));
                        }
                        else
                            im5.setVisible(false);
                        break;
                    case 6:
                        if(p<=playersArchieve.getCurrentPlayer().getCards().size()){
                            im6.setVisible(true);
                        im6.setImage(new Image(getClass().getResourceAsStream(
                                "/ClashRoyale/resources/pics/cards/"+
                                        cardsImageMap.get(playersArchieve.getCurrentPlayer().getCards().get(p-1)))));
                        }
                        else
                            im6.setVisible(false);
                        break;
                    case 7:
                        if(p<=playersArchieve.getCurrentPlayer().getCards().size()){
                            im7.setVisible(true);
                        im7.setImage(new Image(getClass().getResourceAsStream(
                                "/ClashRoyale/resources/pics/cards/"+
                                        cardsImageMap.get(playersArchieve.getCurrentPlayer().getCards().get(p-1)))));}
                        else
                            im7.setVisible(false);
                        break;
                    case 8:
                        if(p<=playersArchieve.getCurrentPlayer().getCards().size()){
                            im8.setVisible(true);
                        im8.setImage(new Image(getClass().getResourceAsStream(
                                "/ClashRoyale/resources/pics/cards/"+
                                        cardsImageMap.get(playersArchieve.getCurrentPlayer().getCards().get(p-1)))));}
                        else
                            im8.setVisible(false);
                        break;



                }
        }

    }

    @FXML
    void menuInprofile(ActionEvent event) throws IOException {
        changeScene(event,"../View/MenuView.fxml");

    }
    public void changeScene(ActionEvent event,String address) throws IOException {
        root = FXMLLoader.load(getClass().getResource(address));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void mappingHashmap(){
        cardsImageMap.put(Entity.Type.ARCHER,"archer.png");
        cardsImageMap.put(Entity.Type.ARROWS,"arrows.png");
        cardsImageMap.put(Entity.Type.BABY_DRAGON,"baby-dragon.png");
        cardsImageMap.put(Entity.Type.BARBARIANS,"barbarian.png");
        cardsImageMap.put(Entity.Type.CANNON,"canon-card.png");
        cardsImageMap.put(Entity.Type.FIRE,"fireball-card.png");
        cardsImageMap.put(Entity.Type.GIANT,"giant.png");
        cardsImageMap.put(Entity.Type.INFERNO_TOWER,"inferno-tower-card.png");
        cardsImageMap.put(Entity.Type.MINI_PEKKA,"mini-pekka.png");
        cardsImageMap.put(Entity.Type.RAGE,"rage.png");
        cardsImageMap.put(Entity.Type.VALKYRIE,"valkyrie.png");
        cardsImageMap.put(Entity.Type.WIZARD,"wizard.png");


    }

}