package ClashRoyale.model.elements.entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Spell category for the game's card
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class Spell extends Card {

    private final int radius;
    private double duration = 2; // default duration
    private int areaDamage;
    private final ArrayList<Entity> targets; // number of targets being affected by this spell
    private Timeline timeline; // a timeline to model the speed(for fireball & arrows) or the duration(for rage)
    private double rate = 1;

    /**
     * Constructs a spell
     * @param type type of this spell
     * @param isEnemy determines whether this spell if for the blue team or not
     * @param radius the radius it can affect
     * @param var
     */
    public Spell(Type type, boolean isEnemy, int radius, double var) {
        super(type, isEnemy);
        this.radius = radius;
        targets = new ArrayList<>();
        if (type.equals(Type.RAGE))
            this.duration = var;
        else if (type.equals(Type.ARROWS) || type.equals(Type.FIRE))
            this.areaDamage = (int) var;
        images = new HashMap<>();
        loadImages();
    }

    /**
     * Loads the image of this spell
     */
    private void loadImages() {
        if (getType() == Type.FIRE) {
            images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/explosions/fire.png")));
        } else if (getType() == Type.ARROWS) {
            images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/arrows/arrow.png")));
        }
    }

    /**
     * @return current image
     */
    @Override
    public Image getCurrentImage() {
        return images.get("DEFAULT");
    }

    /**
     * @return radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @return duration
     */
    public double getDuration() {
        return duration;
    }

    /**
     * @return area damage
     */
    public int getAreaDamage() {
        return areaDamage;
    }

    /**
     * @param areaDamage area damage
     */
    public void setAreaDamage(int areaDamage) {
        this.areaDamage = areaDamage;
    }

    /**
     * @param rate rate of boosting (if type is RAGE)
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * Activates this spell
     */
    @Override
    public void activate() {
        if (getLocation() != null) { // it should be placed on the map otherwise, it cannot be launched
            launch();
            startTimeline();
            Card.playSound(getType());
        }
    }

    /**
     * Starts timeline for this spell
     */
    public void startTimeline() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> update()) // calls update() each second for "duration + 1" times
        );
        timeline.setCycleCount((int) (duration + 1));
        timeline.play();
    }

    /**
     * launches this spell
     */
    private void launch() {
        int x = (int) getLocation().getX();
        int y = (int) getLocation().getY();

        // checks the enemies inside the radius of this spell
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                if (gameData.isInsideMap(i,j)) { // avoiding index out of bounds exception
                    if (getType().equals(Type.ARROWS) || getType().equals(Type.FIRE))
                        attack(i,j);
                    else
                        activateRage(i,j);
                }
            }
        }
    }

    /**
     * Attacks (if type is FIREBALL or ARROWS)
     * @param x x coordinate
     * @param y y coordinate
     */
    private void attack(int x, int y) {
        Entity entity = gameData.map[x][y];
        if (entity != null) {
            if (super.canAttack(entity))
                entity.getAttacked(this.areaDamage);
        } else {
            gameData.map[x][y] = this; // fireball or arrows will be displayed on the map
        }

    }

    /**
     * Activates rage
     * @param x x coordinate
     * @param y y coordinate
     */
    private void activateRage(int x, int y) { // activates rage on the given x,y coordinate
        Entity entity = gameData.map[x][y];
        if (entity != null) {
            if (!super.canAttack(entity) && (entity instanceof Tower || entity instanceof Building || entity instanceof Troop)) {
                // should not be its enemy
                entity.boost(rate);
                targets.add(entity);
            }
        }
    }

    /**
     * Decrement the duration until it reaches 0 (when it reaches 0, the timeline stops)
     */
    private void update() {
        duration--;
        if (duration == 0) {
            stop();
        }
    }

    /**
     * Stops this spell and resets everything to its normal state
     */
    public void stop() {
        if (timeline != null && timeline.getStatus().equals(Animation.Status.RUNNING))
            timeline.stop();

        if (getType().equals(Type.RAGE)) {
            for (Entity e : targets) {
                e.undoBoost(rate);
            }
        }
        else { // remove the arrow/fireball from the map so that gameView will no longer render the image of them
            int x = (int) getLocation().getX();
            int y = (int) getLocation().getY();
            for (int i = x - radius; i <= x + radius; i++) {
                for (int j = y - radius; j <= y + radius; j++) {
                    // avoiding index out of bounds exception
                    if (gameData.isInsideMap(i,j)) {
                        if (gameData.map[i][j] != null && gameData.map[i][j].equals(this))
                            gameData.map[i][j] = null;
                    }
                }
            }
        }
    }
}
