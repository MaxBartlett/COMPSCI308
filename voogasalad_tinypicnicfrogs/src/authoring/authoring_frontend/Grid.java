package authoring.authoring_frontend;

import authoring.authoring_backend.GameManager;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Grid class to store the total grid in a map.
 *
 * @author Allen
 */
public class Grid {
    private GridPane mapGridPane;
    private static final int DEFAULT_WIDTH  = 30;
    private static final int DEFAULT_HEIGHT = 20;
    private int cellWidth;
    private int cellHeight;
    private ArrayList<ArrayList<Cell>> myCells;
    private int gridWidth;
    private int gridHeight;
    private String programName;
    private GameManager gameManager;
    private StackPane lastSelectedCell = null;
    private MapManager mapManager;

    /**
     * Default constructor makes a 30x20 grid.
     * @param name Program name.
     * @param myManager GameManager of current game.
     */
    Grid(String name, int cellWidth, int cellHeight, GameManager myManager, MapManager m){
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, cellWidth, cellHeight, name, myManager, m);
    }

    /**
     * Constructor
     * @param width Width in squares.
     * @param height Height in squares.
     * @param name Program name.
     * @param myManager GameManager of current game.
     */
    Grid(int width, int height, int cWidth, int cHeight, String name, GameManager myManager, MapManager m){
        cellWidth = cWidth;
        cellHeight = cHeight;
        gridWidth = width;
        gridHeight = height;
        programName = name;
        gameManager = myManager;
        mapGridPane = new GridPane();
        mapManager = m;
        myCells = new ArrayList<>();
        setupArrayList(width, height);
    }

    /**
     * Sets up the ArrayList of ArrayLists to store cells
     * @param width Width of map
     * @param height Height of map
     */
    private void setupArrayList(int width, int height){
        for(int i=0;i<height;i++){
            myCells.add(new ArrayList<>());
            for(int j=0;j<width;j++){
                myCells.get(i).add(new Cell(j, i));
                mapGridPane.add(createCell(), j, i);
            }
        }
    }

    /**
     * Creates a cell.
     * @return A StackPane with the cell contents.
     */
    private StackPane createCell(){
        StackPane thisCell = new StackPane();
        thisCell.setMinSize(cellWidth, cellHeight);
        thisCell.setStyle("-fx-border-color: black;");

        thisCell.setOnMouseClicked(event -> {
            Actor activeActor = ActiveItem.getActiveItem(programName);
            int x = GridPane.getColumnIndex(thisCell);
            int y = GridPane.getRowIndex(thisCell);
            if(activeActor != null){
                if(event.isShiftDown() && lastSelectedCell != null){
                    shiftDraw(activeActor, x, y);
                }
                else {
                    addActor(thisCell, activeActor, x, y);
                }
            }
            else {
                if(thisCell.getChildren().size() > 0){
                    thisCell.getChildren().remove(thisCell.getChildren().size()-1);
                    ArrayList<Actor> actorsOfCell = myCells.get(x).get(y).getActors();
                    gameManager.deleteActor(actorsOfCell.get(actorsOfCell.size()-1).getActorPrototypeID()+x+"-"+y+"-"+(thisCell.getChildren().size()-1));
                    myCells.get(x).remove(y);
                }
            }
            lastSelectedCell = thisCell;
        });

        return thisCell;
    }

    /**
     * Checks cases for the shift draw box function
     * @param activeActor The currently active actor
     * @param x Current x
     * @param y Current y
     */
    private void shiftDraw(Actor activeActor, int x, int y) {
        int lastX = GridPane.getColumnIndex(lastSelectedCell);
        int lastY = GridPane.getRowIndex(lastSelectedCell);
        if(x > lastX && y >lastY){
            for(int i=lastX;i<=x;i++){
                for(int j=lastY;j<=y;j++){
                    StackPane currentCell = (StackPane)getGridPaneNodeAt(i, j);
                    addActor(currentCell, activeActor, i, j);
                }
            }
        }
        else if(x > lastX && y <lastY){
            for(int i=lastX;i<=x;i++){
                for(int j=y;y<=lastY;j++){
                    StackPane currentCell = (StackPane)getGridPaneNodeAt(i, j);
                    addActor(currentCell, activeActor, i, j);
                }
            }
        }
        else if(x < lastX && y >lastY){
            for(int i=x;i<=lastX;i++){
                for(int j=lastY;j<=y;j++){
                    StackPane currentCell = (StackPane)getGridPaneNodeAt(i, j);
                    addActor(currentCell, activeActor, i, j);
                }
            }
        }
        else if(x < lastX && y < lastY){
            for(int i=x;i<=lastX;i++){
                for(int j=y;j<=lastY;j++){
                    StackPane currentCell = (StackPane)getGridPaneNodeAt(i, j);
                    addActor(currentCell, activeActor, i, j);
                }
            }
        }
        else if(x == lastX && y < lastY){
            for(int i=y;i<=lastY;i++){
                StackPane currentCell = (StackPane)getGridPaneNodeAt(x, i);
                addActor(currentCell, activeActor, x, i);
            }
        }
        else if(x == lastX && y > lastY){
            for(int i=lastY;i<=y;i++){
                StackPane currentCell = (StackPane)getGridPaneNodeAt(x, i);
                addActor(currentCell, activeActor, x, i);
            }
        }
        else if(y == lastY && x < lastX){
            for(int i=x;i<=lastX;i++){
                StackPane currentCell = (StackPane)getGridPaneNodeAt(i, y);
                addActor(currentCell, activeActor, i, y);
            }
        }
        else if(y == lastY && x > lastX){
            for(int i=lastX;i<=x;i++){
                StackPane currentCell = (StackPane)getGridPaneNodeAt(i, y);
                addActor(currentCell, activeActor, i, y);
            }
        }
    }

    /**
     * Adds an actor to a specific cell
     * @param cell Cell to add actor to
     * @param actor Actor to add
     * @param x x position of cell
     * @param y y position of cell
     */
    private void addActor(StackPane cell, Actor actor, int x, int y){
        cell.getChildren().add(actor.getActorImage());
        myCells.get(y).get(x).addActor(actor);
        gameManager.createActor(actor.getActorPrototypeID(), cellWidth*x, cellHeight*y, cell.getChildren().size());
    }

    /**
     * Adds actor without knowing specific cell
     * @param actor Actors to add
     * @param x x position of cell
     * @param y y position of cell
     */
    public void addActor(Actor actor, int x, int y){
        StackPane cell = (StackPane)getGridPaneNodeAt(x, y);
        addActor(cell, actor, x, y);
    }

    /**
     * Adds an actor to the frontend only
     * @param actor Actor to add
     * @param x x coordinate of actor
     * @param y y coordinate of actor
     * @param z z coordinate of actor
     */
    public void addActorFrontendOnly(Actor actor, int x, int y, int z){
        //divide by cell size
        System.out.println("Adding an actor at (" + x + ", " + y + ")");
        StackPane cell = (StackPane)getGridPaneNodeAt(x, y);
        cell.getChildren().add(z-1, actor.getActorImage());
        myCells.get(y).get(x).addActor(actor);
    }

    /**
     * Gets the GridPane of this grid.
     * @return GridPane
     */
    GridPane getGridPane(){
        return mapGridPane;
    }

    /**
     * Gets a ScrollPane of this grid
     * @return ScrollPane
     */
    ScrollPane getScrollPane(){
        ScrollPane thisMap = new ScrollPane(mapGridPane);
        thisMap.setPrefViewportHeight(575);
        thisMap.setPrefViewportWidth(725);
        return thisMap;
    }

    /**
     * Gets matrix of cells in this grid.
     * @return Matrix of cells contained in this grid.
     */
    ArrayList<ArrayList<Cell>> getCells(){
        return myCells;
    }


    /**
     * Note: the method is probably in the wrong class
     *
     * when the user hovers over an actor in the Grid, ideally we could get info on that actor
     * @param actorIV
     */
    public void hoverInfo(ImageView actorIV){
        Group cell = new Group();

        Tooltip tooltip = new Tooltip();
        tooltip.setText(
                "\n Actor ID, coordinates, name" //TODO: get the right info for the actors
        );

        //tooltip.setFont(new Font("Arial", 16));
        Button actor = new Button("", actorIV);
        actor.setTooltip(new Tooltip(tooltip.getText()));

        cell.getChildren().add(actor);
    }

    /**
     * Changes the dimensions of this grid
     * @param newWidth New width
     * @param newHeight New height
     * @param size New cell size
     */
    void changeDimensions(int newWidth, int newHeight, int size){
        resizeWidth(newWidth);
        resizeHeight(newWidth, newHeight);

        gridWidth = newWidth;
        gridHeight = newHeight;
        ObservableList<Node> children = mapGridPane.getChildren();

        ArrayList<Node> toRemove = new ArrayList<>();
        for(Iterator<Node> iter = children.iterator();iter.hasNext();){
            Node n = iter.next();
            StackPane thisPane = (StackPane) n;
            thisPane.setMinSize(size, size);
            if(GridPane.getColumnIndex(n) > newWidth-1 || GridPane.getRowIndex(n) > newHeight-1){
                toRemove.add(n);
                iter.remove();
            }
        }
        mapGridPane.getChildren().removeAll(toRemove);

    }

    /**
     * Resizes the height of the Grid
     * @param newWidth New width
     * @param newHeight New height
     */
    private void resizeHeight(int newWidth, int newHeight) {
        if(newHeight < gridHeight){
            myCells.subList(newHeight-1, myCells.size()-1).clear();
        }
        else if(newHeight > gridHeight){
            for(int i=0;i<newHeight-gridHeight;i++){
                myCells.add(new ArrayList<>());
                for(int j=0;j<newWidth;j++){
                    myCells.get(myCells.size()-1).add(new Cell(j, myCells.size()-1));
                    mapGridPane.add(createCell(), j, myCells.size()-1);
                }
            }
        }
    }

    /**
     * Resizes the width of the Grid
     * @param newWidth New width of the grid
     */
    private void resizeWidth(int newWidth) {
        if(newWidth < gridWidth){
            for (ArrayList<Cell> myCell : myCells) {
                myCell.subList(newWidth - 1, myCell.size() - 1).clear();
            }
        }
        else if(newWidth > gridWidth){
            for(int i=0;i<myCells.size();i++){
                for(int j=0;j<newWidth-gridWidth;j++){
                    myCells.get(i).add(new Cell(myCells.get(i).size()-1, i));
                    mapGridPane.add(createCell(), myCells.get(i).size()-1, i);
                }
            }
        }
    }

    /**
     * Finds the node at x, y of the GridPane
     * @param x x to search
     * @param y y to search
     * @return Node found or null
     */
    private Node getGridPaneNodeAt(int x, int y){
        Node result = null;
        ObservableList<Node> gridPaneChildren = mapGridPane.getChildren();
        for(Node n:gridPaneChildren){
            if(GridPane.getColumnIndex(n) == x && GridPane.getRowIndex(n) == y){
                result = n;
                break;
            }
        }
        return result;
    }

}
