package ClashRoyale.controller;

import ClashRoyale.model.gamelogic.GameManager;
import ClashRoyale.view.ClashRoyaleView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.util.Duration;


public class GameCon implements EventHandler<MouseEvent> {

    final private static double FRAMES_PER_SECOND = 5.0;

    // the VBox that is going to show 4 cards and the next 5th card
    @FXML private ImageView card1, card2, card3, card4, nextCard, imageView;
    @FXML private Label timer, blueCrowns, redCrowns, elixirNum;
    @FXML private ClashRoyaleView clashRoyaleView;
    private ImageView selectedCard;
    private final GameManager gameManager;
    private Timeline gameTimer, elixirTimer, gameRenderingTimer;

    public GameCon() {
        gameManager = new GameManager();
    }

    public Timeline getGameTimer() {
        return gameTimer;
    }

    public Timeline getElixirTimer() {
        return elixirTimer;
    }

    public Timeline getGameRenderingTimer() {
        return gameRenderingTimer;
    }

    @FXML
    public void initialize() {
        initCards();
//        imageView.setOnDragOver(this::handleImageDragOver);
//        imageView.setOnDragDropped(this::handleImageDrop);

//        render();
//        startGame();

        startGameTimer();
        startElixirTimer(2);
    }

    public void initCards() {
        card1.setImage(new Image(getClass().getResourceAsStream("/ClashRoyale/resources/arrows/arrows.png")));
        card2.setImage(new Image(getClass().getResourceAsStream("/ClashRoyale/resources/buildings/canon-card.png")));
        card3.setImage(new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer.png")));
        card4.setImage(new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard.png")));
        nextCard.setImage(new Image(getClass().getResourceAsStream("/ClashRoyale/resources/fireball/fireball-card.png")));

        setMouseHoverOnCard(card1, true);
        setMouseHoverOnCard(card2, false);
        setMouseHoverOnCard(card3, true);
        setMouseHoverOnCard(card4, false);
    }

    public void render() {
        gameManager.updateGame();
        clashRoyaleView.update(gameManager.getGameData());
        if (gameManager.getGameData().gameOver)
            stop();
    }

    public void startGame() {
        long frameTimeInMillis = (long)(1000.0 / FRAMES_PER_SECOND);
        this.gameRenderingTimer = new Timeline(
                new KeyFrame(Duration.seconds(frameTimeInMillis), event -> {

                })
        );
        gameRenderingTimer.setCycleCount(Animation.INDEFINITE);
        gameRenderingTimer.play();
    }

    public void startElixirTimer(int duration) {
        this.elixirTimer = new Timeline(
                new KeyFrame(Duration.seconds(duration), event -> {
                    int num = Integer.parseInt(elixirNum.getText());
                    if (num <= 9)
                        elixirNum.setText(Integer.toString(++num));
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
        }
    }

    private void setMouseHoverOnCard(ImageView cardImageView, boolean bool) {
        var scaleTrans = new ScaleTransition(Duration.millis(250), cardImageView);
        scaleTrans.setFromX(1.0);
        scaleTrans.setFromY(1.0);
        scaleTrans.setToX(1.2);
        scaleTrans.setToY(1.2);
        if (bool) {
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

    public void stop() {
        this.gameTimer.stop();
        this.elixirTimer.stop();
        this.gameRenderingTimer.stop();
    }
}
