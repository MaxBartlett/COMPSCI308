package initialize;

import cellular.fire.BurningCell;
import cellular.fire.BurntCell;
import cellular.fire.TreeCell;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.*;
import nodes.Node;
import nodes.StaticNode;
import xml.XMLParser;
import game.FireGame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import grids.*;
import java.io.IOException;

/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class extends the Handle class to implement the Spreading of Fire cellular automaton
 *  Assumptions: There is an XML parser and config file, along with a "Game" hierarchy and "Handle" hierarchy
 *  Dependencies: Arrays, ArrayList, HashMap, Random, game.Game, grids.Grid, File, TilePane, Shape, xml.XMLParser
 *  Example: Handle h = new FireHandle();
 */
public class FireHandle extends Handle {

    //Keys for Spreading of Fire cells
    public static final char BURNING_CELL_KEY = 'B';
    public static final char BURNT_CELL_KEY = 'U';
    public static final char TREE_CELL_KEY = 'T';

    //Config parameter handling
    private double probCatch; //probability that a tree next to a burning cell will catch on fire
    public static final String FIRE_PROB_CATCH = "probCatch";
    public static final double DEFAULT_PROBCATCH = 0.5;

    public static final double DEFAULT_BURNING_CELL_WEIGHTED_PROB = 0.34;
    public static final double DEFAULT_BURNT_CELL_WEIGHTED_PROB = 0.33;
    public static final double DEFAULT_TREE_CELL_WEIGHTED_PROB = 0.33;

    /**
     *constructor for FireHandle object, no parameters to facilitate reflection
     */
    public FireHandle(){
        super(new ArrayList<>(), new HashMap<>());
        this.numCellTypes = 3;
        this.DEFAULT_WEIGHTED_PROBABILITIES = new ArrayList<>(Arrays.asList(DEFAULT_BURNING_CELL_WEIGHTED_PROB,DEFAULT_BURNT_CELL_WEIGHTED_PROB, DEFAULT_TREE_CELL_WEIGHTED_PROB));
    }

    /**
     * Get spreading of fire config parameters
     * @param xParse XMLParser to read config parameters
     */
    public void getConfigurationParameters(XMLParser xParse)
    {
        ArrayList<String> fireConfig = new ArrayList<String>();
        fireConfig.add(FIRE_PROB_CATCH);
        xParse.setDataFields(fireConfig);

        HashMap<String, String> fireMap = null;
        try {
            fireMap = xParse.getData(configFile);
            this.probCatch = Double.parseDouble(fireMap.get(FIRE_PROB_CATCH).replaceAll("\\s+",""));
        }
        catch(IOException e)
        {
            System.err.println("IOException when parsing FireHandle config parameters, setting value to default");
            this.probCatch = DEFAULT_PROBCATCH;
        }
        catch(NumberFormatException f)
        {
            System.err.println("NumberFormatException when parsing FireHandle config parameter probCatch, setting value to default");
            this.probCatch = DEFAULT_PROBCATCH;
        }
    }

    /**
     * Initialize grid for spreading of fire simulation
     * @param xParse XMLParser used for specific (per cell) grid initialization
     * @param tpane GUI TilePane for displaying the cells of the gird
     * @return
     */
    public FireGame initializeGrid(XMLParser xParse, TilePane tpane)
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

        ArrayList<Character> cellCharacters = new ArrayList<>(Arrays.asList(BURNING_CELL_KEY, BURNT_CELL_KEY, TREE_CELL_KEY));
        ArrayList<Character>[] cellTypes = initializeCellTypes(gridX, gridY, cellCharacters, rowStringMap, xParse);

        StaticNode[][] grid = initializeCellArray(gridX, gridY, tpane, cellTypes);
        Grid lifeCreate = new SquareGrid(grid, gridX, gridY, false); //operate on grid of nodes
        initializeGUIArray(gridX, gridY, lifeCreate, tpane, grid);
        configureTilePane(tpane, gridY);

        ArrayList<Node> nodeList = new ArrayList<>();
        HashMap<Node, ArrayList<Node>> nodeAdjacentMaps = new HashMap<Node, ArrayList<Node>>();
        initializeGameNodeLists(gridX, gridY, grid, lifeCreate, nodeList, nodeAdjacentMaps);
        giveNodesTotalMap(nodeList, nodeAdjacentMaps);

        return new FireGame(nodeAdjacentMaps, nodeList);

    }

    /**
     * Initialize the cell array for a spreading fire simulation
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

                if (rowCellType.get(j) == BURNING_CELL_KEY)
                {
                    nodeGrid[i][j] = new StaticNode(new BurningCell(new Rectangle()));

                }
                else if (rowCellType.get(j) == BURNT_CELL_KEY)
                {
                    nodeGrid[i][j] = new StaticNode(new BurntCell(new Rectangle()));
                }
                else if (rowCellType.get(j) == TREE_CELL_KEY)
                {
                    nodeGrid[i][j] = new StaticNode(new TreeCell(new Rectangle(), this.probCatch));
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
     * @param fireCreate Grid object for the array of nodes
     * @param nodeList empty array list of nodes
     * @param nodeAdjacentMaps empty map of nodes to neighbors
     */
    public void initializeGameNodeLists(int gridX, int gridY, Node[][] grid, Grid fireCreate, ArrayList<Node> nodeList, HashMap<Node, ArrayList<Node>> nodeAdjacentMaps)
    {
        for (int i = 0; i < gridX; i++) //build requirements to make a Game object
        {
            for (int j = 0; j < gridY; j++)
            {
                nodeList.add(grid[i][j]);
                nodeAdjacentMaps.put(grid[i][j], fireCreate.getAdjacentNodesVonNeumannNeighborhood(i,j));
            }
        }
    }
}

