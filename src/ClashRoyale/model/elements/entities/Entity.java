package ClashRoyale.model.elements.entities;

import ClashRoyale.model.GameData;
import javafx.geometry.Point2D;

public abstract class Entity {

    private final String name;
    private final boolean isEnemy;
    private boolean isDying;
    private Point2D location;
    protected GameData gameData;

    public Entity(String name, boolean isEnemy) {
        this.name = name;
        this.isEnemy = isEnemy;
        gameData = GameData.getInstance();
    }

    public Entity(String name, boolean isEnemy, Point2D location) {
        this.name = name;
        this.isEnemy = isEnemy;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public boolean isDying() {
        return isDying;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setDying(boolean dying) {
        isDying = dying;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }
}
