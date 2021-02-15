package authoring.authoring_frontend;

import authoring.authoring_backend.GameManager;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

/**
 * Map class that stores the grid and cells.
 *
 * @author Allen Qiu
 */
public class Map {
    private int id;
    private Grid myGrid;
    private int width;
    private int height;
    private int cellWidth;
    private int cellHeight;
    private String programName;
    private MapManager mapManager;

    /**
     * Default constructor that starts ordering at ID 1.
     * @param mapWidth Width of map in squares.
     * @param mapHeight Height of map in squares.
     * @param pName Name of the program.
     * @param myManager GameManager of the game.
     */
    Map(int mapWidth, int mapHeight, int cWidth, int cHeight, String pName, GameManager myManager, MapManager m){
        this(1, mapWidth, mapHeight, cWidth, cHeight, pName, myManager, m);
    }

    /**
     * Constructor
     * @param mapID ID of this map
     * @param mapWidth Width of map in squares.
     * @param mapHeight Height of map in squares.
     * @param pName Name of the program.
     * @param myManager GameManager of the game.
     */
    Map(int mapID, int mapWidth, int mapHeight, int cWidth, int cHeight, String pName, GameManager myManager, MapManager m){
        cellHeight = cHeight;
        cellWidth = cWidth;
        id = mapID;
        width = mapWidth;
        height = mapHeight;
        programName = pName;
        mapManager = m;
        myGrid = new Grid(width, height, cWidth, cHeight, programName, myManager, m);
    }

    /**
     * Gets the ID of the map.
     * @return The ID of the map.
     */
    public int getMapID(){
        return id;
    }

    /**
     * String print function.
     * @return Map and its ID.
     */
    public String toString(){
        return "Map " + id;
    }

    /**
     * Gets a GridPane of the map.
     * @return GridPane of the map.
     */
    public GridPane getGridPane(){
        return myGrid.getGridPane();
    }

    /**
     * Gets a ScrollPane of the map
     * @return ScrollPane of the map
     */
    ScrollPane getScrollPane(){
        return myGrid.getScrollPane();
    }

    /**
     * Gets the grid in this map.
     * @return Grid contained in this map.
     */
    public Grid getGrid(){
        return myGrid;
    }

    /**
     * Gets width of map
     * @return Width
     */
    public int getWidth(){
        return width;
    }

    /**
     * Gets height of map
     * @return Height
     */
    public int getHeight(){
        return height;
    }

    /**
     * Changes width of map
     * @param newWidth New width
     */
    void changeWidth(int newWidth){
        width = newWidth;
    }

    /**
     * Changes height of map
     * @param newHeight New height
     */
    void changeHeight(int newHeight){
        height = newHeight;
    }
}
