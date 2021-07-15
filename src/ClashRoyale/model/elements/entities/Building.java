package ClashRoyale.model.elements.entities;

import ClashRoyale.model.elements.TargetType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Building extends Card { // can be an inferno tower or a cannon

    private int hp;
    private int damage;
    private int maxDamage; // this is used for inferno tower, since its damage increases when it finds a target
    private double hitSpeed;
    private final TargetType targetType;
    private final int range;
    private int lifetime;

    private Timeline attackingTimeline; // a timeline to model the building's lifetime
    private Entity targetEnemy;         // the building's current target

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
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public double getHitSpeed() {
        return hitSpeed;
    }

    public TargetType getTarget() {
        return targetType;
    }

    public double getRange() {
        return range;
    }

    public int getLifetime() {
        return lifetime;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    public void setHitSpeed(double hitSpeed) {
        this.hitSpeed = hitSpeed;
    }

    @Override
    public void boost(double rate) { // used for when Rage spell is activated on this building
        setDamage((int) (getDamage() * rate));
        setHitSpeed((int) (getHitSpeed() * rate));
    }

    @Override
    public void undoBoost(double rate) { // used for when Rage spell is de-activated on this building
        setDamage((int) (getDamage() / rate));
        setHitSpeed((int) (getHitSpeed() / rate));
    }


    @Override
    public void activate() {
        startAttackingTimeline();
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

    public void decrementLifetime() {
        lifetime--;
        if (lifetime == 0) {
            setDead(true);
            stop(); // stops the timeline
        }
    }

    public void attack() {
        if (targetEnemy != null && !targetEnemy.isDead()) {
            if (getType() == Type.INFERNO_TOWER && damage <= maxDamage)
                this.damage += 15; // increment the damage of inferno tower
            targetEnemy.getAttacked(this.damage);
//            return;
        }
        targetEnemy = findEnemy();
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
            if (y2 <= 0) { // avoiding IndexOutOfBound exception
                entity = gameData.map[x2][y2];
                if (super.isTargetType(entity, targetType) && super.canAttack(entity))
                    return entity;
            }
        }
        return null;
    }

    @Override
    public void getAttacked(int damage) {
        this.hp -= damage;

        if (this.hp <= 0) { // has been destroyed by enemy
            // stop attacking
            setDead(true);
            stop(); // stop the timeline
        }
    }

    public void stop() {
        if (attackingTimeline != null && attackingTimeline.getStatus().equals(Animation.Status.RUNNING))
            attackingTimeline.stop();
    }
}
