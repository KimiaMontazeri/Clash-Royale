package ClashRoyale.model.gamelogic;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.EntityFactory;
import ClashRoyale.model.elements.History;
import ClashRoyale.model.elements.Player;
import ClashRoyale.model.elements.entities.Entity;
import ClashRoyale.model.elements.entities.River;
import ClashRoyale.model.elements.entities.Tower;
import javafx.geometry.Point2D;

import java.util.ArrayList;

public class GameManager {

    private final GameData gameData;
    private final GameSetup gameSetup;

    public GameManager() {
        gameData = GameData.getInstance();
        gameSetup = new GameSetup();
    }

    public GameData getGameData() {
        return gameData;
    }

    /**
     * This method is called by GameCon at the start of each game
     * It will ask the GameSetup to do the initial stuff before the game (creating a the map, managing the players' cards,...)
     */
    public void start() {
//        gameData.player = new Player("kimia", "123");
        gameSetup.initMap(1, 1);
        gameSetup.setUpTerritories();
        gameSetup.setUpCards();
        gameData.blueQueenTowerUp.activate();
        gameData.blueQueenTowerDown.activate();
        gameData.redQueenTowerUp.activate();
        gameData.redQueenTowerDown.activate();
        gameData.bot.start();
    }

    /**
     * adds an entity to the map
     * @param type type of the entity
     * @param x x coordinate
     * @param y y coordinate
     * @param isEnemy determines whether the entity is on the red team or blue
     * @param level level of the player/bot which has chosen to add this entity
     * @return the result of entity addition (returns false if the given x,y coordinate is not valid)
     */
    public boolean addEntity(Entity.Type type, int x, int y, boolean isEnemy, int level) {
        ArrayList<Entity> entities;
        try {
            entities = EntityFactory.createEntity(type, x, y, gameData, isEnemy, level);
            for (Entity e : entities) {
                e.activate();
            }
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void updateGame() {
        int blueDeadTowers = 0, redDeadTowers = 0;
        if (gameData.blueQueenTowerDown.isDead()) {
            gameData.blueQueenDownTerritory.setActive(false);
            blueDeadTowers++;
            if (!gameData.blueKingTower.isActivated())
                gameData.blueKingTower.activate();
        }
        if (gameData.blueQueenTowerUp.isDead()) {
            gameData.blueQueenUpTerritory.setActive(false);
            blueDeadTowers++;
            if (!gameData.blueKingTower.isActivated())
                gameData.blueKingTower.activate();
        }
        if (gameData.blueKingTower.isDead()) {
            gameData.redCrownNum = 3;
            gameData.gameOver = true;
            return;
        }
        if (gameData.redQueenTowerDown.isDead()) {
            gameData.redQueenDownTerritory.setActive(false);
            redDeadTowers++;
            if (!gameData.redKingTower.isActivated())
                gameData.redKingTower.activate();
        }
        if (gameData.redQueenTowerUp.isDead()) {
            gameData.redQueenUpTerritory.setActive(false);
            redDeadTowers++;
            if (!gameData.redKingTower.isActivated())
                gameData.redKingTower.activate();
        }
        if (gameData.redKingTower.isDead()) {
            gameData.blueCrownNum = 3;
            gameData.gameOver = true;
            return;
        }

        gameData.blueCrownNum = redDeadTowers;
        gameData.redCrownNum = blueDeadTowers;
        if (gameData.redKingTower.isAttacked() && !gameData.redKingTower.isActivated())
            gameData.redKingTower.activate();
        if (gameData.blueKingTower.isAttacked() && !gameData.blueKingTower.isActivated())
            gameData.blueKingTower.activate();

        updateMap();
        removeDeadEntities();
    }

    public void updateMap() {
        gameData.bridgeUp = new Point2D(4, 15);
        gameData.bridgeDown = new Point2D(13, 15);
        for (int row = 0; row < gameData.rowCount; row++) {
            for (int col = 0; col < gameData.colCount; col++) {
                // updating the river
                if ( (col == 15 && row != 4 && row != 13 && row != 0 && row != 17) || (col == 16 && row != 4 && row != 13 && row != 0 && row != 17) ) {
                    if (gameData.map[row][col] instanceof River || gameData.map[row][col] == null)
                        gameData.map[row][col] = new River(Entity.Type.RIVER, new Point2D(row, col));
                }

                // updating the towers
                else if (row == 4 && col == 3)
                    gameData.map[row][col] = gameData.blueQueenTowerUp;
                else if (row == 13 && col == 3)
                    gameData.map[row][col] = gameData.blueQueenTowerDown;
                else if (row == 8 && col == 3)
                    gameData.map[row][col] = gameData.blueKingTower;
                else if (row == 4 && col == 28)
                    gameData.map[row][col] = gameData.redQueenTowerUp;
                else if (row == 13 && col == 28)
                    gameData.map[row][col] = gameData.redQueenTowerDown;
                else if (row == 8 && col == 28)
                    gameData.map[row][col] = gameData.redKingTower;

            }
        }
    }

    public void removeDeadEntities() {
        for (int i = 0; i < gameData.rowCount; i++) {
            for (int j = 0; j < gameData.colCount; j++) {
                if (gameData.map[i][j] != null) {
                    if (!(gameData.map[i][j] instanceof Tower) && gameData.map[i][j].isDead()) {
                        gameData.map[i][j].stop();
                        gameData.map[i][j] = null;
                    }
                }
            }
        }
    }

    /**
     * This method is called by the GameCon when the player has drag and dropped a card in the map
     * @param cardNum helps us find the chosen card in the displayed cards hashmap
     * @param x x coordinate
     * @param y y coordinate
     */
    public void useCard(int cardNum, int x, int y) {
        Entity.Type chosenCard = gameData.displayedCards.get(cardNum);
//        if (chosenCard.getCost() < gameData.elixirs) // this if statement will not be true, but has been placed here for more safety
//            return;

        boolean res = addEntity(chosenCard, x, y, false, gameData.player.getLevel()); 

        if (res) {
            gameData.displayedCards.put(cardNum, gameData.nextCard); // replace the chosen card with the next card
            gameData.nextCard = gameData.upcomingCards.pop();        // remove the last card in upcoming cards and set it to the next card
            gameData.upcomingCards.add(chosenCard);                  // add the chosen card to the end of the upcoming cards stack
            gameData.elixirs -= chosenCard.getCost();
        }
        else System.out.println("couldn't place this card :(");

    }

    /**
     * This method is called by the GameCon when it finds out that the game has ended
     */
    public void findWinner() {
        // find the player with a king crown
        if (gameData.blueCrownNum > gameData.redCrownNum)
            gameData.winnerName = gameData.player.getUsername();
        else if (gameData.blueCrownNum < gameData.redCrownNum)
            gameData.winnerName = gameData.bot.toString();
        else {
            int hpSumBlueTeam = gameData.blueKingTower.getHp()
                    + gameData.blueQueenTowerUp.getHp()
                    + gameData.blueQueenTowerDown.getHp();
            int hpSumRedTeam = gameData.redKingTower.getHp()
                    + gameData.redQueenTowerUp.getHp()
                    + gameData.redQueenTowerDown.getHp();
            if (hpSumBlueTeam > hpSumRedTeam)
                gameData.winnerName = gameData.player.getUsername();
            else
                gameData.winnerName = gameData.bot.toString();
        }
    }

    public void endGame() {
        History history = new History(gameData.player.getUsername(), gameData.winnerName);
        gameData.player.addHistory(history);
    }
}
