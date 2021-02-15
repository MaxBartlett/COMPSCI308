package Backend;

//import Backend.Commands.ControlStructure;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Michael Glushakov (mg367)
 * @apiNote Purpose: Helper class used by CommandManager to parse initial user input
 * @apiNote Assumptions: None
 * @apiNote Dependencies: None
 * @apiNote Usage: parse(String input) parses the string into an arraylist
 */

public class TextParser {
    public static final String WHITESPACE = "\\s+";
    private List<Map.Entry<String, Pattern>> mySymbols;

    /**
     * @apiNote Default constructor
     */
    public TextParser() {
        mySymbols = new ArrayList<>();
    }

    /**
     * Constructor with path of the initial language file
     *
     * @param path to the initial language file
     */
    public TextParser(String path) {
        mySymbols = new ArrayList<>();
        setLanguage(path);
    }

    /**
     * @param path: url path to the properties file location
     * @author Robert C. Duvall
     * @apiNote sets the language o be recognized by the parser
     */
    protected void setLanguage(String path) {
        mySymbols.clear();
        ResourceBundle languageBundle = ResourceBundle.getBundle(path);
        for (var key : Collections.list(languageBundle.getKeys())) {
            var regex = languageBundle.getString(key);
            mySymbols.add(new AbstractMap.SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
    }

    /**
     * @param userInput
     * @return List<String> of user input parsed by whitespace
     * @throws IllegalArgumentException
     */
    public List<String> parse(String userInput) throws IllegalArgumentException {
        String[] input = userInput.split(WHITESPACE);
        List<String> out = new ArrayList<>();
        if (input[0].equals("[")) {
            throw new IllegalArgumentException("Brackets can be used to group commands only when preceded by a Control Structures");
        }
        for (int i = 0; i < input.length; i += 1) {
            try {
                String symbol = getSymbol(input[i]);
                out.add(symbol);

            } catch (IllegalArgumentException e) {
                    out.add(input[i]);
            }
        }

        return out;
    }

    /**
     * @param text
     * @return the String matching the key in the Backend.Command Map
     * @throws IllegalArgumentException
     * @author Robert C. Duvall
     * @author Michael Glushakov (mg367)
     */
    private String getSymbol(String text) throws IllegalArgumentException {
        final String ERROR = "INVALID EXPRESSION: ";
        for (var e : mySymbols) {
            if (e.getValue().matcher(text).matches()) {
                return e.getKey();
            }
        }
        throw new IllegalArgumentException(ERROR + text);
    }
}