package ClashRoyale.model.elements.entities;

import ClashRoyale.model.elements.TargetType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Building extends Card {

    private int hp;
    private int damage;
    private int hitSpeed;
    private final TargetType targetType;
    private final int range;
    private int lifetime;

    private Timeline attackingTimeline;
    private Entity targetEnemy;

    public Building(Type type, boolean isEnemy, int cost, int hp, int damage,
                    int hitSpeed, TargetType targetType, int range, int lifetime) {
        super(type, isEnemy, cost);
        this.hp = hp;
        this.damage = damage;
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

    public int getHitSpeed() {
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


    @Override
    public void activate() {
        startAttackingTimeline();
    }

    /**
     * Creates a timeline to call the attack() method every 'hitSpeed' seconds (relative to the building's hit speed)
     * Will be canceled if it's enemy target dies
     */
    public void startAttackingTimeline() {
        attackingTimeline = new Timeline(
                new KeyFrame(Duration.seconds(hitSpeed), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        attack();
                        decrementLifetime();
                    }
                })
        );
        attackingTimeline.setCycleCount(lifetime / hitSpeed);
        attackingTimeline.play();
    }

    public void decrementLifetime() {
        lifetime--;
        if (lifetime == 0)
            setDead(true);
    }

    public void attack() {
        if (getType().equals(Type.INFERNO_TOWER) && targetEnemy != null && lifetime <= 25)
            this.damage += 5;

        if (targetEnemy != null) {
            targetEnemy.getAttacked(this.damage);
            return;
        }
        targetEnemy = findEnemy();
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

    @Override
    public void getAttacked(int damage) {
        this.hp -= damage;

        if (this.hp == 0) { // has been destroyed by enemy
            // stop attacking
            if (attackingTimeline != null && attackingTimeline.getStatus().equals(Animation.Status.RUNNING))
                attackingTimeline.stop();

            setDead(true);
        }
    }
}
