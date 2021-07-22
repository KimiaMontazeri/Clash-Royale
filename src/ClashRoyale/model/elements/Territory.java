package ClashRoyale.model.elements;

/**
 * Represents the territory of each tower in the game which is used for placing troops or buildings on the map
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class Territory {

    private final int MAX_X, MIN_X, MAX_Y, MIN_Y;
    private boolean active; // will get de-activated by the GameManager when it's corresponding tower dies

    /**
     * Creates a territory
     * @param minX minimum x
     * @param maxX maximum x
     * @param minY minimum y
     * @param maxY maximum y
     */
    public Territory(int minX, int maxX, int minY, int maxY) {
        this.MIN_X = minX;
        this.MAX_X = maxX;
        this.MIN_Y = minY;
        this.MAX_Y = maxY;
        this.active = true;
    }

    /**
     * @return max x
     */
    public int getMAX_X() {
        return MAX_X;
    }

    /**
     * @return min x
     */
    public int getMIN_X() {
        return MIN_X;
    }

    /**
     * @return max y
     */
    public int getMAX_Y() {
        return MAX_Y;
    }

    /**
     * @return min y
     */
    public int getMIN_Y() {
        return MIN_Y;
    }

    /**
     * @return if it's active or not
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active if it's active or not
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * checks if this x,y coordinate is inside this territory
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the x,y coordinate are inside this territory
     */
    public boolean isInTerritory(int x, int y) {
        return x >= getMIN_X() && x <= getMAX_X() && y >= getMIN_Y() && y <= getMAX_Y();
    }
}
