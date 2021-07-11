package ClashRoyale.model;


import ClashRoyale.model.elements.entities.Entity;
import javafx.geometry.Point2D;

public class GameData {

    private static GameData instance = null;
    public Entity[][] map;
    public Point2D bridgeUp, bridgeDown;
    public final int rowCount = 18;
    public final int colCount = 32;

    private GameData() {
        map = new Entity[rowCount][colCount];
    }

    public static GameData getInstance() {
        if (instance == null)
            instance = new GameData();
        return instance;
    }



}
