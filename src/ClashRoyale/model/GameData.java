package ClashRoyale.model;


import ClashRoyale.model.elements.entities.Entity;

public class GameData {

    private static GameData instance = null;
    public Entity[][] map;

    private GameData() { 

    }

    public static GameData getInstance() {
        if (instance == null)
            instance = new GameData();
        return instance;
    }



}
