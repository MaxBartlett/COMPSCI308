package xml;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class handles parsing XML files and returning a completed object.
 * (FOR CELLULAR AUTOMATA SIMULATIONS USING JAVAFX - R.P.)
 *
 * @author Rhondu Smithwick
 * @author Robert C. Duvall
 * @author Ryan Piersma
 */
public class XMLParser {
    // Readable error message that can be displayed by the GUI
    public static final String ERROR_MESSAGE = "XML file does not represent %s";

    // keep only one documentBuilder because it is expensive to make and can reset it before parsing
    private final DocumentBuilder DOCUMENT_BUILDER;

    // Instance variable of the different data fields the XML file should have for parsing
    private ArrayList<String> DATA_FIELDS;


    public static final String[] DATA_TYPE = {"Life","Seg","Fire","Wator", "Sugar"};
    public static final int NUM_SIMS = 5;
    // name of root attribute that notes the type of file expecting to parse
    public static final String TYPE_ATTRIBUTE = "simType";



    /**
     * Create a parser for XML files of given type.
     */
    public XMLParser (ArrayList<String> dataFields) throws IOException {
        DOCUMENT_BUILDER = getDocumentBuilder();
        DATA_FIELDS = dataFields;
    }

    public void setDataFields(ArrayList<String> newDataFields)
    {
        DATA_FIELDS = newDataFields;
    }

    /**
     * Get the data contained in this XML file as an object
     */
    public HashMap<String, String> getData(File dataFile) throws IOException {
        var root = getRootElement(dataFile);
        // if (! isValidFile(root)) {
        //     throw new IOException("Incorrect game type found in CellSocietyConfig.xml");
        // }
        // read data associated with the fields given by the object
        var results = new HashMap<String, String>();
        for (var field : DATA_FIELDS) {
            results.put(field, getTextValue(root, field));
        }
        //return new Game(results);
        return results;
    }

    // Get root element of an XML file
    public Element getRootElement (File xmlFile) throws IOException {
        try {
            DOCUMENT_BUILDER.reset();
            var xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
            return xmlDocument.getDocumentElement();
        }
        catch (SAXException | IOException e) {
            // throw new XMLException(e);
            System.err.println("Root element not retrieved.");
            return null;
        }
    }

    // Returns if this is a valid XML file for the specified object type
    public boolean isValidFile (Element root) {

        for (int i = 0; i < NUM_SIMS; i++)
        {
           if (getAttribute(root, TYPE_ATTRIBUTE) != null) {
               if (getAttribute(root, TYPE_ATTRIBUTE).equals(DATA_TYPE[i])) {
                   return true;
               }
           }
        }

        return false;
    }

    // Get value of Element's attribute
    public String getAttribute (Element e, String attributeName) {
         if (e != null) {
             return e.getAttribute(attributeName);
         }
         else return null;
    }

    // Get value of Element's text
    private String getTextValue (Element e, String tagName) {
        if (e != null) {
            var nodeList = e.getElementsByTagName(tagName);
            if (nodeList != null && nodeList.getLength() > 0) {
                return nodeList.item(0).getTextContent();
            } else {
                // Note, R.P. -> it is not an error to not find the text value
                return "";
            }
        }

        return "";
    }

    // Boilerplate code needed to make a documentBuilder
    private DocumentBuilder getDocumentBuilder () throws IOException {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            //throw new XMLException(e);
            throw new IOException(e);
        }
    }

}

