package ClashRoyale.model.gamelogic;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.EntityFactory;
import ClashRoyale.model.elements.Territory;
import ClashRoyale.model.elements.entities.Entity;
import ClashRoyale.model.elements.entities.River;
import ClashRoyale.model.elements.entities.Tower;
import javafx.geometry.Point2D;

import java.util.ArrayList;

/**
 * This class is responsible for setting up the game at the beginning
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public class GameSetup {

    private final GameData gameData;

    /**
     * Constructs a GameSetup
     */
    public GameSetup() {
        gameData = GameData.getInstance();
    }

    /**
     * Creates a map and initializes default values to it
     * @param level1 level of the first player
     * @param level2 level of the second player
     */
    public void initMap(int level1, int level2) {
        gameData.bridgeUp = new Point2D(4, 15);
        gameData.bridgeDown = new Point2D(13, 15);
        for (int row = 0; row < gameData.rowCount; row++) {
            for (int col = 0; col < gameData.colCount; col++) {
                if ( (col == 15 && row != 4 && row != 13 && row != 0 && row != 17) || (col == 16 && row != 4 && row != 13 && row != 0 && row != 17) ) {
                    gameData.map[row][col] = new River(Entity.Type.RIVER, new Point2D(row, col));
                }
                // placing the towers
                else if (row == 4 && col == 3) {
                    gameData.blueQueenTowerUp = (Tower)
                            EntityFactory.createQueenTower(new Point2D(row, col), false, level1, gameData);
                    gameData.map[row][col] = gameData.blueQueenTowerUp;
                } else if (row == 13 && col == 3) {
                    gameData.blueQueenTowerDown = (Tower)
                            EntityFactory.createQueenTower(new Point2D(row, col), false, level1, gameData);
                    gameData.map[row][col] = gameData.blueQueenTowerDown;
                } else if (row == 8 && col == 3) {
                    gameData.blueKingTower = (Tower)
                            EntityFactory.createKingTower(new Point2D(row, col), false, level1, gameData);
                    gameData.map[row][col] = gameData.blueKingTower;
                } else if (row == 4 && col == 28) {
                    gameData.redQueenTowerUp = (Tower)
                            EntityFactory.createQueenTower(new Point2D(row, col), true, level2, gameData);
                    gameData.map[row][col] = gameData.redQueenTowerUp;
                } else if (row == 13 && col == 28) {
                    gameData.redQueenTowerDown = (Tower)
                            EntityFactory.createQueenTower(new Point2D(row, col), true, level2, gameData);
                    gameData.map[row][col] = gameData.redQueenTowerDown;
                } else if (row == 8 && col == 28) {
                    gameData.redKingTower = (Tower)
                            EntityFactory.createKingTower(new Point2D(row, col), true, level2, gameData);
                    gameData.map[row][col] = gameData.redKingTower;
                }
            }
        }
    }

    /**
     * Sets up the territory of each tower
     */
    public void setUpTerritories() {

        gameData.redQueenUpTerritory = new Territory(0, 8, 15, 23);
        gameData.redQueenDownTerritory = new Territory(8, 17, 15, 23);
        gameData.redKingTerritory = new Territory(0, 17, 23, 31);

        gameData.blueQueenUpTerritory = new Territory(0, 8, 8, 16);
        gameData.blueQueenDownTerritory = new Territory(8, 17, 8, 16);
        gameData.blueKingTerritory = new Territory(0, 17, 0, 8);

    }

    /**
     * Sets up the player's cards
     */
    public void setUpCards() {
        ArrayList<Entity.Type> playerCards = gameData.player.getCards();

        int i;
        for (i = 0; i < 4; i++)
            gameData.displayedCards.put(i + 1, playerCards.get(i));

        gameData.nextCard = playerCards.get(4);

        for (i = 5; i < playerCards.size(); i++)
            gameData.upcomingCards.push(playerCards.get(i));
    }

}
