package initialize;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import game.Game;
import java.io.File;
import grids.Grid;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Shape;
import nodes.*;
import xml.XMLParser;


/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This abstract class facilitates extension by any specific Cell Society simulation to initialize it
 *  Assumptions: There is an XML parser and config file, along with a "Game" hierarchy and "Handle" hierarchy
 *  Dependencies: Arrays, ArrayList, HashMap, Random, game.Game, grids.Grid, File, TilePane, Shape, xml.XMLParser
 *  Class is abstract - don't instantiate
 */
public abstract class Handle{

    //Stores the sim's config parameter names so the XML parser can grab them
    protected ArrayList<String> configParameters;
    protected HashMap<Character,String> cellReadMap;
    protected int numCellTypes;
    protected File configFile = new File("xml/FireConfig.xml"); //if no configFile is selected, this will be the default
    protected ArrayList<String> gridSizeFind;
    protected ArrayList<Double> DEFAULT_WEIGHTED_PROBABILITIES;

    //Constants needed for simulations
    public static final double WINDOW_WIDTH = 800.0;
    public static final int TILE_GAP = 0;
    public static final int DEFAULT_X = 10;
    public static final int DEFAULT_Y = 10;

    //grid dimension finding constants
    public static final String X_TAG = "xdim";
    public static final String Y_TAG = "ydim";
    public static final String ROW_IDENTIFIER = "row";

    //grid initialization type constants
    public static final String GRID_INITIALIZE_TYPE_TAG = "initializeType";
    public static final String DEFAULT_INITIALIZE_TYPE_TAG = "random";
    public static final ArrayList<String> initializeTypeTags = new ArrayList<>(Arrays.asList("random","specific","weightedrandom"));
    public static final int PROBABILITY_GRANULARITY = 100;

    /**
     * Constructor for a Handle object
     * @param configParameters XML tags used for configuration parameters of a specific simuation
     * @param cellReadMap Map of character tags to a cell type for a simulation
     */
    public Handle(ArrayList<String> configParameters, HashMap<Character,String> cellReadMap)
    {
        gridSizeFind = new ArrayList<>();
        gridSizeFind.add(X_TAG);
        gridSizeFind.add(Y_TAG);

        this.configParameters = configParameters;
        this.cellReadMap = cellReadMap;
    }

    /**
     * Read the values of configuration parameters for a simulation
     * @param xParse XMLParser to read config parameters
     */
    public abstract void getConfigurationParameters(XMLParser xParse);

    /**
     * Set the configuration file for an instance of a simulation
     * @param configFile the file being set as the config file
     */
    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    /**
     * Initialize the grid for a particular simulation
     * @param xParse XMLParser used for specific (per cell) grid initialization
     * @param tpane GUI TilePane for displaying the cells of the gird
     * @return initialized Game object that is passed back to the initializer to return to the Main simulation
     * @throws IOException from XMLParser when it cannot read data correctly from XML file
     */
    public abstract Game initializeGrid(XMLParser xParse, TilePane tpane) throws IOException;

    /**
     * Create the systematic XML tags used in specific grid initialization
     * @param rows Number of rows that the grid for a simulation has
     * @return ArrayList of strings "row1","row2","row3",...,"rowN"
     * (These are the XML tags for a row in the grid)
     */
    public ArrayList<String> generateRowlist(int rows)
    {
        ArrayList<String> rowlist = new ArrayList<>();
        for (int i = 1; i <= rows; i++)
        {
            rowlist.add("row" + i);
        }
        return rowlist;
    }

    /**
     * Parse what type of grid initialization (specific, random, weightedrandom) is desired for this simulation instance
     * @param xParse XMLParser to read from confi file
     * @return String representing what type of initialization to perform on the grid
     * @throws IOException from XMLParser when it cannot read data correctly from XML file
     */
    public String getInitializeType(XMLParser xParse) throws IOException
    {
        ArrayList<String> gridTypeField = new ArrayList<>(Arrays.asList(GRID_INITIALIZE_TYPE_TAG));
        xParse.setDataFields(gridTypeField);
        HashMap<String,String> initType = xParse.getData(configFile);
        return initType.get(GRID_INITIALIZE_TYPE_TAG).replaceAll("\\s+","");
    }

    /**
     * Validate simulation type returned from config file. If nonexistent set to default
     * @param initializeType simulation type from config file
     * @return error-checked grid initialization type
     */
    public String checkInitializeType(String initializeType)
    {
        if (initializeTypeTags.contains(initializeType)) {return initializeType;}
            initializeType = DEFAULT_INITIALIZE_TYPE_TAG;
            return initializeType;
    }

    /**
     * Parse probabilities of generating different cells for a simulation if it is being
     * initialized with "weightedrandom" option
     * @param xParse XMLParser to read these different probabilities
     * @param cellReadList List of cell character identifiers for a simulation
     * @return ArrayList of characters of the cell names, with relative frequencies equal to the probabilities
     * specified in the config file (This method is way too long!)
     */
    public ArrayList<Character> getWeightedRandomProbabilities(XMLParser xParse, ArrayList<Character> cellReadList)
    {
        ArrayList<Character> buildWeightedProbabilities = new ArrayList<>();

        ArrayList<String> convertCellChars = new ArrayList<>();
        for (Character c : cellReadList)
        {
            convertCellChars.add(c.toString());
        }
        xParse.setDataFields(convertCellChars);

        HashMap<String, String> cellData = null;
        try {
            cellData = xParse.getData(this.configFile);
        }
        catch (IOException e)
        {
            System.err.println("IOException reading weighted random values from config file");
        }

        double[] cellInitializeProbabilities = new double[this.numCellTypes];
        double sum = 0;
        try {
            for (int i = 0; i < numCellTypes; i++) {
                cellInitializeProbabilities[i] = Double.parseDouble(cellData.get(convertCellChars.get(i)));
                sum += cellInitializeProbabilities[i];
            }
            if (sum != 1.0)
            {
                System.err.println("Probabilities do not add up to one, setting to default weights");
                //cellInitializeProbabilities = this.DEFAULT_WEIGHTED_PROBABILITIES;
            }
        }
        catch (NumberFormatException f)
        {
            System.err.println("Did not parse weighted probabilities correctly, setting to default weights");
            //cellInitializeProbabilities = this.DEFAULT_WEIGHTED_PROBABILITIES;
        }

        for (int j = 0; j < numCellTypes; j++)
        {
            int upperBound = (int)(Math.ceil(PROBABILITY_GRANULARITY * (cellInitializeProbabilities[j])));
            for (int k = 0; k < upperBound; k++)
            {
                buildWeightedProbabilities.add(cellReadList.get(j));
            }
        }

        return buildWeightedProbabilities;

    }

    /**
     * Create per cell character indicators given a String representing a row of cells for the simulation grid
     * @param cellCharacters Valid cell characters for cells in a simulation
     * @param rowString String representing a row of cells for the simulation grid
     * @param gridY Number of columns in the relevant grid
     * @param rowNum Row number being parsed
     * @param initializeType Type of grid initialization that will be performed (specific, random , weighted random)
     * @param xParse XMLParser to pass when there is weighted random initialization
     * @return
     */
    public ArrayList<Character> generateCellIndicators(ArrayList<Character> cellCharacters, String rowString, int gridY, int rowNum, String initializeType, XMLParser xParse)
    {
        ArrayList<Character> rowCells = new ArrayList<>();

        if (initializeType.equals("specific")) {
            for (int i = 0; i < rowString.length(); i++) {
                if (cellCharacters.contains(rowString.charAt(i))) {
                    rowCells.add(rowString.charAt(i));
                }
            }
        }

        if (initializeType.equals("weightedrandom"))
        {
            cellCharacters = getWeightedRandomProbabilities(xParse, cellCharacters);
        }

        if (rowCells.size() < gridY)
        {
            if (initializeType.equals("specific")) {
            System.err.println("Row " + rowNum + " has too few cells, padding with random simulation cells to reach correct length."); }
            Random moreCells = new Random();
            while (rowCells.size() != gridY)
            {
                int randIndex = moreCells.nextInt(cellCharacters.size());
                Object newCell = cellCharacters.toArray()[randIndex];
                rowCells.add((Character)newCell);
            }
        }
        else if (rowCells.size() > gridY)
        {
            System.err.println("Row " + rowNum + " has too many cells, removing cells on right of row");
            while (rowCells.size() != gridY)
            {
                rowCells.remove(rowCells.get(rowCells.size() - 1));
            }
        }

        return rowCells;
    }

    /**
     * Configure the TilePane object used to display a simulation graphically
     * @param tpane TilePane object to configure
     * @param numColumns number of columns the grid should have
     */
    public void configureTilePane(TilePane tpane, int numColumns)
    {
        tpane.setHgap(TILE_GAP);
        tpane.setPrefColumns(numColumns);
        tpane.setMaxWidth(WINDOW_WIDTH);
    }

    /**
     * Retrieve grid dimensions from XML configuration file. If not found, initialize to defaults
     * @param xParse XMLParser used to find grid dimensions from config file
     * @return array of integers with two elements representing x dimension and y dimension of grid respectively
     */
    public int[] getGridDimensions(XMLParser xParse)
    {
        xParse.setDataFields(gridSizeFind);
        HashMap<String, String> dimensions = null;
        int gridX, gridY;

        try {
            dimensions = xParse.getData(configFile);
            gridX = Integer.parseInt((dimensions.get(Handle.X_TAG)).replaceAll("\\s+",""));
            gridY = Integer.parseInt((dimensions.get(Handle.Y_TAG)).replaceAll("\\s+",""));
        }
        catch (NumberFormatException ne) {
            System.err.println("Number format exception when parsing dimensions of grid, setting dimensions to defaults");
            gridX = DEFAULT_X;
            gridY = DEFAULT_Y;
        }
        catch (IOException ie)
        {
            System.err.println("IO exception when parsing dimensions of grid, setting dimensions to defaults");
            gridX = DEFAULT_X;
            gridY = DEFAULT_Y;
        }

        int[] gridDimensions = {gridX, gridY};
        return gridDimensions;
    }

    /**
     * Give nodes access to all other nodes with a map created outside of this method
     * @param nodes list of nodes for a simulation
     * @param totalNodeMap Map of all nodes to their neighbors
     */
    public void giveNodesTotalMap(ArrayList<Node> nodes, HashMap<Node, ArrayList<Node>> totalNodeMap)
    {
        for (Node node: nodes)
        {
            node.setMap(totalNodeMap);
        }
    }

    /**
     * Gives AlgoNodes access to all other nodes with a map created outside of this method
     * @param algoNodes list of AlgoNodes for a simulation
     * @param totalNodeMap Map of all nodes to their neighbors
     * (This method is necessary because AlgoNodes have a special constraint not allowing for a list of Nodes that
     *  are all AlgoNodes to be passed to it- it must be specifically a list of AlgoNodes)
     */
    public void giveAlgoNodesTotalMap(ArrayList<AlgoNode> algoNodes, HashMap<Node, ArrayList<Node>> totalNodeMap)
    {
        for (AlgoNode a : algoNodes)
        {
            a.setMap(totalNodeMap);
        }
    }


    /**
     * Facilitate translation of grid rows as strings to character arrays
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param cellCharacters Characters represented in a specific simulation
     * @param rowStringMap Map of strings that represent rows of the grid, parsed from XML parser
     * @param xParse
     * @return
     */
    public ArrayList<Character>[] initializeCellTypes(int gridX, int gridY, ArrayList<Character> cellCharacters, HashMap<String,String> rowStringMap, XMLParser xParse)
    {
        ArrayList<Character>[] cellTypes = (ArrayList<Character>[])new ArrayList[gridX];

        String initializeType = null;
        try {
            initializeType = getInitializeType(xParse);
        }
        catch (IOException e)
        {
            System.err.println("IOException in checking grid initialization type");
        }

        initializeType = checkInitializeType(initializeType);

        for (int i = 1; i <= gridX; i++)
        {
                String rowString = rowStringMap.get(Handle.ROW_IDENTIFIER + i);
                cellTypes[i - 1] = generateCellIndicators(cellCharacters, rowString, gridY, i, initializeType, xParse);
        }

        return cellTypes;
    }

    /**
     * Place graphical representations for cells in the simulation TilePane
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param grid Grid object representing the grid for the simulation
     * @param tpane TilePane object to contain all nodes
     * @param nodes Actual group of initialized nodes
     */
    public void initializeGUIArray(int gridX, int gridY, Grid grid, TilePane tpane, Node[][] nodes)
    {
        for (int i = 0; i < gridX; i++)
        {
            for (int j = 0; j < gridY; j++)
            {
                Shape guiCell = grid.createShape(gridX, gridY, i, j);
                nodes[i][j].getMyCell().replaceMySquare(guiCell);
                tpane.getChildren().add(guiCell);
            }
        }
    }

}
