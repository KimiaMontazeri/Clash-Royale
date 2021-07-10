package ClashRoyale.model;


import ClashRoyale.model.elements.CellValue;

public class GameData {

    private GameData instance = null;
    public CellValue[][] map;

    private GameData() {

    }

    public GameData getInstance() {
        if (instance == null)
            instance = new GameData();
        return instance;
    }

    public CellValue[][] getMap() {
        return map;
    }

}
