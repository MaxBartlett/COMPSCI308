package initialize;

import cellular.sugar.AgentCell;
import cellular.sugar.SugarCell;
import game.SugarGame;
import grids.SquareGrid;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import nodes.MovingNode;
import nodes.Node;
import xml.XMLParser;
import grids.*;

import java.io.IOException;
import java.util.*;

/** @author Jonathan Nakagawa
 *  @date 10.5.18
 *  Purpose: This class extends the Handle class to implement the Game of Life cellular automaton
 *  Assumptions: There is an XML parser and config file, along with a "Game" hierarchy and "Handle" hierarchy
 *  Dependencies: Arrays, ArrayList, HashMap, Random, game.Game, grids.Grid, File, TilePane, Shape, xml.XMLParser
 *  Example: Handle h = new LifeHandle();
 */
public class SugarHandle extends Handle {

    //Sugar game cell keys
    public static final char SUGAR_CELL_KEY = 'S';
    public static final char AGENT_CELL_KEY = 'A';

    private double growthRate; //Rate at which sugar regrows
    private double metabolismRate; // Rate at which sugar is consumed
    private double visionRange; // Distance agents and see
    public static final String GROWTH_RATE = "growthRate";
    public static final String METABOLISM_RATE = "metabolismRate";
    public static final String VISION_RANGE = "visionRange";
    public static final double DEFAULT_GROWTH_RATE = 4;
    public static final double DEFAULT_METABOLISM_RATE = 2;
    public static final double DEFAULT_VISION_RANGE = 10;
    public static final int MAX_SUGAR = 10;

    /**
     * Constructor for SugarHandle object
     */
    public SugarHandle(){
        super(new ArrayList<>(), new HashMap<>());
        this.numCellTypes = 2;
    }

    /**
     * Get Sugar game config parameters from XML file
     * @param xParse XMLParser to read config parameters
     */
    public void getConfigurationParameters(XMLParser xParse)
    {
        ArrayList<String> sugarConfig = new ArrayList<String>();
        sugarConfig.add(GROWTH_RATE);
        sugarConfig.add(METABOLISM_RATE);
        sugarConfig.add(VISION_RANGE);
        xParse.setDataFields(sugarConfig);

        HashMap<String, String> sugarMap = null;
        try {
            sugarMap = xParse.getData(configFile);
            this.growthRate = Double.parseDouble(sugarMap.get(GROWTH_RATE).replaceAll("\\s+",""));
            this.metabolismRate = Double.parseDouble(sugarMap.get(METABOLISM_RATE).replaceAll("\\s+",""));
            this.visionRange = Double.parseDouble(sugarMap.get(VISION_RANGE).replaceAll("\\s+",""));
        }
        catch(IOException e)
        {
            System.err.println("IOException when parsing SegHandle config parameters, setting value to default");
            this.growthRate = DEFAULT_GROWTH_RATE;
            this.metabolismRate = DEFAULT_METABOLISM_RATE;
            this.visionRange = DEFAULT_VISION_RANGE;
        }
        catch(NumberFormatException f)
        {
            System.err.println("NumberFormatException when parsing SegHandle config parameter percentSatisfaction, setting value to default");
            this.growthRate = DEFAULT_GROWTH_RATE;
            this.metabolismRate = DEFAULT_METABOLISM_RATE;
            this.visionRange = DEFAULT_VISION_RANGE;
        }
    }

    /**
     * Initialize a SugarGame
     * @param xParse XMLParser used for specific (per cell) grid initialization
     * @param tpane GUI TilePane for displaying the cells of the grid
     * @return a SugarGame for the main sim
     * @throws IOException when XMLParser can't read from config file correctly
     */
    public SugarGame initializeGrid(XMLParser xParse, TilePane tpane) throws IOException
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

        ArrayList<Character> cellCharacters = new ArrayList<Character>(Arrays.asList(SUGAR_CELL_KEY, AGENT_CELL_KEY));
        ArrayList<Character>[] cellTypes = initializeCellTypes(gridX, gridY, cellCharacters, rowStringMap, xParse);

        Node[][] grid = initializeCellArray(gridX, gridY, tpane, cellTypes);
        SquareGrid lifeCreate = new SquareGrid(grid, gridX, gridY, false); //operate on grid of nodes
        initializeGUIArray(gridX, gridY, lifeCreate, tpane, grid);
        configureTilePane(tpane, gridY);

        ArrayList<Node> nodeList = new ArrayList<>();
        HashMap<Node, ArrayList<Node>> nodeAdjacentMaps = new HashMap<>();
        initializeGameNodeLists(gridX, gridY, grid, lifeCreate, nodeList, nodeAdjacentMaps);
        giveNodesTotalMap(nodeList, nodeAdjacentMaps);

        return new SugarGame(nodeAdjacentMaps, nodeList);
    }


    /**
     * Initialize the cell array for a sugar sim
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param tpane TilePane object for graphical objects on grid
     * @param cellTypes Characters representing cell types for the simulation
     * @return 2d array of nodes initialized to the correct cell types
     */
    public Node[][] initializeCellArray(int gridX, int gridY, TilePane tpane, ArrayList<Character>[] cellTypes)
    {

        MovingNode[][] grid = new MovingNode[gridX][gridY];

        for (int i = 0; i < gridX; i++) //cell initialize in the grid
        {
            ArrayList<Character> rowCellType = cellTypes[i];
            for (int j = 0; j < gridY; j++)
            {
                if (rowCellType.get(j) == SUGAR_CELL_KEY)
                {
                    Random rand = new Random();
                    grid[i][j] = new MovingNode(new SugarCell(new Rectangle(), (int) this.growthRate, rand.nextInt(MAX_SUGAR)));

                }
                else if (rowCellType.get(j) == AGENT_CELL_KEY)
                {
                    Random rand = new Random();
                    grid[i][j] = new MovingNode(new AgentCell(new Rectangle(), rand.nextInt(MAX_SUGAR), (int) this.metabolismRate));
                }
            }
        }

        return grid;
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
    public void initializeGameNodeLists(int gridX, int gridY, Node[][] grid, SquareGrid lifeCreate, ArrayList<Node> nodeList, HashMap<Node, ArrayList<Node>> nodeAdjacentMaps)
    {
        for (int i = 0; i < gridX; i++) //build requirements to make a Game object
        {
            for (int j = 0; j < gridY; j++)
            {
                nodeList.add(grid[i][j]);
                nodeAdjacentMaps.put(grid[i][j], lifeCreate.getAdjacentCrossNeighborhood(i,j, (int) this.visionRange));
            }
        }
    }
}
