package ClashRoyale.model.gamelogic;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.EntityFactory;
import ClashRoyale.model.elements.Territory;
import ClashRoyale.model.elements.entities.Entity;
import ClashRoyale.model.elements.entities.River;
import ClashRoyale.model.elements.entities.Tower;
import javafx.geometry.Point2D;

public class GameSetup {

    private final GameData gameData;

    public GameSetup() {
        gameData = GameData.getInstance();
    }

    public void initMap(int level1, int level2) {
        gameData.bridgeUp = new Point2D(4, 15);
        gameData.bridgeDown = new Point2D(13, 15);
        for (int row = 0; row < gameData.rowCount; row++) {
            for (int col = 0; col < gameData.colCount; col++) {
                if ( (col == 15 && row != 4 && row != 13) || (col == 16 && row != 4 && row != 13) ) {
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

    public void setUpTerritories() {

        gameData.redQueenUpTerritory = new Territory(0, 8, 15, 23);
        gameData.redQueenDownTerritory = new Territory(8, 17, 15, 23);
        gameData.redKingTerritory = new Territory(0, 17, 23, 31);

        gameData.blueQueenUpTerritory = new Territory(0, 8, 8, 16);
        gameData.blueQueenDownTerritory = new Territory(8, 17, 8, 16);
        gameData.blueKingTerritory = new Territory(0, 17, 0, 8);

    }

    public void setUpCards() {
        // get the players cards
        // set 4 of them to displayedCardsHashMap in GameData
        // set one of them to nextCard in GameData
        // set the last 3 to upcomingCards in GameData
    }

}
