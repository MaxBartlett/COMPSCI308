package initialize;

import cellular.segregation.EmptyCell;
import cellular.segregation.TypeOneCell;
import cellular.segregation.TypeTwoCell;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import nodes.AlgoNode;
import nodes.Node;
import xml.XMLParser;
import game.SegGame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import grids.*;
import java.io.IOException;

/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class extends the Handle class to implement the Schelling Model of Segregation cellular automaton
 *  Assumptions: There is an XML parser and config file, along with a "Game" hierarchy and "Handle" hierarchy
 *  Dependencies: Arrays, ArrayList, HashMap, Random, game.Game, grids.Grid, File, TilePane, Shape, xml.XMLParser
 *  Example: Handle h = new SegHandle();
 */
public class SegHandle extends Handle {

    //Keys for cells in Schelling's model of segregation
    public static final char TYPE_ONE_CELL_KEY = '1';
    public static final char TYPE_TWO_CELL_KEY = '2';
    public static final char EMPTY_CELL_KEY = 'E';

    private double percentSatisfaction; //probability that a tree next to a burning cell will catch on fire
    public static final String PERCENT_SATISFACTION = "percentSatisfaction";
    public static final double DEFAULT_PERCENT_SATISFACTION = 0.5;

    public static final double DEFAULT_TYPE_ONE_CELL_WEIGHTED_PROB = 0.4;
    public static final double DEFAULT_TYPE_TWO_CELL_WEIGHTED_PROB = 0.4;
    public static final double DEFAULT_EMPTY_CELL_WEIGHTED_PROB = 0.2;

    /**
     * constructor for LifeHandle object, no parameters to facilitate reflection
     */
    public SegHandle(){
        super(new ArrayList<>(), new HashMap<>());
        this.numCellTypes = 3;
        this.DEFAULT_WEIGHTED_PROBABILITIES = new ArrayList<>(Arrays.asList(DEFAULT_TYPE_ONE_CELL_WEIGHTED_PROB, DEFAULT_TYPE_TWO_CELL_WEIGHTED_PROB, DEFAULT_EMPTY_CELL_WEIGHTED_PROB));
    }

    /**
     * Get Schelling Segregation config parameters
     * @param xParse XMLParser to read config parameters
     */
    public void getConfigurationParameters(XMLParser xParse)
    {
        ArrayList<String> segConfig = new ArrayList<String>();
        segConfig.add(PERCENT_SATISFACTION);
        xParse.setDataFields(segConfig);

        HashMap<String, String> segMap = null;
        try {
            segMap = xParse.getData(configFile);
            this.percentSatisfaction = Double.parseDouble(segMap.get(PERCENT_SATISFACTION).replaceAll("\\s+",""));
        }
        catch(IOException e)
        {
            System.err.println("IOException when parsing SegHandle config parameters, setting value to default");
            this.percentSatisfaction = DEFAULT_PERCENT_SATISFACTION;
        }
        catch(NumberFormatException f)
        {
            System.err.println("NumberFormatException when parsing SegHandle config parameter percentSatisfaction, setting value to default");
            this.percentSatisfaction = DEFAULT_PERCENT_SATISFACTION;
        }
    }

    /**
     * Initialize grid for a SegGame
     * @param xParse XMLParser used for specific (per cell) grid initialization
     * @param tpane GUI TilePane for displaying the cells of the gird
     * @return SegGame object for main simulation to happen
     * @throws IOException when XMLParser can't read from file correctly
     */
    public SegGame initializeGrid(XMLParser xParse, TilePane tpane) throws IOException
    {

        int[] gridDimensions = getGridDimensions(xParse);
        int gridX = gridDimensions[0];
        int gridY = gridDimensions[1];

        ArrayList<String> rowStrings = generateRowlist(gridX);

        xParse.setDataFields(rowStrings);
        HashMap<String,String> rowStringMap = xParse.getData(configFile);

        ArrayList<Character> cellCharacters = new ArrayList<>(Arrays.asList(TYPE_ONE_CELL_KEY, TYPE_TWO_CELL_KEY, EMPTY_CELL_KEY));
        ArrayList<Character>[] cellTypes = initializeCellTypes(gridX, gridY, cellCharacters, rowStringMap, xParse);

        AlgoNode[][] grid = initializeCellArray(gridX, gridY, tpane, cellTypes);
        Grid lifeCreate = new HexGrid(grid, gridX, gridY, false); //operate on grid of nodes
        initializeGUIArray(gridX, gridY, lifeCreate, tpane, grid);
        configureTilePane(tpane, gridY);

        ArrayList<AlgoNode> nodeList = new ArrayList<>();
        HashMap<Node, ArrayList<Node>> nodeAdjacentMaps = new HashMap<>();
        initializeGameNodeLists(gridX, gridY, grid, lifeCreate, nodeList, nodeAdjacentMaps);
        giveAlgoNodesTotalMap(nodeList, nodeAdjacentMaps);

        return new SegGame(nodeAdjacentMaps, nodeList);
    }

    /**
     * Initialize the cell array for a Schelling Model of Segreagation
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param tpane TilePane object for graphical objects on grid
     * @param cellTypes Characters representing cell types for the simulation
     * @return 2d array of nodes initialized to the correct cell types
     */
    public AlgoNode[][] initializeCellArray(int gridX, int gridY, TilePane tpane, ArrayList<Character>[] cellTypes)
    {

        AlgoNode[][] nodeGrid = new AlgoNode[gridX][gridY];

        for (int i = 0; i < gridX; i++) //cell initialize in the grid
        {
            ArrayList<Character> rowCellType = cellTypes[i];
            for (int j = 0; j < gridY; j++)
            {

                if (rowCellType.get(j) == TYPE_ONE_CELL_KEY)
                {
                    nodeGrid[i][j] = new AlgoNode(new TypeOneCell(new Rectangle(), this.percentSatisfaction));

                }
                else if (rowCellType.get(j) == TYPE_TWO_CELL_KEY)
                {
                    nodeGrid[i][j] = new AlgoNode(new TypeTwoCell(new Rectangle(), this.percentSatisfaction));
                }
                else if (rowCellType.get(j) == EMPTY_CELL_KEY)
                {
                    nodeGrid[i][j] = new AlgoNode(new EmptyCell(new Rectangle()));
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
    public void initializeGameNodeLists(int gridX, int gridY, AlgoNode[][] grid, Grid lifeCreate, ArrayList<AlgoNode> nodeList, HashMap<Node, ArrayList<Node>> nodeAdjacentMaps)
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

