package ClashRoyale.model.elements;

import ClashRoyale.model.elements.entities.Entity;
import ClashRoyale.model.elements.entities.Tower;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class MediumBot extends Bot {

    public MediumBot() {
        super(2);
    }

    @Override
    public void start() {
        this.timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> {
                    placeEntity(chooseCard());
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public Entity.Type chooseCard() {
        Entity.Type chosenCard;
        int r = getRandomGenerator().nextInt(10);
        if (r <= 5) {
            chosenCard = chooseTroop();
            findTroopCoordinate();
        }
        else if (r <= 7) {
            chosenCard = chooseBuilding();
            findBuildingCoordinate();
        }
        else {
            chosenCard = chooseSpell();
            findSpellCoordinate(chosenCard);
        }
        return chosenCard;
    }

    public void findTroopCoordinate() {
        int attempts = 0;
        do {

            Point2D loc = findEnemyInField();
            if (loc != null) {
                // places a troop in front of an enemy
                x = (int) loc.getX();
                y = (int) loc.getY() + 3;
                if (y > 28)
                    y = (int) loc.getY() - 3;
            } else {
                // places a troop closer to the river
                x = getRandomGenerator().nextInt(11) + 3;
                y = getRandomGenerator().nextInt(10) + 17;
            }
            // don't try more than 5 times
            attempts++;
            if (attempts == 5)
                return;

        } while (gameData.isInsideMap(x, y) && gameData.map[x][y] == null);
    }

    public void findBuildingCoordinate() {
        int attempt = 0;
        do {

            x = getRandomGenerator().nextInt(11) + 3;
            y = getRandomGenerator().nextInt(10) + 17;
            attempt++;
            if (attempt == 5)
                break;

        } while (gameData.isInsideMap(x, y) && gameData.map[x][y] == null);
    }

    public void findSpellCoordinate(Entity.Type chosenSpell) {
        if (chosenSpell != null) {
            if (chosenSpell == Entity.Type.RAGE) {
                boost();
            }
            else if (chosenSpell == Entity.Type.FIRE) {
                int hp1 = gameData.blueQueenTowerUp.getHp();
                int hp2 = gameData.blueKingTower.getHp();
                int hp3 = gameData.blueQueenTowerDown.getHp();
                if (hp1 <= hp2 && hp1 <= hp3) {
                    x = (int) gameData.blueQueenTowerUp.getLocation().getX();
                    y = (int) gameData.blueQueenTowerUp.getLocation().getY();
                }
                else if (hp2 <= hp1 && hp2 <= hp3) {
                    x = (int) gameData.blueKingTower.getLocation().getX();
                    y = (int) gameData.blueKingTower.getLocation().getY();
                }
                else {
                    x = (int) gameData.blueQueenTowerDown.getLocation().getX();
                    y = (int) gameData.blueQueenTowerDown.getLocation().getY();
                }
            }
            else if (chosenSpell == Entity.Type.ARROWS) {
                for (int row = 0; row < gameData.rowCount; row++) {
                    for (int col = 0; col < gameData.colCount; col++) {
                        Entity entity = gameData.map[row][col];
                        if (entity != null && !entity.isEnemy()) {
                            x = (int) entity.getLocation().getX();
                            y = (int) entity.getLocation().getY();
                            return;
                        }
                    }
                }
            }
        }
    }

    public void boost() {

        if (gameData.redKingTower.getHp() <= 1000) {
            x = (int) gameData.redKingTower.getLocation().getX();
            y = (int) gameData.redKingTower.getLocation().getY();
            System.out.println("medium bot boosted its king tower");
        }
        else if (gameData.redQueenTowerUp.getHp() <= 500) {
            x = (int) gameData.redQueenTowerUp.getLocation().getX();
            y = (int) gameData.redQueenTowerUp.getLocation().getY();
            System.out.println("medium bot boosted its upper queen tower");
        }
        else if (gameData.redQueenTowerDown.getHp() <= 500) {
            x = (int) gameData.redQueenTowerDown.getLocation().getX();
            y = (int) gameData.redQueenTowerDown.getLocation().getY();
            System.out.println("medium bot boosted its lower queen tower");
        }
        else {
            for (int row = 0; row < gameData.rowCount; row++) {
                for (int col = 0; col < gameData.colCount; col++) {
                    Entity entity = gameData.map[row][col];
                    if (entity != null && entity.isEnemy() && !(entity instanceof Tower)) {
                        x = (int) entity.getLocation().getX();
                        y = (int) entity.getLocation().getY();
                        System.out.println("medium bot boosted " + entity.getType());
                        return;
                    }
                }
            }
        }
    }

    private Point2D findEnemyInField() {
        for (int row = 0; row < gameData.rowCount; row++) {
            for (int col = 17; col < gameData.colCount; col++) {
                if (gameData.map[row][col] != null && !gameData.map[row][col].isEnemy())
                    return gameData.map[row][col].getLocation();
            }
        }
        return null;
    }

    public String getName() {
        return "MEDIUM BOT";
    }
}
