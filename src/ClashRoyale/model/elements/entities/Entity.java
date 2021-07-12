package ClashRoyale.model.elements.entities;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.TargetType;
import javafx.geometry.Point2D;

public abstract class Entity {

    public enum Type {
        KING_TOWER, QUEEN_TOWER,
        BARBARIANS, ARCHER, BABY_DRAGON, WIZARD, MINI_PEKKA, GIANT, VALKYRIE,
        RAGE, FIRE, ARROWS,
        CANNON, INFERNO_TOWER
    }

    private final Type type;
    private final boolean isEnemy;
    private boolean dead = false;
    private Point2D location;
    protected GameData gameData;

    public Entity(Type type, boolean isEnemy) {
        this.type = type;
        this.isEnemy = isEnemy;
        gameData = GameData.getInstance();
    }

    public Entity(Type type, boolean isEnemy, Point2D location) {
        this.type = type;
        this.isEnemy = isEnemy;
        this.location = location;
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

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public abstract void activate();

    public void boost(double rate) {}

    public void undoBoost(double rate) {}

    public void getAttacked(int damage) {}

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
            if (targetType == TargetType.GROUND || targetType == TargetType.AIR_GROUND || targetType == TargetType.BUILDINGS)
                return true;
        }
        return (target instanceof Troop) && (targetType == TargetType.GROUND || targetType == TargetType.AIR_GROUND);
    }
}
