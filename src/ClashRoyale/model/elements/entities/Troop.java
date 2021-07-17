package ClashRoyale.model.elements.entities;

import ClashRoyale.model.elements.Direction;
import ClashRoyale.model.elements.TargetType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;


public class Troop extends Card {

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
        loadImages();
    }

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
        setDamage((int) (getDamage() * rate));     // boosting the damage
        setHitSpeed((int) (getHitSpeed() * rate)); // boosting the hitSpeed
        switch (speed) {                           // boosting the speed
            case SLOW -> setSpeed(Speed.MEDIUM);
            case MEDIUM -> setSpeed(Speed.FAST);
            case FAST -> setSpeed(Speed.VERY_FAST);
        }
    }

    @Override
    public void undoBoost(double rate) {
        setDamage((int) (getDamage() / rate));     // undo the boost on damage
        setHitSpeed((int) (getHitSpeed() / rate)); // undo the boost on hitSpeed
        switch (speed) {                           // undo the boost on speed
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
        if (targetEnemy != null && !targetEnemy.isDead()) {
            setAttacking(true);
            targetEnemy.getAttacked(this.damage);
            if (isAreaSplash) {
                areaSplash();
            }
            return;
        }
        // if this line is reached, it means that the target has died and the troop should start walking again
        setAttacking(false);
        attackingTimeline.stop();
        startMovingTimeline();
    }

    // attacks all the enemies surrounding this troop in 1 tile radius
    public void areaSplash() {
        int x = (int) getLocation().getX(), y = (int) getLocation().getY();
        // finding all the 8 entities around this troop
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
            setDead(true);
            stop(); // stop moving or attacking
        }
    }

    public void move() { // moves one step closer to the enemy's field and meanwhile, checks for enemies to kill
        targetEnemy = findEnemy();
        if (targetEnemy != null) {
            movingTimeline.stop(); // stands still and starts attacking the enemy that it just found
            startAttackingTimeline();
            return;
        }
        Point2D possibleLoc;
        if (isEnemy()) possibleLoc = getLocation().add(directionToPoint2D(Direction.LEFT));  // red team moves one step to the left
        else           possibleLoc = getLocation().add(directionToPoint2D(Direction.RIGHT)); // blue team moves one step to the right

        if (gameData.map[(int) possibleLoc.getX()][(int) possibleLoc.getY()] == null || getType() == Type.BABY_DRAGON) { // noting that baby dragon can fly over buildings or the river
            setLocation(possibleLoc);
        } else if (gameData.map[(int) possibleLoc.getX()][(int) possibleLoc.getY()] instanceof River) { // it has reached the river

            Direction direction = findDirectionToBridge(); // finds the direction to the closest bridge, so that it can cross the river
            possibleLoc = getLocation().add(directionToPoint2D(direction));
            setLocation(possibleLoc);

        } else if (gameData.map[(int) possibleLoc.getX()][(int) possibleLoc.getY()] instanceof Building ||
                gameData.map[(int) possibleLoc.getX()][(int) possibleLoc.getY()] instanceof Tower) { // it has reached a building or a tower

            if (getLocation().getX() >= 8) // it's in the lower half of the map (it's more realistic to move down)
                possibleLoc = getLocation().add(directionToPoint2D(Direction.DOWN));
            else // it's in the upper half of the map (it's more realistic to move up)
                possibleLoc = getLocation().add(directionToPoint2D(Direction.UP));

            setLocation(possibleLoc);
        }
        /* else -> there's another troop (which is not an enemy) in the possible location
           this troop will not move and waits for the other troop to pass by
         */
    }

    // finds an enemy in its range
    private Entity findEnemy() {
        int x1 = (int) getLocation().getX(); // current x coordinate
        int y1 = (int) getLocation().getY(); // current y coordinate
        int x2, y2; // x,y coordinates to check
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
                    return entity;
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

    // finds the closest bridge and decides which direction is better
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
        double speedInSeconds = 1; // default value (only for avoiding compile error -_-)
        switch (speed) {
            case SLOW ->      speedInSeconds = 0.75; // these numbers may be changed later (for better smoothness)
            case MEDIUM ->    speedInSeconds = 0.5;
            case FAST ->      speedInSeconds = 0.25;
            case VERY_FAST -> speedInSeconds = 0.15;
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

    private Point2D directionToPoint2D(Direction direction) {
        return switch (direction) {
            case UP -> new Point2D(0, 1);
            case DOWN -> new Point2D(0, -1);
            case LEFT -> new Point2D(-1, 0);
            case RIGHT -> new Point2D(1, 0);
        };
    }

    // stops all the running timelines
    public void stop() {
        if (attackingTimeline != null && attackingTimeline.getStatus().equals(Animation.Status.RUNNING))
            attackingTimeline.stop();
        if (movingTimeline != null && movingTimeline.getStatus().equals(Animation.Status.RUNNING))
            movingTimeline.stop();
    }

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

    private void loadArchers() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/archers/archer_standing_enemy.png")));
    }

    private void loadBabyDragon() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/baby-dragon/babydragon_fly.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/baby-dragon/babydragon_fly_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/baby-dragon/babydragon_attack.gif")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/baby-dragon/babydragon_attack_enemy.gif")));
    }

    private void loadBarbarians() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/barbarians/barbarian_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/barbarians/barbarian_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/barbarians/barbarian_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/barbarians/barbarian_standing_enemy.png")));
    }

    private void loadGiant() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/giant/giant_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/giant/giant_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/giant/giant_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/giant/giant_standing_enemy.png")));
    }

    private void loadMiniPekka() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/mini-pekka/pekka_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/mini-pekka/pekka_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/mini-pekka/pekka_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/mini-pekka/pekka_standing_enemy.png")));
    }

    private void loadValkyrie() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/valkyrie/valkyrie_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/valkyrie/valkyrie_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/valkyrie/valkyrie_attacking.gif")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/valkyrie/valkyrie_attacking_enemy.gif")));
    }

    private void loadWizard() {
        images.replace("RUNNING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard_running.gif")));
        images.replace("RUNNING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard_running_enemy.gif")));
        images.replace("ATTACKING", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard_standing.png")));
        images.replace("ATTACKING_ENEMY", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/troops/wizard/wizard_standing_enemy.png")));
    }

}
