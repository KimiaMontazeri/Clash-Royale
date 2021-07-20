package ClashRoyale.model.elements;

import ClashRoyale.model.elements.entities.Entity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class EasyBot extends Bot {

    public EasyBot() {
        super(1);
    }

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


}
