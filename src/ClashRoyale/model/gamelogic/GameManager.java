package ClashRoyale.model.gamelogic;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.EntityFactory;
import ClashRoyale.model.elements.entities.Entity;
import ClashRoyale.model.elements.entities.Tower;

import java.util.ArrayList;

public class GameManager {

    private final GameData gameData;

    public GameManager() {
        gameData = GameData.getInstance();
    }

    /**
     * This method is called by GameCon at the start of each game
     * It will ask the GameSetup to do the initial stuff before the game (creating a the map, managing the players' cards,...)
     */
    public void start() {
        gameData.blueQueenTowerUp.activate();
        gameData.blueQueenTowerDown.activate();
        gameData.redQueenTowerUp.activate();
        gameData.redQueenTowerDown.activate();
    }

    public void addEntity(Entity.Type type, int x, int y, boolean isEnemy, int level) {
        ArrayList<Entity> entities;
        try {
            entities = EntityFactory.createEntity(type, x, y, gameData, isEnemy, level);
            for (Entity e : entities)
                e.activate();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateGame() {
        if (gameData.blueQueenTowerDown.isDead()) {
            gameData.blueQueenDownTerritory.setActive(false);
            // show a destroyed tower
            // give a queen crown to red team
            if (!gameData.blueKingTower.isActivated())
                gameData.blueKingTower.activate();
        }
        if (gameData.blueQueenTowerUp.isDead()) {
            gameData.blueQueenUpTerritory.setActive(false);
            // show a destroyed tower
            // give a queen crown to red team
            if (!gameData.blueKingTower.isActivated())
                gameData.blueKingTower.activate();
        }
        if (gameData.blueKingTower.isDead()) {
            // show a destroyed tower
            // give a king crown to red team
            // end the game
            gameData.gameOver = true; // is checked by the controller each time it renders the game to the screen
            return;
        }
        if (gameData.redQueenTowerDown.isDead()) {
            gameData.redQueenDownTerritory.setActive(false);
            // show a destroyed tower
            // give a queen crown to blue team
            if (!gameData.redKingTower.isActivated())
                gameData.redKingTower.activate();
        }
        if (gameData.redQueenTowerUp.isDead()) {
            gameData.redQueenUpTerritory.setActive(false);
            // show a destroyed tower
            // give a queen crown to blue team
            if (!gameData.redKingTower.isActivated())
                gameData.redKingTower.activate();
        }
        if (gameData.redKingTower.isDead()) {
            // show a destroyed tower
            // give a king crown to blue team
            // end the game
            gameData.gameOver = true; // is checked by the controller each time it renders the game to the screen
            return;
        }

        if (gameData.redKingTower.isAttacked() && !gameData.redKingTower.isActivated())
            gameData.redKingTower.activate();
        if (gameData.blueKingTower.isAttacked() && !gameData.blueKingTower.isActivated())
            gameData.blueKingTower.activate();

        removeDeadEntities();
    }

    public void removeDeadEntities() {
        for (int i = 0; i < gameData.rowCount; i++) {
            for (int j = 0; j < gameData.colCount; j++) {
                if (gameData.map[i][j] != null) {
                    if (!(gameData.map[i][j] instanceof Tower) && gameData.map[i][j].isDead())
                        gameData.map[i][j] = null;
                }
            }
        }
    }

    /**
     * This method is called by the GameCon when it finds out that the game has ended
     */
    public void findWinner() {
        // find the player with a king crown
        // compare each player's number of queen crowns
        // compare the hp of each player's towers
    }
}
