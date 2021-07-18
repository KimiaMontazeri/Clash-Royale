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

    // location of the bridges
    public Point2D bridgeUp, bridgeDown;
    // location of the towers
    public Tower blueQueenTowerUp, blueQueenTowerDown, redQueenTowerUp, redQueenTowerDown;
    public Tower blueKingTower, redKingTower;

    // players :
    public Player player;
    public Bot bot;

    // when the game is over, the user will get back to the game's menu and the instance of this class should be null again (so that we can create new GameData for further games)
    public boolean gameOver = false;

    // territory of each tower :
    public Territory blueQueenUpTerritory, blueQueenDownTerritory, blueKingTerritory;
    public Territory redQueenUpTerritory, redQueenDownTerritory, redKingTerritory;

    private GameData() {
        map = new Entity[rowCount][colCount];
    }

    // using singleton pattern for this class so that we have only 1 GameData in the whole game
    public static GameData getInstance() {
        if (instance == null)
            instance = new GameData();
        return instance;
    }

    public boolean isInTerritory(int x, int y, boolean isEnemy) {
        if (isEnemy) {
            // != null is written to avoid NullPointerException
            if (blueKingTerritory != null && blueKingTerritory.isActive() && blueKingTerritory.isInTerritory(x,y))
                return true;
            if (blueQueenUpTerritory != null && blueQueenUpTerritory.isActive() && blueQueenUpTerritory.isInTerritory(x,y))
                return true;
            if (blueQueenDownTerritory != null && blueQueenDownTerritory.isActive() && blueQueenDownTerritory.isInTerritory(x,y))
                return true;
        } else {
            if (redKingTerritory != null && redKingTerritory.isActive() && redKingTerritory.isInTerritory(x,y))
                return true;
            if (redQueenUpTerritory != null && redQueenUpTerritory.isActive() && redQueenUpTerritory.isInTerritory(x,y))
                return true;
            if (redQueenDownTerritory != null && redQueenDownTerritory.isActive() && redQueenDownTerritory.isInTerritory(x,y))
                return true;
        }
        return true;
    }

    public boolean isInsideMap(int x, int y) {
        return x >= 0 && x < rowCount && y >= 0 && y < colCount;
    }

}
