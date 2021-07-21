package ClashRoyale.controller;

import ClashRoyale.model.elements.entities.Card;
import ClashRoyale.model.gamelogic.GameManager;
import ClashRoyale.view.ClashRoyaleView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class GameCon implements EventHandler<MouseEvent> {

    // the VBox that is going to show 4 cards and the next 5th card
    @FXML private ImageView card1, card2, card3, card4, nextCard;
    @FXML private Label timer, blueCrowns, redCrowns, elixirNum;
    @FXML private ClashRoyaleView clashRoyaleView;

    private ImageView selectedCard;
    private final GameManager gameManager;
    private Timeline gameTimer, elixirTimer, gameRenderingTimer;

    public GameCon() {
        gameManager = new GameManager();
    }

    @FXML
    public void initialize() {
        // init game model
        gameManager.start();

        // init game view
        initTiles();
        updateCardView();

        // start timelines
        startGame();
        startGameTimer();
        startElixirTimer(2);
    }

    public void initTiles() {

        ImageView[][] tiles = clashRoyaleView.getMap();
        for (int i = 0; i < clashRoyaleView.getRowCount(); i++) {
            for (int j = 0; j < clashRoyaleView.getColumnCount(); j++) {
                ImageView tile = tiles[i][j];
                tile.setOnDragOver(this::handleImageDragOver);
                tile.setOnDragDropped(this::handleImageDrop);
            }
        }
    }

    public void render() {
        gameManager.updateGame();
        clashRoyaleView.update(gameManager.getGameData());
        updateCrowns();
        // stop the game if it's over
        if (gameManager.getGameData().gameOver)
            stop();
    }

    private void updateCrowns() {
        Platform.runLater(() -> {
            redCrowns.setText(String.valueOf(gameManager.getGameData().redCrownNum));
            blueCrowns.setText(String.valueOf(gameManager.getGameData().blueCrownNum));
        });
    }

    public void startGame() {
        this.gameRenderingTimer = new Timeline(
                new KeyFrame(Duration.seconds(0.2), event -> render())
        );
        gameRenderingTimer.setCycleCount(Animation.INDEFINITE);
        gameRenderingTimer.play();
    }

    public void startElixirTimer(int duration) {
        this.elixirTimer = new Timeline(
                new KeyFrame(Duration.seconds(duration), event -> {
                    int num = gameManager.getGameData().elixirs;
                    if (num <= 10) {
                        elixirNum.setText(Integer.toString(num));
                        gameManager.getGameData().elixirs++;
                        updateCardView();
                    }
                })
        );
        elixirTimer.setCycleCount(Animation.INDEFINITE);
        elixirTimer.play();
    }

    public void startGameTimer() {
        this.gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> decrementSecond()));
        gameTimer.setCycleCount(Animation.INDEFINITE);
        gameTimer.play();
    }

    private void decrementSecond() {
        String time = timer.getText();

        // finding minute
        int min = Integer.parseInt(String.valueOf(time.charAt(0))) * 60;

        // finding second
        int digit1 = Integer.parseInt(String.valueOf(time.charAt(2))), digit2 = Integer.parseInt(String.valueOf(time.charAt(3)));
        int sec = (digit1 * 10) + digit2;

        int currentTime = min + sec - 1;
        sec = currentTime % 60;
        min = currentTime / 60;

        timer.setText("" + min + ":" + (sec < 10 ? "0" + sec : sec));

        // double the speed of incrementing elixirs
        if (min == 1 && sec == 0) {
            elixirTimer.stop();
            startElixirTimer(1);
        } else if (min == 0 && sec == 0) {
            gameManager.getGameData().gameOver = true;
        }
    }

    private void setMouseHoverOnCard(ImageView cardImageView, boolean isAffordable) {
        var scaleTrans = new ScaleTransition(Duration.millis(250), cardImageView);
        scaleTrans.setFromX(1.0);
        scaleTrans.setFromY(1.0);
        scaleTrans.setToX(1.2);
        scaleTrans.setToY(1.2);
        if (isAffordable) {
            cardImageView.setOnMouseEntered(e -> {
                scaleTrans.stop();
                scaleTrans.setRate(1.0);
                cardImageView.setViewOrder(-1.0);
                scaleTrans.play();
            });
            cardImageView.setOnMouseExited(e -> {
                scaleTrans.stop();
                scaleTrans.setRate(-1.0);
                cardImageView.setViewOrder(0.0);
                scaleTrans.play();
            });
            cardImageView.setOnMouseClicked(this);
            cardImageView.setCursor(Cursor.HAND);
            cardImageView.setOnDragDetected(mouseEvent -> this.handleOnDragDetection(mouseEvent, cardImageView));
        } else {
            cardImageView.setOnMouseEntered(e -> scaleTrans.stop());
            cardImageView.setOnMouseExited(e -> scaleTrans.stop());
            cardImageView.setOnMouseClicked(null);
            cardImageView.setCursor(Cursor.DEFAULT);
            cardImageView.setOpacity(0.2);
            cardImageView.setOnDragDetected(null);
        }
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        ImageView source = (ImageView) mouseEvent.getSource();
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            source.setImage(nextCard.getImage());
        }
    }

    @FXML
    private void handleOnDragDetection(MouseEvent mouseEvent, ImageView imageView) {
        selectedCard = imageView;
        Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putImage(imageView.getImage());
        dragboard.setContent(clipboardContent);
        mouseEvent.consume();
    }

    @FXML
    private void handleImageDragOver(DragEvent event) {
        if (event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void handleImageDrop(DragEvent event) {
        // finding the tile that the card has been dropped on
        ImageView tile = (ImageView) event.getTarget(); // x , y esh bar axe
        if (tile != null) {
            int row = (int) (tile.getX() / clashRoyaleView.getRowCount());
            int col = (int) (tile.getY() / clashRoyaleView.getColumnCount());

            Platform.runLater(() -> {
                if (selectedCard.equals(card1)) {
                    gameManager.useCard(1, col, row);
                }
                else if (selectedCard.equals(card2)) {
                    gameManager.useCard(2, col, row);
                }
                else if (selectedCard.equals(card3)) {
                    gameManager.useCard(3, col, row);
                }
                else if (selectedCard.equals(card4)) {
                    gameManager.useCard(4, col, row);
                }
                else System.out.println("WHY HAVE I REACHED THIS LINE???");
            });

            updateCardView();
        }
    }

    private void updateCardView() {
        Platform.runLater(() -> {
            // update their images
            card1.setImage(Card.loadCardImage(gameManager.getGameData().displayedCards.get(1)));
            card2.setImage(Card.loadCardImage(gameManager.getGameData().displayedCards.get(2)));
            card3.setImage(Card.loadCardImage(gameManager.getGameData().displayedCards.get(3)));
            card4.setImage(Card.loadCardImage(gameManager.getGameData().displayedCards.get(4)));

            // update each card ImageView's mouse hovering event
            setMouseHoverOnCard(card1, gameManager.getGameData().displayedCards.get(1).getCost() <= gameManager.getGameData().elixirs);
            setMouseHoverOnCard(card2, gameManager.getGameData().displayedCards.get(2).getCost() <= gameManager.getGameData().elixirs);
            setMouseHoverOnCard(card3, gameManager.getGameData().displayedCards.get(3).getCost() <= gameManager.getGameData().elixirs);
            setMouseHoverOnCard(card4, gameManager.getGameData().displayedCards.get(4).getCost() <= gameManager.getGameData().elixirs);
        });
    }

    public void stop() {
        this.gameTimer.stop();
        this.elixirTimer.stop();
        this.gameRenderingTimer.stop();
        gameManager.findWinner();
        gameManager.endGame();
        switchToBattleHistory();
    }

    /**
     * loads the battle history fxml file
     */
    private void switchToBattleHistory() {
        try {
            Stage stage = (Stage) nextCard.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/BattleHistoryView.fxml"));
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
