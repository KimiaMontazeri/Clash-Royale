package ClashRoyale.model.elements.entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class Tower extends Entity {

    private int hp, damage;
    private int hitSpeed;
    private final int range;
    private boolean isActivated;

    private Timeline attackingTimeline;

    public Tower(Type type, boolean isEnemy, Point2D loc, int hp, int damage, int hitSpeed, int range) {
        super(type, isEnemy, loc);
        this.hp = hp;
        this.damage = damage;
        this.hitSpeed = hitSpeed;
        this.range = range;
        isActivated = false;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getHitSpeed() {
        return hitSpeed;
    }

    public int getRange() {
        return range;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHitSpeed(int hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    @Override
    public void boost(double rate) {
        setDamage((int) (getDamage() * rate));
        setHitSpeed((int) (getHitSpeed() * rate));
    }

    @Override
    public void undoBoost(double rate) {
        setDamage((int) (getDamage() / rate));
        setHitSpeed((int) (getHitSpeed() / rate));
    }

    /**
     * GameManager will activate this tower in 3 conditions:
     * 1) it's queen tower,
     * 2) it's king tower and one of the queen towers is damaged
     * 3) it's king tower and has been damaged
     */
    @Override
    public void activate() {
        isActivated = true;
        startAttackingTimeline();
    }

    /**
     * Creates a timeline to call the attack() method every 'hitSpeed' seconds (relative to the tower's hit speed)
     * Will be canceled if it's enemy target dies
     */
    public void startAttackingTimeline() {
        attackingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(hitSpeed), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        attack();
                    }
                })
        );
        attackingTimeline.setCycleCount(Timeline.INDEFINITE);
        attackingTimeline.play();
    }

    public void attack() {
        Entity targetEnemy = findEnemy();
        if (targetEnemy != null) {
            targetEnemy.getAttacked(this.damage);
        }
    }

    @Override
    public void getAttacked(int damage) {
        hp -= damage;
        if (hp == 0) {
            setDead(true);
            stop();
        }
    }

    /**
     * Finds an enemy in its range
     */
    private Entity findEnemy() {
        int x1 = (int) getLocation().getX();
        int y1 = (int) getLocation().getY();
        int x2, y2;
        Entity entity;
        for (int i = 1; i <= range; i++) {
            x2 = x1 + i;
            y2 = y1;
            if (x2 < gameData.rowCount) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity))
                    return entity;
            }

            x2 = x1 - i;
            if (x2 >= 0) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity))
                    return entity;
            }

            x2 = x1;
            y2 = y1 + i;
            if (y2 < gameData.colCount) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity))
                    return entity;;
            }

            y2 = y1 - i;
            if (y2 <= 0) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity))
                    return entity;
            }
        }
        return null;
    }

    /**
     * will also be called by the gameManager when the game has ended
     */
    public void stop() {
        if (attackingTimeline != null && attackingTimeline.getStatus().equals(Animation.Status.RUNNING))
            attackingTimeline.stop();
    }
}
