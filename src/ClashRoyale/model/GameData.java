package ClashRoyale.model;


import ClashRoyale.utils.AudioPlayer;
import ClashRoyale.model.elements.Bot;
import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.Territory;
import ClashRoyale.model.elements.entities.Entity;
import ClashRoyale.model.elements.entities.Tower;
import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Stack;

/**
 * All the game's data will be save here and can be accessed by any other class
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
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

    // crowns :
    public int blueCrownNum = 0, redCrownNum = 0;

    // info about the player's cards
    public HashMap<Integer, Entity.Type> displayedCards; // cardNumber -> cardType
    public Entity.Type nextCard;
    public Stack<Entity.Type> upcomingCards;  // use pop method to remove last element
    public int elixirs = 4;

    // when the game is over, the user will get back to the game's menu and the instance of this class should be null again (so that we can create new GameData for further games)
    public boolean gameOver = false;
    public String winnerName;

    // territory of each tower :
    public Territory blueQueenUpTerritory, blueQueenDownTerritory, blueKingTerritory;
    public Territory redQueenUpTerritory, redQueenDownTerritory, redKingTerritory;

    // game sound track audio player
    public AudioPlayer audioPlayer;

    /**
     * Constructs a GameData
     */
    private GameData() {
        map = new Entity[rowCount][colCount];
        displayedCards = new HashMap<>(4);
        upcomingCards = new Stack<>();
    }

    /**
     * @return instance of this class (singleton)
     */
    public static GameData getInstance() {
        if (instance == null)
            instance = new GameData();
        return instance;
    }

    /**
     * @param bot game's bot
     */
    public void setBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * Cheks if this coordinate is in the territory of enemy's towers or not
     * @param x x coordinate
     * @param y y coordinate
     * @param isEnemy is enemy or not
     * @return true if this x,y coordinate is inside a territory
     */
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
        return false;
    }

    /**
     * Checks if this x,y coordinate is inside the game's map
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the x,y coordinate is in the map
     */
    public boolean isInsideMap(int x, int y) {
        return x >= 0 && x < rowCount && y >= 0 && y < colCount;
    }

}
