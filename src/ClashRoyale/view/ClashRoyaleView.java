package ClashRoyale.view;

import ClashRoyale.model.GameData;
import ClashRoyale.model.elements.entities.Entity;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ClashRoyaleView extends Group {

    public final static double CELL_WIDTH = 20.0;
    public final static double CELL_HEIGHT = 25.0;
    private int rowCount;
    private int columnCount;
    private ImageView[][] tiles;

    // tiles
    private final Image tileCenter, tileDown, tileLeft, tileLeftCornerDown, tileLeftCornerUp, tileRight, tileRightCornerDown, tileRightCornerUp, tileUp;

    public ClashRoyaleView() {
        tileDown = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_down.png"));
        tileLeft = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_left.png"));
        tileLeftCornerDown = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_left_corner_down.png"));
        tileLeftCornerUp = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_left_corner_up.png"));
        tileRight = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_right.png"));
        tileRightCornerDown = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_right_corner_down.png"));
        tileRightCornerUp = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_right_corner_up.png"));
        tileUp = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_up.png"));
        tileCenter = new Image(getClass().getResourceAsStream("/ClashRoyale/resources/tiles/tile_center.png"));
    }

    public ImageView[][] getMap() {
        return tiles;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
        initMap();
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
        initMap();
    }

    public void initMap() {
        tiles = new ImageView[rowCount][columnCount];
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                ImageView imageView = new ImageView();
                imageView.setX(col * CELL_WIDTH);
                imageView.setY(row * CELL_HEIGHT);
                imageView.setFitWidth(CELL_WIDTH);
                imageView.setFitHeight(CELL_HEIGHT);
                tiles[row][col] = imageView;
                this.getChildren().add(tiles[row][col]);
            }
        }
    }

    public void update(GameData gameData) {
        Entity[][] map = gameData.map;
        for (int row = 0; row < gameData.rowCount; row++) {
            for (int col = 0; col < gameData.colCount; col++) {
                // default
                this.tiles[row][col].setImage(tileCenter);
                // rendering the border of the map
                if (col == 0)
                    this.tiles[row][col].setImage(tileLeft);
                if (col == 31)
                    this.tiles[row][col].setImage(tileRight);
                if (row == 0)
                    this.tiles[row][col].setImage(tileUp);
                if (row == 17)
                    this.tiles[row][col].setImage(tileDown);
                if (col == 0 && row == 0)
                    this.tiles[row][col].setImage(tileLeftCornerUp);
                if (col == 31 && row == 0)
                    this.tiles[row][col].setImage(tileRightCornerUp);
                if (col == 0 && row == 17)
                    this.tiles[row][col].setImage(tileLeftCornerDown);
                if (col == 31 && row == 17)
                    this.tiles[row][col].setImage(tileRightCornerDown);


                // rendering the image of the entities on the map
                if (map[row][col] != null) {
                    // set the image
                    this.tiles[row][col].setImage(map[row][col].getCurrentImage());
                }
            }
        }

    }



}
