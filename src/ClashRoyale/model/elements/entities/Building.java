package ClashRoyale.model.elements.entities;

import ClashRoyale.model.elements.TargetType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.HashMap;

/**
 * This class is a building that can be placed on the map
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class Building extends Card {

    private int hp;
    private int damage;
    private int maxDamage; // this is used for inferno tower, since its damage increases when it finds a target
    private double hitSpeed;
    private final TargetType targetType;
    private final int range;
    private int lifetime;

    private Timeline attackingTimeline; // a timeline to model the building's lifetime
    private Entity targetEnemy;         // the building's current target

    /**
     * Creates a building
     * @param type entity type
     * @param isEnemy determines if it's on the read team or not
     * @param hp number of lives
     * @param damage value for its damage
     * @param hitSpeed hit speed
     * @param targetType target type that it can attack
     * @param range the radius that is checked for finding enemies
     * @param lifetime duration of the building
     */
    public Building(Type type, boolean isEnemy, int hp, int damage,
                    double hitSpeed, TargetType targetType, int range, int lifetime) {
        super(type, isEnemy);
        this.hp = hp;
        this.damage = damage;
        this.maxDamage = damage;
        this.hitSpeed = hitSpeed;
        this.targetType = targetType;
        this.range = range;
        this.lifetime = lifetime;
        images = new HashMap<>();
        loadImages();
    }

    /**
     * loads the image of this building depending on its type
     */
    private void loadImages() {
        if (getType() == Type.CANNON) {
            if (isEnemy())
                images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/buildings/canon-red-team.png")));
            else
                images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/buildings/canon-blue-team.png")));
        } else if (getType() == Type.INFERNO_TOWER) {
            images.put("DEFAULT", new Image(getClass().getResourceAsStream("/ClashRoyale/resources/buildings/inferno-tower.png")));
        }
    }

    /**
     * @return image of this building
     */
    @Override
    public Image getCurrentImage() {
        return images.get("DEFAULT");
    }

    /**
     * @return tower's hp
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
     * @return maxDamage
     */
    public int getMaxDamage() {
        return maxDamage;
    }

    /**
     * @return hit speed
     */
    public double getHitSpeed() {
        return hitSpeed;
    }

    /**
     * @return cardType
     */
    public TargetType getTarget() {
        return targetType;
    }

    /**
     * @return enemy detecting range
     */
    public double getRange() {
        return range;
    }

    /**
     * @return lifetime
     */
    public int getLifetime() {
        return lifetime;
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
     * @param maxDamage maximum damage
     */
    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    /**
     * @param hitSpeed hit speed
      */
    public void setHitSpeed(double hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    /**
     * boosts this building by increasing its damage and hit speed
     * @param rate rate of boosting
     */
    @Override
    public void boost(double rate) { // used for when Rage spell is activated on this building
        setDamage((int) (getDamage() * rate));
        setHitSpeed((int) (getHitSpeed() * rate));
    }

    /**
     * undo boosts on this building
     * @param rate rate of boosting
     */
    @Override
    public void undoBoost(double rate) { // used for when Rage spell is de-activated on this building
        setDamage((int) (getDamage() / rate));
        setHitSpeed((int) (getHitSpeed() / rate));
    }

    /**
     * Activate this building
     */
    @Override
    public void activate() {
        startAttackingTimeline();
        Card.playSound(getType());
    }

    /**
     * Creates a timeline to call the attack() method every 'hitSpeed' seconds (relative to the building's hit speed)
     * Will be canceled if it's enemy target dies
     */
    public void startAttackingTimeline() {
        // the timeline will call the attack() and decrementLifetime() method each "hitSpeed" time
        attackingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(hitSpeed), event -> {
                    attack();
                    decrementLifetime();
                })
        );
        attackingTimeline.setCycleCount((int) (lifetime / hitSpeed)); // the number of times that this timeline is going to work
        attackingTimeline.play();
    }

    /**
     * Decrement the lifetime of this building
     */
    public void decrementLifetime() {
        lifetime--;
        if (lifetime == 0) {
            setDead(true);
            stop(); // stops the timeline
        }
    }

    /**
     * Attacks target enemy
     */
    public void attack() {
        if (targetEnemy != null && !targetEnemy.isDead()) {
            if (getType() == Type.INFERNO_TOWER && damage <= maxDamage)
                this.damage += 15; // increment the damage of inferno tower
            targetEnemy.getAttacked(this.damage);
            setAttacking(true);
        }
        targetEnemy = findEnemy();
        setAttacking(false);
    }

    /**
     * Finds an enemy in its range
     */
    private Entity findEnemy() {
        int x1 = (int) getLocation().getX(); // troop's x coordinate
        int y1 = (int) getLocation().getY(); // troop's y coordinate
        int x2, y2; // coordinates to check
        Entity entity;
        for (int i = 1; i <= range; i++) {
            x2 = x1 + i; // starts from the front
            y2 = y1;
            if (x2 < gameData.rowCount) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            x2 = x1 - i; // checks behind
            if (x2 >= 0) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            x2 = x1;
            y2 = y1 + i; // checks right
            if (y2 < gameData.colCount) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }

            y2 = y1 - i; // checks left
            if (y2 >= 0) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }
        }
        return null;
    }

    /**
     * Gets attacked
     * @param damage
     */
    @Override
    public void getAttacked(int damage) {
        this.hp -= damage;

        if (this.hp <= 0) { // has been destroyed by enemy
            // stop attacking
            setDead(true);
            stop(); // stop the timeline
        }
    }

    /**
     * Stops this building
     */
    public void stop() {
        if (attackingTimeline != null && attackingTimeline.getStatus().equals(Animation.Status.RUNNING))
            attackingTimeline.stop();
    }
}
