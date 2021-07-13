package ClashRoyale.model.elements.entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;

public class Spell extends Card {

    private final int radius;
    private double duration = 2;
    private int areaDamage;
    private final ArrayList<Entity> targets;
    private Timeline timeline;
    private double rate = 1;

    public Spell(Type type, boolean isEnemy, int radius, double var) {
        super(type, isEnemy);
        this.radius = radius;
        targets = new ArrayList<>();
        if (type.equals(Type.RAGE))
            this.duration = var;
        else if (type.equals(Type.ARROWS) || type.equals(Type.FIRE))
            this.areaDamage = (int) var;
    }

    public int getRadius() {
        return radius;
    }

    public double getDuration() {
        return duration;
    }

    public int getAreaDamage() {
        return areaDamage;
    }

    public void setAreaDamage(int areaDamage) {
        this.areaDamage = areaDamage;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public void activate() {
        if (getLocation() != null) { // it should be placed on the map otherwise, it cannot be launched
            launch();
            startTimeline();
        }
    }

    public void startTimeline() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        update();
                    }
                })
        );
        timeline.setCycleCount((int) (duration + 1));
        timeline.play();
    }

    private void launch() {
        int x = (int) getLocation().getX();
        int y = (int) getLocation().getY();

        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                if ((x >= 0 && x < gameData.rowCount) && (y >= 0 && y < gameData.colCount)) { // avoiding index out of bounds exception
                    if (getType().equals(Type.ARROWS) || getType().equals(Type.FIRE))
                        attack(x,y);
                    else
                        activateRage(x,y);
                }
            }
        }
    }

    private void attack(int x, int y) {
        Entity entity = gameData.map[x][y];
        if (entity != null) {
            if (super.canAttack(entity))
                entity.getAttacked(this.areaDamage);
        } else {
            gameData.map[x][y] = this; // fire or arrows will be displayed on the map
        }

    }

    private void activateRage(int x, int y) {
        Entity entity = gameData.map[x][y];
        if (entity != null) {
            if (!super.canAttack(entity) && (entity instanceof Tower || entity instanceof Building || entity instanceof Troop)) {
                // should not be its enemy
                entity.boost(rate);
                targets.add(entity);
            }
        }
    }

    private void update() {
        duration--;
        if (duration == 0) {
            stop();
        }
    }

    public void stop() {
        if (timeline != null && timeline.getStatus().equals(Animation.Status.RUNNING))
            timeline.stop();

        if (getType().equals(Type.RAGE)) {
            for (Entity e : targets) {
                e.undoBoost(rate);
            }
        }
        else {
            int x = (int) getLocation().getX();
            int y = (int) getLocation().getY();
            for (int i = x - radius; i <= x + radius; i++) {
                for (int j = y - radius; j <= y + radius; j++) {
                    // avoiding index out of bounds exception
                    if ((x >= 0 && x < gameData.rowCount) && (y >= 0 && y < gameData.colCount)) {
                        if (gameData.map[i][j].equals(this))
                            gameData.map[i][j] = null;
                    }
                }
            }
        }
    }
}
