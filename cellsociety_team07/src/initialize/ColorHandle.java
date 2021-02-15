package initialize;

import game.ColorGame;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.*;
import nodes.MovingNode;
import nodes.Node;
import xml.XMLParser;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Arrays;
import grids.*;
import cellular.rpc.*;

/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class extends the Handle class to implement the color cellular automaton
 *  Assumptions: There is an XML parser and config file, along with a "Game" hierarchy and "Handle" hierarchy
 *  Dependencies: Arrays, ArrayList, HashMap, Random, game.Game, grids.Grid, File, TilePane, Shape, xml.XMLParser
 *  Example: Handle h = new ColorHandle();
 */
public class ColorHandle extends Handle {

    public static final char BLUE_CELL_KEY = 'B';
    public static final char GREEN_CELL_KEY = 'G';
    public static final char RED_CELL_KEY = 'R';
    public static final char WHITE_CELL_KEY = 'W';
    public static final double DEFAULT_BLUE_CELL_WEIGHTED_PROB = 0.25;
    public static final double DEFAULT_WHITE_CELL_WEIGHTED_PROB = 0.25;
    public static final double DEFAULT_RED_CELL_WEIGHTED_PROB = 0.25;
    public static final double DEFAULT_GREEN_CELL_WEIGHTED_PROB = 0.25;

    /**
     * Constructor for a ColorHandle object
     */
    public ColorHandle(){
        super(new ArrayList<>(), new HashMap<>());
        this.numCellTypes = 4;
        this.DEFAULT_WEIGHTED_PROBABILITIES = new ArrayList<>(Arrays.asList(DEFAULT_BLUE_CELL_WEIGHTED_PROB,DEFAULT_WHITE_CELL_WEIGHTED_PROB, DEFAULT_RED_CELL_WEIGHTED_PROB, DEFAULT_GREEN_CELL_WEIGHTED_PROB));
    }

    //no config parameters for Color Game
    public void getConfigurationParameters(XMLParser xParse)
    {
        return;
    }

    /**
     * Initialize grid for a Color Game
     * @param xParse XMLParser used for specific (per cell) grid initialization
     * @param tpane GUI TilePane for displaying the cells of the gird
     * @return ColorGame object for main sim
     * @throws IOException when XMLParser can't read from file correctly
     */
    public ColorGame initializeGrid(XMLParser xParse, TilePane tpane) throws IOException
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

        ArrayList<Character> cellCharacters = new ArrayList<>(Arrays.asList(BLUE_CELL_KEY, GREEN_CELL_KEY, WHITE_CELL_KEY, RED_CELL_KEY));
        ArrayList<Character>[] cellTypes = initializeCellTypes(gridX, gridY, cellCharacters, rowStringMap, xParse);
        System.out.println(cellTypes);

        MovingNode[][] grid = initializeCellArray(gridX, gridY, tpane, cellTypes);
        Grid lifeCreate = new SquareGrid(grid, gridX, gridY, false); //operate on grid of nodes
        initializeGUIArray(gridX, gridY, lifeCreate, tpane, grid);
        configureTilePane(tpane, gridY);

        ArrayList<Node> nodeList = new ArrayList<>();
        HashMap<Node, ArrayList<Node>> nodeAdjacentMaps = new HashMap<>();
        initializeGameNodeLists(gridX, gridY, grid, lifeCreate, nodeList, nodeAdjacentMaps);
        giveNodesTotalMap(nodeList, nodeAdjacentMaps);

        return new ColorGame(nodeAdjacentMaps, nodeList);

    }


    /**
     * Initialize the cell array for a color game
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param tpane TilePane object for graphical objects on grid
     * @param cellTypes Characters representing cell types for the simulation
     * @return 2d array of nodes initialized to the correct cell types
     */
    public MovingNode[][] initializeCellArray(int gridX, int gridY, TilePane tpane, ArrayList<Character>[] cellTypes)
    {

        MovingNode[][] nodeGrid = new MovingNode[gridX][gridY];

        for (int i = 0; i < gridX; i++) //cell initialize in the grid
        {
            ArrayList<Character> rowCellType = cellTypes[i];

            for (int j = 0; j < gridY; j++)
            {

                if (rowCellType.get(j) == BLUE_CELL_KEY)
                {
                    nodeGrid[i][j] = new MovingNode(new BlueCell(new Rectangle()));

                }
                else if (rowCellType.get(j) == GREEN_CELL_KEY)
                {
                    nodeGrid[i][j] = new MovingNode(new GreenCell(new Rectangle()));
                }
                else if (rowCellType.get(j) == WHITE_CELL_KEY)
                {
                    nodeGrid[i][j] = new MovingNode(new WhiteCell(new Rectangle()));
                }
                else if (rowCellType.get(j) == RED_CELL_KEY)
                {
                    nodeGrid[i][j] = new MovingNode(new RedCell(new Rectangle()));
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


