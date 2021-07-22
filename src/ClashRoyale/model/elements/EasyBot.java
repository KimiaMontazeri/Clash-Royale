package ClashRoyale.model.elements;

import ClashRoyale.model.elements.entities.Entity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Game's easy bot
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class EasyBot extends Bot {
    /**
     * Create a bot with level 1
     */
    public EasyBot(int level) {
        super(level);
    }

    /**
     * Starts this bot
     */
    @Override
    public void start() {
        this.timeline = new Timeline(
                new KeyFrame(Duration.seconds(15), event -> {
                    Entity.Type chosenTroop = chooseTroop();
                    chooseCoordinate();
                    placeEntity(chosenTroop);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    /**
     * Chooses a random x,y coordinate in its own field
     */
    public void chooseCoordinate() {
        int attempt = 0;
        do {

            x = getRandomGenerator().nextInt(11) + 3;
            y = getRandomGenerator().nextInt(10) + 17;
            attempt++;
            if (attempt == 5)
                break;

        } while (gameData.isInsideMap(x, y) && gameData.map[x][y] == null);
    }

    /**
     * @return bot's name
     */
    public String getName() {
        return "EASY BOT";
    }


}
