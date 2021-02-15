package initialize;

import cellular.wator.FishCell;
import cellular.wator.KelpCell;
import cellular.wator.SharkCell;
import cellular.wator.WaterCell;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.*;
import nodes.MovingNode;
import nodes.Node;
import xml.XMLParser;
import game.WaterWorldGame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import grids.*;
import java.io.IOException;

/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class extends the Handle class to implement the WaTor World cellular automaton
 *  Assumptions: There is an XML parser and config file, along with a "Game" hierarchy and "Handle" hierarchy
 *  Dependencies: Arrays, ArrayList, HashMap, Random, game.Game, grids.Grid, File, TilePane, Shape, xml.XMLParser
 *  Example: Handle h = new WatorHandle();
 */
public class WatorHandle extends Handle {

    //Cell keys for Wator World sim
    public static final char FISH_CELL_KEY = 'F';
    public static final char SHARK_CELL_KEY = 'S';
    public static final char KELP_CELL_KEY = 'K';
    public static final char WATER_CELL_KEY = 'W';

    private int sharkSpawnTurns;
    private int fishSpawnTurns;

    public static final String SHARK_SPAWN_TURNS = "sharkSpawnTurns";
    public static final String FISH_SPAWN_TURNS = "sharkSpawnTurns";

    public static final int INITIAL_TURNS_ALIVE = 0;
    public static final int DEFAULT_SHARK_SPAWN_TURNS = 3;
    public static final int DEFAULT_FISH_SPAWN_TURNS = 3;

    public static final double DEFAULT_KELP_CELL_WEIGHTED_PROB = 0.2;
    public static final double DEFAULT_WATER_CELL_WEIGHTED_PROB = 0.6;
    public static final double DEFAULT_FISH_CELL_WEIGHTED_PROB = 0.15;
    public static final double DEFAULT_SHARK_CELL_WEIGHTED_PROB = 0.05;

    /**
     * Constructor for WatorHandle object
     */
    public WatorHandle(){
        super(new ArrayList<>(), new HashMap<>());
        this.numCellTypes = 4;
        this.DEFAULT_WEIGHTED_PROBABILITIES = new ArrayList<>(Arrays.asList(DEFAULT_FISH_CELL_WEIGHTED_PROB,DEFAULT_KELP_CELL_WEIGHTED_PROB,DEFAULT_WATER_CELL_WEIGHTED_PROB,DEFAULT_SHARK_CELL_WEIGHTED_PROB));
    }

    /**
     * Get config parameters for WatorWorld sim: shark spawn turns, fish spawn turns
     * @param xParse XMLParser to read config parameters
     */
    public void getConfigurationParameters(XMLParser xParse)
    {
        ArrayList<String> watorConfig = new ArrayList<>();
        watorConfig.add(SHARK_SPAWN_TURNS);
        watorConfig.add(FISH_SPAWN_TURNS);
        xParse.setDataFields(watorConfig);

        HashMap<String, String> segMap = null;
        try {
            segMap = xParse.getData(configFile);
            this.sharkSpawnTurns = Integer.parseInt(segMap.get(SHARK_SPAWN_TURNS).replaceAll("\\s+",""));
            this.fishSpawnTurns = Integer.parseInt(segMap.get(FISH_SPAWN_TURNS).replaceAll("\\s+",""));
        }
        catch(IOException e)
        {
            System.err.println("IO Exception parsing configuration parameters for WatorHandle, setting config parameters to defaults");
            this.sharkSpawnTurns = DEFAULT_SHARK_SPAWN_TURNS;
            this.fishSpawnTurns = DEFAULT_FISH_SPAWN_TURNS;
        }
        catch(NumberFormatException f)
        {
            System.err.println("Parsing error in configuration parameters for WatorHandle, setting config parameters to defaults");
            this.sharkSpawnTurns = DEFAULT_SHARK_SPAWN_TURNS;
            this.fishSpawnTurns = DEFAULT_FISH_SPAWN_TURNS;
        }
    }

    /**
     *  Initialize grid for WatorWorld Game
     * @param xParse XMLParser used for specific (per cell) grid initialization
     * @param tpane GUI TilePane for displaying the cells of the gird
     * @return WaterWorldGame object for main sim
     * @throws IOException when XMLParser can't read from config file
     */
    public WaterWorldGame initializeGrid(XMLParser xParse, TilePane tpane) throws IOException
    {

        int[] gridDimensions = getGridDimensions(xParse);
        int gridX = gridDimensions[0];
        int gridY = gridDimensions[1];

        ArrayList<String> rowStrings = generateRowlist(gridX);
        xParse.setDataFields(rowStrings);
        HashMap<String,String> rowStringMap = xParse.getData(configFile);

        ArrayList<Character> cellCharacters = new ArrayList<>(Arrays.asList(SHARK_CELL_KEY, KELP_CELL_KEY, WATER_CELL_KEY, FISH_CELL_KEY));
        ArrayList<Character>[] cellTypes = initializeCellTypes(gridX, gridY, cellCharacters, rowStringMap, xParse);

        MovingNode[][] grid = initializeCellArray(gridX, gridY, tpane, cellTypes);
        Grid lifeCreate = new SquareGrid(grid, gridX, gridY, false); //operate on grid of nodes
        initializeGUIArray(gridX, gridY, lifeCreate, tpane, grid);
        configureTilePane(tpane, gridY);

        ArrayList<Node> nodeList = new ArrayList<>();
        HashMap<Node, ArrayList<Node>> nodeAdjacentMaps = new HashMap<>();
        initializeGameNodeLists(gridX, gridY, grid, lifeCreate, nodeList, nodeAdjacentMaps);
        giveNodesTotalMap(nodeList, nodeAdjacentMaps);

        return new WaterWorldGame(nodeAdjacentMaps, nodeList);

    }

    /**
     * Initialize the cell array for a WaTorWorld game
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

                if (rowCellType.get(j) == WATER_CELL_KEY)
                {
                    nodeGrid[i][j] = new MovingNode(new WaterCell(new Rectangle()));
                }
                else if (rowCellType.get(j) == KELP_CELL_KEY)
                {
                    nodeGrid[i][j] = new MovingNode(new KelpCell(new Rectangle()));
                }
                else if (rowCellType.get(j) == SHARK_CELL_KEY)
                {
                    nodeGrid[i][j] = new MovingNode(new SharkCell(new Rectangle(), INITIAL_TURNS_ALIVE, this.sharkSpawnTurns));
                }
                else if (rowCellType.get(j) == FISH_CELL_KEY)
                {
                    nodeGrid[i][j] = new MovingNode(new FishCell(new Rectangle(), INITIAL_TURNS_ALIVE , this.fishSpawnTurns));
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
                nodeAdjacentMaps.put(grid[i][j], lifeCreate.getAdjacentNodesVonNeumannNeighborhood(i,j));
            }
        }
    }

}

