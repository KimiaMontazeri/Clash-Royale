package ClashRoyale.model.elements.entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Tower extends Entity {

    private int hp, damage;
    private double hitSpeed;
    private final int range;
    private boolean isActivated, isAttacked;
    // isAttacked is mostly used for KingTower (Because KingTower will be activated if it gets attacked, or if one of the queen towers get damaged)

    private Timeline attackingTimeline;
    private Entity targetEnemy;

    public Tower(Type type, boolean isEnemy, Point2D loc, int hp, int damage, double hitSpeed, int range) {
        super(type, isEnemy, loc);
        this.hp = hp;
        this.damage = damage;
        this.hitSpeed = hitSpeed;
        this.range = range;
        isActivated = isAttacked = false;
        loadImages();
    }

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

    @Override
    public Image getCurrentImage() {
        if (isDead())
            return images.get("DEAD");
        return images.get("DEFAULT");
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public double getHitSpeed() {
        return hitSpeed;
    }

    public int getRange() {
        return range;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isAttacked() {
        return isAttacked;
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
                new KeyFrame(Duration.seconds(hitSpeed), event -> attack()) // calls the attack method each "hitSpeed" time
        );
        attackingTimeline.setCycleCount(Timeline.INDEFINITE); // the timeline won't be stopped until the game finishes or the tower gets destroyed
        attackingTimeline.play();
    }

    public void attack() {
        if (targetEnemy != null && !targetEnemy.isDead()) {
            targetEnemy.getAttacked(this.damage);
            setAttacking(true);
            return; // won't search for other enemies until the current target dies
        }
        targetEnemy = findEnemy();
        setAttacking(false);
    }

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
