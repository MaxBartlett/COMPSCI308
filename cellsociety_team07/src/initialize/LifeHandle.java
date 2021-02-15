package initialize;

import cellular.life.DeadCell;
import cellular.life.LiveCell;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.*;
import nodes.Node;
import nodes.StaticNode;
import xml.XMLParser;
import game.LifeGame;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;
import grids.*;

/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class extends the Handle class to implement the Game of Life cellular automaton
 *  Assumptions: There is an XML parser and config file, along with a "Game" hierarchy and "Handle" hierarchy
 *  Dependencies: Arrays, ArrayList, HashMap, Random, game.Game, grids.Grid, File, TilePane, Shape, xml.XMLParser
 *  Example: Handle h = new LifeHandle();
 */
public class LifeHandle extends Handle {

    //Keys for Game of Life cells
    public static final char LIVE_CELL_KEY = 'L';
    public static final char DEAD_CELL_KEY = 'D';

    public static final double DEFAULT_LIVE_CELL_WEIGHTED_PROB = 0.5;
    public static final double DEFAULT_DEAD_CELL_WEIGHTED_PROB = 0.5;

    /**
     * constructor for LifeHandle object, no parameters to facilitate reflection
     */
    public LifeHandle(){
        super(new ArrayList<>(), new HashMap<>());
        this.numCellTypes = 2;
        this.DEFAULT_WEIGHTED_PROBABILITIES = new ArrayList<>(Arrays.asList(DEFAULT_LIVE_CELL_WEIGHTED_PROB,DEFAULT_DEAD_CELL_WEIGHTED_PROB));
    }

    /**
     * Get Game of Life config parameters (there are none for Game of Life)
     * @param xParse XMLParser to read config parameters
     */
    public void getConfigurationParameters(XMLParser xParse)
        {
            return;
        }

    /**
     * Initialize grid for game of life simulation
     * @param xParse XMLParser used for specific (per cell) grid initialization
     * @param tpane GUI TilePane for displaying the cells of the gird
     * @return LifeGame object for main simulation to happen
     * @throws IOException when XMLParser can't read from file correctly
     */
    public LifeGame initializeGrid(XMLParser xParse, TilePane tpane) throws IOException
    {

        int[] gridDimensions = getGridDimensions(xParse);
        int gridX = gridDimensions[0];
        int gridY = gridDimensions[1];

        ArrayList<String> rowStrings = generateRowlist(gridX);
        xParse.setDataFields(rowStrings);

        HashMap<String, String> rowStringMap = null;
        try{
            rowStringMap = xParse.getData(configFile);}
        catch (IOException e)
        {
            System.err.println("IOException in parsing row strings for grid ");
        }

        ArrayList<Character> cellCharacters = new ArrayList<>(Arrays.asList(LIVE_CELL_KEY, DEAD_CELL_KEY));
        ArrayList<Character>[] cellTypes = initializeCellTypes(gridX, gridY, cellCharacters, rowStringMap, xParse);
        System.out.println(cellTypes);

        StaticNode[][] grid = initializeCellArray(gridX, gridY, tpane, cellTypes);
        Grid lifeCreate = new SquareGrid(grid, gridX, gridY, false); //operate on grid of nodes
        initializeGUIArray(gridX, gridY, lifeCreate, tpane, grid);
        configureTilePane(tpane, gridY);

        ArrayList<Node> nodeList = new ArrayList<>();
        HashMap<Node, ArrayList<Node>> nodeAdjacentMaps = new HashMap<>();
        initializeGameNodeLists(gridX, gridY, grid, lifeCreate, nodeList, nodeAdjacentMaps);
        giveNodesTotalMap(nodeList, nodeAdjacentMaps);

        return new LifeGame(nodeAdjacentMaps, nodeList);

    }

    /**
     * Initialize the cell array for a game of life
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param tpane TilePane object for graphical objects on grid
     * @param cellTypes Characters representing cell types for the simulation
     * @return 2d array of nodes initialized to the correct cell types
     */
    public StaticNode[][] initializeCellArray(int gridX, int gridY, TilePane tpane, ArrayList<Character>[] cellTypes)
    {

        StaticNode[][] nodeGrid = new StaticNode[gridX][gridY];

        for (int i = 0; i < gridX; i++) //cell initialize in the grid
        {
            ArrayList<Character> rowCellType = cellTypes[i];

            for (int j = 0; j < gridY; j++)
            {

                if (rowCellType.get(j) == LIVE_CELL_KEY)
                {
                    nodeGrid[i][j] = new StaticNode(new LiveCell(new Rectangle()));

                }
                else if (rowCellType.get(j) == DEAD_CELL_KEY)
                {
                    nodeGrid[i][j] = new StaticNode(new DeadCell(new Rectangle()));
                }
            }
        }

        return nodeGrid;
    }

    /**
     * Initialize neighbor lists for the nodes in a grid
     * @param gridX X dimension for the grid
     * @param gridY Y dimension for the grid
     * @param grid Array of nodes
     * @param lifeCreate Grid object for the array of nodes
     * @param nodeList empty array list of nodes
     * @param nodeAdjacentMaps empty map of nodes to neighbors
     */
    public void initializeGameNodeLists(int gridX, int gridY, Node[][] grid, Grid lifeCreate, ArrayList<Node> nodeList, HashMap<Node, ArrayList<Node>> nodeAdjacentMaps)
    {
        for (int i = 0; i < gridX; i++) //build requirements to make a Game object
        {
            for (int j = 0; j < gridY; j++)
            {
                nodeList.add(grid[i][j]);
                nodeAdjacentMaps.put(grid[i][j], lifeCreate.getAdjacentNodesMooreNeighborhood(i,j));
            }
        }
    }

}

