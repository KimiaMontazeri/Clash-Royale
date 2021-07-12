package ClashRoyale.model.elements.entities;

import javafx.geometry.Point2D;

public class River extends Entity {

    public River(Type type, Point2D location) {
        super(type, false, location);
    }

    @Override
    public void activate() {}
}
