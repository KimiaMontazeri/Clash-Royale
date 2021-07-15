package ClashRoyale.model.elements.entities;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.TargetType;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.LinkedList;

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
    private boolean dead = false;
    private Point2D location;
    protected GameData gameData;
    private LinkedList<Image> images; // some images of the entity in a circular linked list to create a simple animation
    private Image currentImage;

    public Entity(Type type, boolean isEnemy) {
        this.type = type;
        this.isEnemy = isEnemy;
        gameData = GameData.getInstance();
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

    public LinkedList<Image> getImages() {
        return images;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public void setUpImages() {
        images = new LinkedList<>();
        // TODO
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
