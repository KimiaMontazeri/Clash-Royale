package ClashRoyale.model.elements;

/**
 * Represents the territory of each tower in the game which is used for placing troops or buildings on the map
 */
public class Territory {

    private final int MAX_X, MIN_X, MAX_Y, MIN_Y;
    private boolean active; // will get de-activated by the GameManager when it's corresponding tower dies

    public Territory(int minX, int maxX, int minY, int maxY) {
        this.MIN_X = minX;
        this.MAX_X = maxX;
        this.MIN_Y = minY;
        this.MAX_Y = maxY;
        this.active = true;
    }

    public int getMAX_X() {
        return MAX_X;
    }

    public int getMIN_X() {
        return MIN_X;
    }

    public int getMAX_Y() {
        return MAX_Y;
    }

    public int getMIN_Y() {
        return MIN_Y;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // checks if this x,y coordinate is inside this territory
    public boolean isInTerritory(int x, int y) {
        return x >= getMIN_X() && x <= getMAX_X() && y >= getMIN_Y() && y <= getMAX_Y();
    }
}
