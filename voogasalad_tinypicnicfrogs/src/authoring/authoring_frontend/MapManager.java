package authoring.authoring_frontend;

import authoring.authoring_backend.GameManager;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Manages maps created by the user.
 *
 * @author Allen Qiu
 */
public class MapManager {
    //stores and keeps map names to map objects
    private HashMap<String, Map> gameMaps = new HashMap<>();
    private int numMaps = 0;
    private String programName;
    private String activeMapName;
    private BorderPane activeMap = new BorderPane();
    private ArrayList<Portal> portals = new ArrayList<>();
    private GameManager gameManager;
    private int cellSize;
    private int initialWidth;
    private int initialHeight;

    /**
     * Constructor
     * @param pName Program name.
     * @param myManager GameManager of the game.
     */
    MapManager(String pName, GameManager myManager){
        programName = pName;
        gameManager = myManager;
    }

    /**
     * Creates a new map.
     * @param name Name of the map.
     * @param width Width of map in cells.
     * @param height Height of map in cells.
     */
    private void createMap(String name, int width, int height, MapManager mapManager){
        //System.out.println("c" + cellSize);
        gameMaps.put(name, new Map(width, height, cellSize, cellSize, programName, gameManager, mapManager));
    }

    /**
     * Creates map but auto increments name.
     * @param width Width of map in cells.
     * @param height Height of map in cells.
     * @return Map name
     */
    String createMap(int width, int height){
        numMaps++;
        createMap("Map " + numMaps, width, height, this);
        return "Map " + numMaps;
    }

    /**
     * Removes a map.
     * @param toRemove Name of map to remove.
     */
    public void removeMap(String toRemove){
        gameMaps.remove(toRemove);
    }

    /**
     * Removes multiple maps.
     * @param toRemove List of map name to remove.
     */
    void removeMap(List<String> toRemove){
        for(String mapToRemove:toRemove){
            gameMaps.remove(mapToRemove);
        }
    }

    /**
     * Gets a map from its name.
     * @param name Name of the map
     * @return The map.
     */
    public Map getMap(String name){
        return gameMaps.get(name);
    }

    /**
     * Renames a map
     * @param oldName The current name of the map.
     * @param newName The new name of the map.
     */
    public void renameMap(String oldName, String newName){
        gameMaps.put(newName, gameMaps.get(oldName));
        gameMaps.remove(oldName);
    }

    /**
     * Gets the currently active map.
     * @return Cureently active map.
     */
    ScrollPane getActiveMap(){
        return new ScrollPane(activeMap);
    }

    /**
     * Sets the currently active map.
     * @param name Name of the new active map.
     */
    void setActiveMap(String name){
        if(gameMaps.containsKey(name)){
            activeMapName = name;
            activeMap.setCenter(gameMaps.get(name).getScrollPane());
        }
    }

    /**
     * Gets a list of names to maps.
     * @return Map of names to maps.
     */
    public HashMap<String, Map> getGameMaps(){
        return gameMaps;
    }

    /**
     * Gets names of all maps.
     * @return List of map names.
     */
    List<String> getMapList(){
        return new ArrayList<>(gameMaps.keySet());
    }

    /**
     * Adds a portal.
     * @param toAdd Portal to add.
     */
    void addPortal(Portal toAdd){
        portals.add(toAdd);
    }

    /**
     * Removes a portal.
     * @param toRemove Portal to remove.
     */
    public void removePortal(Portal toRemove){
        portals.remove(toRemove);
    }

    /**
     * Gets the name of the active map
     * @return Active map name
     */
    public String getActiveMapName(){
        return activeMapName;
    }

    /**
     * Finds a new active map to display if current active is deleted
     * @param deletedMaps Maps that are deleted
     */
    void findNewActiveMap(List<String> deletedMaps){
        for(String mapName:gameMaps.keySet()){
            if(!deletedMaps.contains(mapName)){
                setActiveMap(mapName);
            }
        }
    }

    /**
     * Changes dimensions of all maps
     * @param newWidth New width
     * @param newHeight New height
     * @param size Cell size
     */
    public void changeMapDimensions(int newWidth, int newHeight, int size){
        cellSize = size;
        if(newWidth < getMap(activeMapName).getWidth() || newHeight < getMap(activeMapName).getHeight()){
            clipPortals(newWidth, newHeight);
            clipActors(newWidth, newHeight);
        }

        getMap(activeMapName).changeWidth(newWidth);
        getMap(activeMapName).changeHeight(newHeight);
        getMap(activeMapName).getGrid().changeDimensions(newWidth, newHeight, cellSize);

        resizeMapImages();

        setActiveMap(activeMapName);
    }

    /**
     * Resizes all map images to fit a new cell size
     */
    private void resizeMapImages() {
        for(java.util.Map.Entry<String, Map> mapEntry:gameMaps.entrySet()){
            Grid thisGrid = mapEntry.getValue().getGrid();
            for(Node n:thisGrid.getGridPane().getChildren()){
                StackPane thisStack = (StackPane)n;
                for(Node nn:thisStack.getChildren()){
                    ImageView thisImage = (ImageView) nn;
                    thisImage.setFitHeight(cellSize);
                    thisImage.setFitWidth(cellSize);
                }
            }
        }
    }

    /**
     * Clips actors out of range of new size
     * @param newWidth New width
     * @param newHeight New height
     */
    private void clipActors(int newWidth, int newHeight) {
        Grid thisGrid = getMap(activeMapName).getGrid();
        for(int i=0;i<thisGrid.getCells().size();i++){
            for(int j=0;j<thisGrid.getCells().get(i).size();j++){
                if((j+1 > newWidth || i+1 > newHeight) && thisGrid.getCells().get(i).get(j).getActors().size() > 0){
                    //delete all these actors
                    for(Actor thisActor:thisGrid.getCells().get(i).get(j).getActors()){
                        gameManager.deleteActor(thisActor.getActorPrototypeID()+i+"-"+j+"-"+(thisGrid.getCells().get(i).get(j).getActors().size()-1));
                    }
                }
            }
        }
    }

    /**
     * Clips portals outside of width and height
     * @param newWidth New width
     * @param newHeight New height
     */
    private void clipPortals(int newWidth, int newHeight) {
        portals.removeIf(p -> p.withinWidth(newWidth) || p.withinHeight(newHeight));
    }

    /**
     * Gets cell size
     * @return Cell size
     */
    public int getCellSize(){
        return cellSize;
    }

    /**
     * Sets cell size
     * @param size New size
     */
    public void setCellSize(int size){
        cellSize = size;
    }

    /**
     * Sets initial width of map
     * @param w Initial width
     */
    public void setInitialWidth(int w){
        initialWidth = w;
    }

    /**
     * Sets initial height of map
     * @param h Initial height
     */
    public void setInitialHeight(int h){
        initialHeight = h;
    }

    /**
     * Gets initial width of map
     * @return Initial width
     */
    int getInitialWidth(){
        return initialWidth;
    }

    /**
     * Gets initial height of map
     * @return Initial height
     */
    int getInitialHeight(){
        return initialHeight;
    }

}
