package ClashRoyale.model.elements.entities;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class River extends Entity {

    public River(Type type, Point2D location) {
        super(type, false, location);
    }

    @Override
    public Image getCurrentImage() {
        return new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_water.png"));
    }

    @Override
    public void activate() {}

    @Override
    public void stop() {}
}
