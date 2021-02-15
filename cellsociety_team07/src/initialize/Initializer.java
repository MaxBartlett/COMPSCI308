package initialize;

import java.util.ArrayList;
import xml.XMLParser;
import java.io.File;
import java.io.IOException;
import javafx.scene.layout.TilePane;
import javafx.scene.Group; //was to be imported to replace Tile Pane with Group, didn't have enough time to implement
import org.w3c.dom.Element;
import game.*;


 /** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class implements initialization of the correct simulation type for Cell Society using reflection
  *  with robust error checking
 *  Assumptions: There is an XML parser and config file, along with a "Game" hierarchy and "Handle" hierarchy
 *  Dependencies: xml.XMLParser, File, IOException, TilePane or Group, Element, all game.Game classes
 *  Example: Initializer a = new Initializer(tilePaneGUI, xmlParser, configFile);
 *
 */
public class Initializer
{

    public static final String DEFAULT_SIMTYPE = "Life"; //game of life is default sim

    private File configFile;
    private TilePane cellGUI;
    private XMLParser xmlParse;

    /**
     * Constructor for Initializer object
     * @param cellGUI TilePane JavaFX object for adding Grid nodes
     * @param xmlParse XMLParser to read from config file
     * @param configFile Name of config file
     */
    public Initializer(TilePane cellGUI, XMLParser xmlParse, File configFile) {
        this.cellGUI = cellGUI;
        this.xmlParse = xmlParse;
        this.configFile = configFile;
    }

    /**
     * Getter method for the config file
     * @return configFile as File object
     */
    public File getConfigFile() {
        return configFile;
    }

    /**
     * Retrieves the simulation type as a string using an XML parser
     * @return A string that contains the simulation type in the relevant configuration file
     */
    public String getSimType()
    {
        ArrayList<String> checkXML = new ArrayList<>();
        checkXML.add("simulation");
        Element xmlRoot;
        try {
            xmlParse = new XMLParser(checkXML);
            xmlRoot = xmlParse.getRootElement(configFile);
        }
        catch (IOException e){ //Handles when config file has simulation tag
            System.err.println("No simulation tag in xml file. Initializing to default sim with random cell generation.");
            return DEFAULT_SIMTYPE;
        }

        String simType;
        if (xmlParse.isValidFile(xmlRoot))
        {
            simType = xmlParse.getAttribute(xmlRoot, "simType");
        }
        else //Handle when the string doesn't represent a valid simuation tag
        {
            System.err.println(configFile + " does not have a correct simulation type, intitializing default sim with random cell generation");
            return DEFAULT_SIMTYPE;
        }

        return simType;
    }

    /**
     * Instantiates the correct simulation Handle class (one per simulation)
     * @param simType String representing simulation type
     * @return Handle object for relevant simulation
     */
    public Handle getHandle(String simType)
    {
        Object gameHandle = null;
        //Code based on https://stackoverflow.com/questions/9886266/is-there-a-way-to-instantiate-a-class-by-name-in-java
        try {
            Class<?> handle = Class.forName("initialize." + simType + "Handle");
            gameHandle = handle.newInstance();
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("Class not found exception in creating instance of Handle. Sim type is probably incorrect" +
                    " in config file, generating default sim.");
        }
        catch (IllegalAccessException e1)
        {
            System.err.println("Illegal access exception in creating instance of Handle");
        }
        catch (InstantiationException e2)
        {
            System.err.println("Instantiation exception creating instance of Handle");
        }
        return (Handle)gameHandle;
    }

    /**
     * Initializes the simulation Handle class found from getHandle() (config parameters, grid)
     * @param gameHandle Uninitialized Handle object for specified simulation
     * @return Initialized Handle object for specified simulation
     * @throws IOException from XMLParser when it cannot correct read data from config file
     */
    public Game handleInitialize(Handle gameHandle) throws IOException
    {
        gameHandle.getConfigurationParameters(xmlParse);
        Game game = gameHandle.initializeGrid(xmlParse, cellGUI);
        return game;
    }
}
