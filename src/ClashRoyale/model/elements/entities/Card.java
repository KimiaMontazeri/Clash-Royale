package ClashRoyale.model.elements.entities;

import javafx.geometry.Point2D;

public abstract class Card {

    private final String name;
    private final int cost;
    private boolean isEnemy;
    private Point2D location;

    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setEnemy(boolean enemy) {
        isEnemy = enemy;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }
}
