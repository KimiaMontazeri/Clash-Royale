package ClashRoyale.model.elements.entities;

import ClashRoyale.model.elements.Direction;
import ClashRoyale.model.elements.TargetType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.util.Duration;


public class Troop extends Card {

    public enum Speed {
        SLOW, MEDIUM, FAST, VERY_FAST
    }

    // attributes
    private int hp;
    private int damage;
    private final TargetType targetType;
    private Speed speed;
    private double hitSpeed;
    private final int range;
    private final boolean isAreaSplash;

    private Timeline movingTimeline, attackingTimeline;
    private Entity targetEnemy;

    public Troop(Type type, boolean isEnemy, int hp, int damage, TargetType targetType, Speed speed, double hitSpeed,
                 int range, boolean isAreaSplash) {
        super(type, isEnemy);
        this.hp = hp;
        this.damage = damage;
        this.targetType = targetType;
        this.speed = speed;
        this.hitSpeed = hitSpeed;
        this.range = range; // 1 -> melee, other -> x number of tiles will be checked for attacking
        this.isAreaSplash = isAreaSplash;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public Speed getSpeed() {
        return speed;
    }

    public double getHitSpeed() {
        return hitSpeed;
    }

    public double getRange() {
        return range;
    }

    public boolean isAreaSplash() {
        return isAreaSplash;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setHitSpeed(double hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    @Override
    public void boost(double rate) {
        setDamage((int) (getDamage() * rate));
        setHitSpeed((int) (getHitSpeed() * rate));
        switch (speed) {
            case SLOW -> setSpeed(Speed.MEDIUM);
            case MEDIUM -> setSpeed(Speed.FAST);
            case FAST -> setSpeed(Speed.VERY_FAST);
        }
    }

    @Override
    public void undoBoost(double rate) {
        setDamage((int) (getDamage() / rate));
        setHitSpeed((int) (getHitSpeed() / rate));
        switch (speed) {
            case MEDIUM -> setSpeed(Speed.SLOW);
            case FAST -> setSpeed(Speed.MEDIUM);
            case VERY_FAST -> setSpeed(Speed.FAST);
        }
    }

    @Override
    public void activate() {
        startMovingTimeline();
    }

    public void attack() {
        if (targetEnemy != null) {
            targetEnemy.getAttacked(this.damage);
            if (isAreaSplash) {
                areaSplash();
            }
            return;
        }
        attackingTimeline.stop();
        startMovingTimeline();
    }

    public void areaSplash() {
        int x = (int) getLocation().getX(), y = (int) getLocation().getY();
        Entity[] entities = new Entity[8];
        entities[0] = gameData.map[x - 1][y - 1];
        entities[1] = gameData.map[x][y - 1];
        entities[2] = gameData.map[x + 1][y + 1];
        entities[3] = gameData.map[x - 1][y];
        entities[4] = gameData.map[x + 1][y];
        entities[5] = gameData.map[x - 1][y - 1];
        entities[6] = gameData.map[x][y - 1];
        entities[7] = gameData.map[x + 1][y - 1];

        for (Entity e : entities) {
            if (e != null && e != targetEnemy && super.isTargetType(e, targetType) && super.canAttack(e))
                e.getAttacked(this.damage);
        }
    }

    @Override
    public void getAttacked(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) { // has died
            // stop moving or attacking
            setDead(true);
        }
    }

    public void move() {
        targetEnemy = findEnemy();
        if (targetEnemy != null) {
            movingTimeline.stop();
            startAttackingTimeline();
            return;
        }
        Point2D possibleLoc;
        if (isEnemy()) possibleLoc = getLocation().add(directionToPoint2D(Direction.LEFT));
        else           possibleLoc = getLocation().add(directionToPoint2D(Direction.RIGHT));

        if (gameData.map[(int) possibleLoc.getX()][(int) possibleLoc.getY()] == null) {
            setLocation(possibleLoc);
        } else if (gameData.map[(int) possibleLoc.getX()][(int) possibleLoc.getY()] instanceof River) {
            Direction direction = findDirectionToBridge();
            possibleLoc = getLocation().add(directionToPoint2D(direction));
            setLocation(possibleLoc);
        }
    }

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
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            x2 = x1 - i;
            if (x2 >= 0) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            x2 = x1;
            y2 = y1 + i;
            if (y2 < gameData.colCount) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;;
            }

            y2 = y1 - i;
            if (y2 <= 0) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }
        }
        return null;
    }

    private Direction findDirectionToBridge() {
        int dy1, dy2;
        dy1 = Math.abs((int) (getLocation().getY() - gameData.bridgeUp.getY()));
        dy2 = Math.abs((int) (getLocation().getY() - gameData.bridgeDown.getY()));

        if (dy1 > dy2)
            return Direction.DOWN;
        return Direction.UP;
    }

    /**
     * Creates a timeline to call the move() method every 'speed' seconds (relative to the troop's speed)
     * Will be canceled in getAttacked() method when the troop dies (hp == 0)
     */
    public void startMovingTimeline() {
        double speedInSeconds = 1;
        switch (speed) {
            case SLOW ->      speedInSeconds = 0.75;
            case MEDIUM ->    speedInSeconds = 0.5;
            case FAST ->      speedInSeconds = 0.25;
            case VERY_FAST -> speedInSeconds = 0.15;
        }

        movingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(speedInSeconds), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        move();
                    }
                })
        );
        movingTimeline.setCycleCount(Timeline.INDEFINITE);
        movingTimeline.play();
    }

    /**
     * Creates a timeline to call the attack() method every 'hitSpeed' seconds (relative to the troop's hit speed)
     * Will be canceled in the move() method if it's enemy target dies
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

    private Point2D directionToPoint2D(Direction direction) {
        return switch (direction) {
            case UP -> new Point2D(0, 1);
            case DOWN -> new Point2D(0, -1);
            case LEFT -> new Point2D(-1, 0);
            case RIGHT -> new Point2D(1, 0);
        };
    }

    public void stop() {
        if (attackingTimeline != null && attackingTimeline.getStatus().equals(Animation.Status.RUNNING))
            attackingTimeline.stop();
        if (movingTimeline != null && movingTimeline.getStatus().equals(Animation.Status.RUNNING))
            movingTimeline.stop();
    }

}
