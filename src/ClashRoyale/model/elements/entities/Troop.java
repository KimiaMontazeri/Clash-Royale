package ClashRoyale.model.elements.entities;

import ClashRoyale.model.elements.Direction;
import ClashRoyale.model.elements.TargetType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * This class models a troop that will be placed on the map
 * and some AI is used to make it move closer to enemies and kill them
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class Troop extends Card {

    /**
     * a ratio that shows the speed of a troop
     */
    public enum Speed {
        SLOW, MEDIUM, FAST, VERY_FAST // VERY_FAST is used for when the troop's speed is boosted
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

    /**
     * Constructs a troop
     * @param type type
     * @param isEnemy determines whether its on the blue team or not
     * @param hp hp
     * @param damage damage
     * @param targetType target type that it can attack
     * @param speed speed
     * @param hitSpeed hit speed
     * @param range range
     * @param isAreaSplash whether it is area splash or not (damages the area around this troop)
     */
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
        images = new HashMap<>();
        loadImages();
    }

    /**
     * @return current image of this troop
     */
    @Override
    public Image getCurrentImage() {
        if (isEnemy()) {
            if (isAttacking())
                return images.get("ATTACKING_ENEMY");
            return images.get("RUNNING_ENEMY");
        } else {
            if (isAttacking())
                return images.get("ATTACKING");
            return images.get("RUNNING");
        }
    }

    /**
     * @return hp
     */
    public int getHp() {
        return hp;
    }

    /**
     * @return damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @return target type
     */
    public TargetType getTargetType() {
        return targetType;
    }

    /**
     * @return running speed
     */
    public Speed getSpeed() {
        return speed;
    }

    /**
     * @return hit speed
     */
    public double getHitSpeed() {
        return hitSpeed;
    }

    /**
     * @return range
     */
    public double getRange() {
        return range;
    }

    /**
     * @return is area splash or not
     */
    public boolean isAreaSplash() {
        return isAreaSplash;
    }

    /**
     * @param hp hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @param damage damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * @param hitSpeed hit speed
     */
    public void setHitSpeed(double hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    /**
     * @param speed running speed
     */
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    /**
     * Boots this troop (increases its speed, hit speed, and damage)
     * @param rate rate of boosting
     */
    @Override
    public void boost(double rate) {
        setDamage((int) (getDamage() * rate));     // boosting the damage
        setHitSpeed((int) (getHitSpeed() * rate)); // boosting the hitSpeed
        switch (speed) {                           // boosting the speed
            case SLOW -> setSpeed(Speed.MEDIUM);
            case MEDIUM -> setSpeed(Speed.FAST);
            case FAST -> setSpeed(Speed.VERY_FAST);
        }
        movingTimeline.stop();
        startMovingTimeline();
    }

    /**
     * Undo the boost on this troop
     * @param rate rate of boosting
     */
    @Override
    public void undoBoost(double rate) {
        setDamage((int) (getDamage() / rate));     // undo the boost on damage
        setHitSpeed((int) (getHitSpeed() / rate)); // undo the boost on hitSpeed
        switch (speed) {                           // undo the boost on speed
            case MEDIUM -> setSpeed(Speed.SLOW);
            case FAST -> setSpeed(Speed.MEDIUM);
            case VERY_FAST -> setSpeed(Speed.FAST);
        }
        movingTimeline.stop();
        startMovingTimeline();
    }

    /**
     * Activates this troop
     */
    @Override
    public void activate() {
        startMovingTimeline();
        Card.playSound(getType());
    }

    /**
     * Attacks target enemy
     */
    public void attack() {
        if (targetEnemy != null && !targetEnemy.isDead()) {
            setAttacking(true);
            targetEnemy.getAttacked(this.damage);
            if (isAreaSplash) {
                areaSplash();
            }
            return;
        }
        // the troop should start walking again
        setAttacking(false);
        attackingTimeline.stop();
        startMovingTimeline();
    }

    /**
     * attacks all the enemies surrounding this troop in 1 tile radius
     */
    public void areaSplash() {
        Entity[] entities = new Entity[8];
        int x = (int) getLocation().getX(), y = (int) getLocation().getY(), index = -1;

        if (gameData.isInsideMap(x - 1, y - 1))
            entities[++index] = gameData.map[x - 1][y - 1];
        if (gameData.isInsideMap(x + 1, y + 1))
            entities[++index] = gameData.map[x + 1][y + 1];
        if (gameData.isInsideMap(x + 1, y - 1))
            entities[++index] = gameData.map[x + 1][y - 1];
        if (gameData.isInsideMap(x - 1, y + 1))
            entities[++index] = gameData.map[x - 1][y + 1];
        if (gameData.isInsideMap(x + 1, y))
            entities[++index] = gameData.map[x + 1][y];
        if (gameData.isInsideMap(x - 1, y))
            entities[++index] = gameData.map[x - 1][y];
        if (gameData.isInsideMap(x, y + 1))
            entities[++index] = gameData.map[x][y + 1];
        if (gameData.isInsideMap(x, y - 1))
            entities[++index] = gameData.map[x][y - 1];

        for (Entity e : entities) {
            if (e != null && e != targetEnemy && super.isTargetType(e, targetType) && super.canAttack(e))
                e.getAttacked(this.damage);
        }
    }

    /**
     * Gets attacked
     * @param damage damage done on this entity
     */
    @Override
    public void getAttacked(int damage) {
        this.hp -= damage;
        if (this.hp <= 0) { // has died
            gameData.map[(int) getLocation().getX()][(int) getLocation().getY()] = null;
            setDead(true);
            stop(); // stop moving or attacking
        }
    }

    /**
     * Update the troop in the map
     * @param prevLoc previous location
     */
    public void updateTroopInMap(Point2D prevLoc) {
        gameData.map[(int) prevLoc.getX()][(int) prevLoc.getY()] = null;
        gameData.map[(int) getLocation().getX()][(int) getLocation().getY()] = this;
    }

    /**
     * Moves this troop
     */
    public void move() {
        targetEnemy = findEnemy();
        if (targetEnemy != null) {
            movingTimeline.stop(); // stands still and starts attacking the enemy that it just found
            startAttackingTimeline();
            return;
        }

        int x = (int) getLocation().getX(), y = (int) getLocation().getY();
        Point2D possibleLoc, prevLoc = getLocation();
        Direction possibleDirection;

        if (getType() != Type.BABY_DRAGON) {
            if (isEnemy() && (x == 4 || x == 13) && y >= 14) {
                possibleDirection = Direction.LEFT;
            }
            else if (!isEnemy() && (x == 4 || x == 13) && y <= 17) {
                possibleDirection = Direction.RIGHT;
            }
            else if (y == 15 || y == 16) {
                if (x <= 8) setLocation(new Point2D(4, y));
                else        setLocation(new Point2D(13, y));
                updateTroopInMap(prevLoc);
                return;
            }
            else if (isEnemy() && y >= 14) {
                if (x < 4)
                    possibleDirection = Direction.DOWN;
                else if (x <= 8)
                    possibleDirection = Direction.UP;
                else if (x > 13)
                    possibleDirection = Direction.UP;
                else
                    possibleDirection = Direction.DOWN;
            }
            else if (!isEnemy() && y <= 17) {
                if (x < 4)
                    possibleDirection = Direction.DOWN;
                else if (x <= 8)
                    possibleDirection = Direction.UP;
                else if (x > 13)
                    possibleDirection = Direction.UP;
                else
                    possibleDirection = Direction.DOWN;
            }
            else {
                possibleDirection = findDirectionToClosestTower();
            }

            possibleLoc = getLocation().add(directionToPoint2D(possibleDirection));
            Entity nextCell = gameData.map[(int) possibleLoc.getX()][(int) possibleLoc.getY()];
            if (nextCell instanceof Troop) {
                if (possibleDirection == Direction.LEFT)
                    possibleDirection = Direction.UP;
                else if (possibleDirection == Direction.RIGHT)
                    possibleDirection = Direction.DOWN;
                else if (possibleDirection == Direction.UP)
                    possibleDirection = Direction.RIGHT;
                else if (possibleDirection == Direction.DOWN)
                    possibleDirection = Direction.LEFT;
            } else if (nextCell instanceof River) {
                possibleDirection = findDirectionToBridge();
            }
        }
        else {
            if (isEnemy()) possibleDirection = Direction.LEFT;
            else           possibleDirection = Direction.RIGHT;
        }

        possibleLoc = handleGoingOffscreen(getLocation().add(directionToPoint2D(possibleDirection)));
        setLocation(possibleLoc);
        updateTroopInMap(prevLoc);
    }

    /**
     * @return direction to the closest tower
     */
    private Direction findDirectionToClosestTower() {
        Tower t1, t2, t3, closestTower;
        // getting towers from gameData
        if (isEnemy()) {
            t1 = gameData.blueQueenTowerUp;
            t2 = gameData.blueKingTower;
            t3 = gameData.blueQueenTowerDown;
        } else  {
            t1 = gameData.redQueenTowerUp;
            t2 = gameData.redKingTower;
            t3 = gameData.redQueenTowerDown;
        }

        // calculating distance
        int x1 = (int) t1.getLocation().getX();
        int x2 = (int) t2.getLocation().getX();
        int x3 = (int) t3.getLocation().getX();
        int dx1 = Math.abs((int) getLocation().getX() - x1);
        int dx2 = Math.abs((int) getLocation().getX() - x2);
        int dx3 = Math.abs((int) getLocation().getX() - x3);

        // finding closest tower
        if (dx1 < dx2 && dx1 < dx3 && !t1.isDead())
            closestTower = t1;
        else if (dx2 < dx1 && dx2 < dx3 && !t2.isDead())
            closestTower = t2;
        else if (dx3 < dx1 && dx3 < dx2 && !t3.isDead())
            closestTower = t3;
        else closestTower = t2;

        // returning the direction to the closest tower
        if (getLocation().getX() < closestTower.getLocation().getX())
            return Direction.DOWN;
        if (getLocation().getX() == closestTower.getLocation().getX()) {
            if (isEnemy())
                return Direction.LEFT;
            return Direction.RIGHT;
        }
        if (getLocation().getX() > closestTower.getLocation().getX())
            return Direction.UP;
        return null;
    }

    /**
     * handles going offscreen
     * @param possibleLoc possible location
     * @return better location to go to
     */
    private Point2D handleGoingOffscreen(Point2D possibleLoc) {
        // troops can't go to the corners of the map
        int x = (int) possibleLoc.getX(), y = (int) possibleLoc.getY();
        if (x <= 0 || x >= gameData.rowCount - 1) {
            // handling x
            if (x <= 0)       possibleLoc = getLocation().add(directionToPoint2D(Direction.DOWN));
            else              possibleLoc = getLocation().add(directionToPoint2D(Direction.UP));

            // handling y
            if (y <= 15)      possibleLoc = possibleLoc.add(directionToPoint2D(Direction.RIGHT)); // or left?
            else              possibleLoc = possibleLoc.add(directionToPoint2D(Direction.LEFT));
        }

        if (y <= 0 || y >= gameData.colCount - 1) {
            // handling y
            if (y <= 0)       possibleLoc = getLocation().add(directionToPoint2D(Direction.RIGHT));
            else              possibleLoc = getLocation().add(directionToPoint2D(Direction.LEFT));

            // handling x
            if (x <= 8)       possibleLoc = possibleLoc.add(directionToPoint2D(Direction.DOWN));
            else              possibleLoc = possibleLoc.add(directionToPoint2D(Direction.UP));
        }
        return possibleLoc;
    }

    /**
     * finds an enemy in its range
     * @return enemy
     */
    private Entity findEnemy() {
        int x1 = (int) getLocation().getX(); // current x coordinate
        int y1 = (int) getLocation().getY(); // current y coordinate
        int x2, y2; // x,y coordinates to check
        Entity entity;
        for (int i = 1; i <= range; i++) {
            x2 = x1 + i;
            y2 = y1;
            if (gameData.isInsideMap(x2, y2)) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            x2 = x1 - i;
            if (gameData.isInsideMap(x2, y2)) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            x2 = x1;
            y2 = y1 + i;
            if (gameData.isInsideMap(x2, y2)) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            y2 = y1 - i;
            if (gameData.isInsideMap(x2, y2)) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            x2 = x1 + i;
            y2 = y1 + i;
            if (gameData.isInsideMap(x2, y2)) {
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            x2 = x1 - i;
            y2 = y1 - i;
            if (gameData.isInsideMap(x2, y2)) {
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }
        }
        return null;
    }

    /**
     * finds the closest bridge and decides which direction is better
     * @return direction
     */
    private Direction findDirectionToBridge() {
        int dx1, dx2;
        dx1 = Math.abs((int) (getLocation().getX() - gameData.bridgeUp.getX()));
        dx2 = Math.abs((int) (getLocation().getX() - gameData.bridgeDown.getX()));

        if (dx1 > dx2)
            return Direction.UP;
        return Direction.DOWN;
    }

    /**
     * Creates a timeline to call the move() method every 'speed' seconds (relative to the troop's speed)
     * Will be canceled in getAttacked() method when the troop dies (hp == 0)
     */
    public void startMovingTimeline() {
        double speedInSeconds = 1; // default value (only for avoiding compile error -_-)
        switch (speed) {
            case SLOW ->      speedInSeconds = 0.8; // these numbers may be changed later (for better smoothness)
            case MEDIUM ->    speedInSeconds = 0.6;
            case FAST ->      speedInSeconds = 0.4;
            case VERY_FAST -> speedInSeconds = 0.2;
        }

        movingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(speedInSeconds), event -> move()) // calls the move() method each "speedInSeconds"
        );
        movingTimeline.setCycleCount(Timeline.INDEFINITE); // will be stopped when the troop dies
        movingTimeline.play();
    }

    /**
     * Creates a timeline to call the attack() method every 'hitSpeed' seconds (relative to the troop's hit speed)
     * Will be canceled in the move() method if it's enemy target dies
     */
    public void startAttackingTimeline() {
        attackingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(hitSpeed), event -> attack()) // calls the attack method each "hitSpeed" seconds
        );
        attackingTimeline.setCycleCount(Timeline.INDEFINITE); // will be stopped if the target dies or the troop itself dies
        attackingTimeline.play();
    }

    /**
     * Converts direction to point2D
     * @param direction direction to convert
     * @return point2D
     */
    private Point2D directionToPoint2D(Direction direction) {
        return switch (direction) {
            case UP -> new Point2D(-1, 0);
            case DOWN -> new Point2D(1, 0);
            case LEFT -> new Point2D(0, -1);
            case RIGHT -> new Point2D(0, 1);
        };
    }

    /**
     * Stops this troop (stops all the running timelines)
     */
    public void stop() {
        if (attackingTimeline != null)
            attackingTimeline.stop();
        if (movingTimeline != null)
            movingTimeline.stop();
    }

    /**
     * Loads images for this troop
     */
    private void loadImages() {
        images.put("RUNNING", null);
        images.put("RUNNING_ENEMY", null);
        images.put("ATTACKING", null);
        images.put("ATTACKING_ENEMY", null);
        switch (getType()) {
            case ARCHER -> loadArchers();
            case BABY_DRAGON -> loadBabyDragon();
            case BARBARIANS -> loadBarbarians();
            case GIANT -> loadGiant();
            case MINI_PEKKA -> loadMiniPekka();
            case VALKYRIE -> loadValkyrie();
            case WIZARD -> loadWizard();
        }
    }

    /**
     * Loads archer images
     */
    private void loadArchers() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer_standing_enemy.png")));
    }

    /**
     * Loads baby dragon images
     */
    private void loadBabyDragon() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/baby-dragon/babydragon_fly.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/baby-dragon/babydragon_fly_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/baby-dragon/babydragon_attack.gif")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/baby-dragon/babydragon_attack_enemy.gif")));
    }

    /**
     * Loads barbarian images
     */
    private void loadBarbarians() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/barbarians/barbarian_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/barbarians/barbarian_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/barbarians/barbarian_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/barbarians/barbarian_standing_enemy.png")));
    }

    /**
     * Loads giant images
     */
    private void loadGiant() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/giant/giant_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/giant/giant_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/giant/giant_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/giant/giant_standing_enemy.png")));
    }

    /**
     * Loads mini pekka images
     */
    private void loadMiniPekka() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/mini-pekka/pekka_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/mini-pekka/pekka_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/mini-pekka/pekka_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/mini-pekka/pekka_standing_enemy.png")));
    }

    /**
     * Loads valkyrie images
     */
    private void loadValkyrie() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/valkyrie/valkyrie_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/valkyrie/valkyrie_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/valkyrie/valkyrie_attacking.gif")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/valkyrie/valkyrie_attacking_enemy.gif")));
    }

    /**
     * Loads wizard images
     */
    private void loadWizard() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard_standing_enemy.png")));
    }

}
