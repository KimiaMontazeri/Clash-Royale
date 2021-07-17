package ClashRoyale.model.elements.entities;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.TargetType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.HashMap;

public abstract class Entity {

    public enum Type {
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

    public Entity(Type type, boolean isEnemy) {
        this.type = type;
        this.isEnemy = isEnemy;
        gameData = GameData.getInstance();
        images = new HashMap<>(4);
    }

    public Entity(Type type, boolean isEnemy, Point2D location) {
        this.type = type;
        this.isEnemy = isEnemy;
        this.location = location;
        gameData = GameData.getInstance();
    }

    public Type getType() {
        return type;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public Point2D getLocation() {
        return location;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public Image getCurrentImage() {
        return null;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public abstract void activate(); // activates this entity

    // the 3 methods below are fully implemented in most of the subclasses
    public void boost(double rate) {}

    public void undoBoost(double rate) {}

    public void getAttacked(int damage) {}

    // checks if this entity is an enemy or not
    public boolean canAttack(Entity enemy) {
        return (enemy.isEnemy() && !isEnemy()) ||
                (!enemy.isEnemy() && isEnemy());
    }

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
