/**
 * @author Max Bartlett
 *
 * Tracks variables and commands
 */

package Backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class VariableTracker {
    private ObservableMap<String, Object> varMap;
    private ObservableMap<String, List<String>> commandMap;
    private TurtleManager turtleManager;

    /**
     * Default constructor initializes Turtle() and HashMap()
     */
    public VariableTracker() {
        varMap = FXCollections.observableMap(new HashMap<>());
        commandMap = FXCollections.observableMap(new HashMap<>());
        turtleManager = new TurtleManager();
    }

    public TurtleManager getTurtleManager() {
        return turtleManager;
    }

    /**
     * Returns active turtle, creates a new one if it does not exist
     * @return turtle
     */

    /**
     * puts <Key, Value> in varMap
     *
     * @param key   variable name
     * @param value variable value
     * @return value if key already exists in varMap, otherwise null
     */
    public Object put(String key, Object value) {
        return varMap.put(key, value);
    }

    /**
     * gets value associated with key from varMap
     *
     * @param key key to be accessed
     * @return value if key exists in varMap, otherwise null
     */
    public Object get(String key) {
        return varMap.get(key);
    }

    /**
     * removes key from varMap
     *
     * @param key key to be removed
     * @return previous value if key exists in varMap, otherwise null
     */
    public Object remove(String key) {
        return varMap.remove(key);
    }

    /**
     * @return set containing all var names
     */
    public Set<String> keySet() {
        return varMap.keySet();
    }

    /**
     * clears varMap
     */
    public void clearVars() {
        varMap.clear();
    }

    /**
     * puts command in commandMap
     * @param key command
     * @param commandSequence sequence of commands
     */
    public void putCommand(String key, List<String> commandSequence) {
        commandMap.put(key, commandSequence);
    }

    /**
     * gets command from commandMap
     * @param key key of command
     * @return command
     */
    public List<String> getCommand(String key) {
        return commandMap.get(key);
    }

    /**
     * clears commandMap
     */
    public void clearCommands() {
        commandMap.clear();
    }

    /**
     * @return commandMap
     */
    public ObservableMap<String, List<String>> getCommandMap() {
        return commandMap;
    }

    /**
     * @return varMap
     */
    public ObservableMap<String, Object> getVarMap() {
        return varMap;
    }
}
