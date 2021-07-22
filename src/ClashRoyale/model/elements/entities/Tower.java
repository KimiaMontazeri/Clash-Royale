package ClashRoyale.model.elements.entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * The game's towers that are placed on the map at the start of the game
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class Tower extends Entity {

    private int hp, damage;
    private double hitSpeed;
    private final int range;
    private boolean isActivated, isAttacked;
    // isAttacked is mostly used for KingTower (Because KingTower will be activated if it gets attacked, or if one of the queen towers get damaged)

    private Timeline attackingTimeline;
    private Entity targetEnemy;

    /**
     * Constructs a tower
     * @param type type of this tower
     * @param isEnemy team blue or red
     * @param loc location
     * @param hp hp
     * @param damage damage
     * @param hitSpeed hit speed
     * @param range range of detecting enemy
     */
    public Tower(Type type, boolean isEnemy, Point2D loc, int hp, int damage, double hitSpeed, int range) {
        super(type, isEnemy, loc);
        this.hp = hp;
        this.damage = damage;
        this.hitSpeed = hitSpeed;
        this.range = range;
        isActivated = isAttacked = false;
        images = new HashMap<>();
        loadImages();
    }

    /**
     * Loads the image of this tower (corresponding to its type)
     */
    private void loadImages() {
        if (getType() == Type.KING_TOWER) {
            if (isEnemy())
                images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/towers/RedKingTower.png")));
            else
                images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/towers/BlueKingTower.png")));
        } else if (getType() == Type.QUEEN_TOWER) {
            if (isEnemy())
                images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/towers/RedQueenTower.png")));
            else
                images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/towers/BlueQueenTower.png")));
        }
        images.put("DEAD", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/explosions/black-smoke.png")));
    }

    /**
     * @return the current image of this tower
     */
    @Override
    public Image getCurrentImage() {
        if (isDead())
            return images.get("DEAD");
        return images.get("DEFAULT");
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
     * @return hit speed
     */
    public double getHitSpeed() {
        return hitSpeed;
    }

    /**
     * @return range of this tower
     */
    public int getRange() {
        return range;
    }

    /**
     * @return true if it's activated
     */
    public boolean isActivated() {
        return isActivated;
    }

    /**
     * @return true if it has been attacked some while ago
     */
    public boolean isAttacked() {
        return isAttacked;
    }

    /**
     * @param hp hp
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * @param damage tower's damage
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
     * Boosts this tower (increases its damage and hit speed)
     * @param rate rate of boosting
     */
    @Override
    public void boost(double rate) {
        setDamage((int) (getDamage() * rate));
        setHitSpeed((int) (getHitSpeed() * rate));
    }

    /**
     * Undo boost on this tower
     * @param rate rate of boosting
     */
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
                new KeyFrame(Duration.seconds(hitSpeed), event -> attack()) // calls the attack method each "hitSpeed" time
        );
        attackingTimeline.setCycleCount(Timeline.INDEFINITE); // the timeline won't be stopped until the game finishes or the tower gets destroyed
        attackingTimeline.play();
    }

    /**
     * Attacks target enemy until it can kill it
     * Then it will search for another enemy in its range
     */
    public void attack() {
        if (targetEnemy != null && !targetEnemy.isDead()) {
            targetEnemy.getAttacked(this.damage);
            setAttacking(true);
            return; // won't search for other enemies until the current target dies
        }
        targetEnemy = findEnemy();
        setAttacking(false);
    }

    /**
     * Gets attacked
     * @param damage damage done on this tower
     */
    @Override
    public void getAttacked(int damage) {
        isAttacked = true;
        hp -= damage;
        if (hp <= 0) {
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
            if (gameData.isInsideMap(x2, y2)) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity) && !(entity instanceof Spell))
                    return entity;
            }

            x2 = x1 - i;
            if (gameData.isInsideMap(x2, y2)) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity) && !(entity instanceof Spell))
                    return entity;
            }

            x2 = x1;
            y2 = y1 + i;
            if (gameData.isInsideMap(x2, y2)) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity) && !(entity instanceof Spell))
                    return entity;;
            }

            y2 = y1 - i;
            if (gameData.isInsideMap(x2, y2)) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity) && !(entity instanceof Spell))
                    return entity;
            }

            x2 = x1 + i;
            y2 = y1 + i;
            if (gameData.isInsideMap(x2, y2)) {
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity) && !(entity instanceof Spell))
                    return entity;
            }

            x2 = x1 - i;
            y2 = y1 - i;
            if (gameData.isInsideMap(x2, y2)) {
                entity = gameData.map[x2][y2];
                if (super.canAttack(entity) && !(entity instanceof Spell))
                    return entity;
            }
        }
        return null;
    }

    /**
     * stops this tower
     */
    public void stop() {
        if (attackingTimeline != null && attackingTimeline.getStatus().equals(Animation.Status.RUNNING))
            attackingTimeline.stop();
    }
}
