package ClashRoyale.model.elements.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * This class represents a river component on the map
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class River extends Entity {

    /**
     * Creates a river :)
     * @param type
     * @param location
     */
    public River(Type type, Point2D location) {
        super(type, false, location);
    }

    /**
     * @return current image
     */
    @Override
    public Image getCurrentImage() {
        return new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_water.png"));
    }

    @Override
    public void activate() {}

    @Override
    public void stop() {}
}
