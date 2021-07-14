package ClashRoyale.model;


import ClashRoyale.model.elements.Bot;
import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.Territory;
import ClashRoyale.model.elements.entities.Entity;
import ClashRoyale.model.elements.entities.Tower;
import javafx.geometry.Point2D;

public class GameData {

    private static GameData instance = null;
    public final int rowCount = 18;
    public final int colCount = 32;
    public Entity[][] map;
    public Point2D bridgeUp, bridgeDown;
    public Tower blueQueenTowerUp, blueQueenTowerDown, redQueenTowerUp, redQueenTowerDown;
    public Tower blueKingTower, redKingTower;
    public Player player;
    public Bot bot;
    public boolean gameOver = false;
    public Territory blueQueenUpTerritory, blueQueenDownTerritory, blueKingTerritory;
    public Territory redQueenUpTerritory, redQueenDownTerritory, redKingTerritory;

    private GameData() {
        map = new Entity[rowCount][colCount];
    }

    public static GameData getInstance() {
        if (instance == null)
            instance = new GameData();
        return instance;
    }



}
