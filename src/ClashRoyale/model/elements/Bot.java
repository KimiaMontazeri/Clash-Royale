package ClashRoyale.model.elements;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.entities.Entity;
import javafx.animation.Timeline;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game's bot
 * @author KIMIA
 * @since 7-22-2021
 * @version 1.0
 */
public abstract class Bot {

    private final int level;
    private final Random randomGenerator;
    protected Timeline timeline;
    protected GameData gameData;

    protected int x = 0, y = 0;

    /**
     * Constructs a bot
     * @param level level
     */
    public Bot(int level) {
        this.level = level;
        this.randomGenerator = new Random();
        gameData = GameData.getInstance();
    }

    /**
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @return random generator
     */
    protected Random getRandomGenerator() {
        return randomGenerator;
    }

    /**
     * @param type card type
     * @return if the card is troop
     */
    protected boolean isTroop(Entity.Type type) {
        return type == Entity.Type.BARBARIANS || type == Entity.Type.ARCHER ||
                type == Entity.Type.BABY_DRAGON || type == Entity.Type.MINI_PEKKA ||
                type == Entity.Type.GIANT || type == Entity.Type.VALKYRIE;
    }

    /**
     * @param type card type
     * @return if the card is spell
     */
    protected boolean isSpell(Entity.Type type) {
        return type == Entity.Type.FIRE || type == Entity.Type.RAGE || type == Entity.Type.ARROWS;
    }

    /**
     * @param type card type
     * @return if the card is building
     */
    protected boolean isBuilding(Entity.Type type) {
        return type == Entity.Type.INFERNO_TOWER || type == Entity.Type.CANNON;
    }

    /**
     * Chooses a troop randomly
     * @return card type
     */
    protected Entity.Type chooseTroop() {
        int r = randomGenerator.nextInt(7);
        if (r == 1)
            return Entity.Type.ARCHER;
        if (r == 2)
            return Entity.Type.BABY_DRAGON;
        if (r == 3)
            return Entity.Type.WIZARD;
        if (r == 4)
            return Entity.Type.MINI_PEKKA;
        if (r == 5)
            return Entity.Type.GIANT;
        return Entity.Type.VALKYRIE;
    }

    /**
     * Chooses a building randomly
     * @return card type
     */
    protected Entity.Type chooseBuilding() {
        int r = randomGenerator.nextInt(2);
        if (r == 0)
            return Entity.Type.INFERNO_TOWER;
        return Entity.Type.CANNON;
    }

    /**
     * Chooses a spell randomly
     * @return card type
     */
    protected Entity.Type chooseSpell() {
        int r = randomGenerator.nextInt(3);
        if (r == 0)
            return Entity.Type.FIRE;
        if (r == 1)
            return Entity.Type.ARROWS;
        return Entity.Type.RAGE;
    }

    /**
     * Places entity on the map
     * @param type card type
     */
    protected void placeEntity(Entity.Type type) {
        if (type == null)
            return;

        ArrayList<Entity> entities;
        try {
            entities = EntityFactory.createEntity(type, x, y, gameData, true, level);
            for (Entity e : entities) {
                e.activate();
                System.out.println("Bot placed a " + e.getType() + " on the map");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts this bot
     */
    public abstract void start();

    /**
     * this is called when the game is over
     */
    public void stopBot() {
        this.timeline.stop();
    }

    /**
     * @return the name of this bot
     */
    public String getName() {
        return "BOT";
    }

}
