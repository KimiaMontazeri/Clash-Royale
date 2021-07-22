package ClashRoyale.model.elements.entities;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.TargetType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Any game map component is an instance of this class
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public abstract class Entity {

    /**
     * entity type
     */
    public enum Type implements Serializable {
        KING_TOWER(0), QUEEN_TOWER(0),
        BARBARIANS(5), ARCHER(3), BABY_DRAGON(4), WIZARD(5), MINI_PEKKA(4), GIANT(5), VALKYRIE(4),
        RAGE(3), FIRE(4), ARROWS(3),
        CANNON(3), INFERNO_TOWER(5), RIVER(0);

        int cost;

        Type(int cost) {
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }
    }

    private final Type type;
    private final boolean isEnemy; // determines whether it's on the blue team or the red team (enemy)
    private boolean dead = false, isAttacking = false;
    private Point2D location;
    protected GameData gameData;
    protected HashMap<String, Image> images;

    /**
     * Constructs an entity
     * @param type entity type
     * @param isEnemy determines whether it's on the red team or blue
     */
    public Entity(Type type, boolean isEnemy) {
        this.type = type;
        this.isEnemy = isEnemy;
        gameData = GameData.getInstance();
        images = new HashMap<>(4);
    }

    /**
     * Constructs an entity
     * @param type entity type
     * @param isEnemy determines whether it's on the red team or blue
     * @param location location of this entity
     */
    public Entity(Type type, boolean isEnemy, Point2D location) {
        this.type = type;
        this.isEnemy = isEnemy;
        this.location = location;
        gameData = GameData.getInstance();
    }

    /**
     *
     * @return entity type
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * @return isEnemy
     */
    public boolean isEnemy() {
        return isEnemy;
    }

    /**
     *
     * @return location
     */
    public Point2D getLocation() {
        return location;
    }

    /**
     *
     * @return whether this entity is dead or not
     */
    public boolean isDead() {
        return dead;
    }

    /**
     *
     * @return whether this entity has started attacking or not
     */
    public boolean isAttacking() {
        return isAttacking;
    }

    /**
     *
     * @return current image of this entity
     */
    public Image getCurrentImage() {
        return null;
    }

    /**
     * @param dead if the entity is dead or not
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * sets the location of this entity
     * @param location location
     */
    public void setLocation(Point2D location) {
        this.location = location;
    }

    /**
     * @param attacking whether this entity is attacking or not
     */
    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    /**
     * Activates this entity
     */
    public abstract void activate(); // activates this entity

    /**
     * Stops this entity
     */
    public abstract void stop(); // stops this entity

    // the 3 methods below are fully implemented in most of the subclasses

    /**
     * boosts this entity
     * @param rate rate of boosting
     */
    public void boost(double rate) {}

    /**
     * undo the boosts on this entity
     * @param rate rate of boosting
     */
    public void undoBoost(double rate) {}

    /**
     * Gets this entity attacked
     * @param damage damage done on this entity
     */
    public void getAttacked(int damage) {}

    // checks if this entity is an enemy or not

    /**
     * Checks whether this entity can attack the given enemy
     * @param enemy enemy
     * @return true if this entity can attack enemy
     */
    public boolean canAttack(Entity enemy) {
        if (enemy == null)
            return false;
        return (enemy.isEnemy() && !isEnemy()) ||
                (!enemy.isEnemy() && isEnemy());
    }

    /**
     * Checks if the target type matched the target of this entity
     * @param target target
     * @param targetType target type
     * @return true if target type matches the target
     */
    public boolean isTargetType(Entity target, TargetType targetType) {
        if (target == null)
            return false;
        if ((target instanceof Building) && (targetType == TargetType.GROUND || targetType == TargetType.AIR_GROUND || targetType == TargetType.BUILDINGS) )
            return true;
        if (target instanceof Tower) {
            Tower tower = (Tower) target;
            if (!tower.isDead() && (targetType == TargetType.GROUND || targetType == TargetType.AIR_GROUND || targetType == TargetType.BUILDINGS))
                return true;
        }
        if (target.getType() == Type.BABY_DRAGON && targetType == TargetType.AIR_GROUND)
            return true;
        return (target instanceof Troop) && (targetType == TargetType.GROUND || targetType == TargetType.AIR_GROUND);
    }
}
