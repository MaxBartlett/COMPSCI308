package Backend;


import Backend.Exceptions.CommandParsingException;
import Backend.Exceptions.InvalidInputException;
import Backend.Exceptions.InvalidVariableCallException;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Michael Glushakov (mg367)
 * @author Christopher Lin
 * @apiNote Purpose: Class that manages the communication between Back-end and Front-end of a single workspace
 * @apiNote Assumptions: The path to the resource bundle folders is constant
 * @apiNote Dependencies: Depends on VariableTracker to store user defined commands and variables, depends on TextParser to
 * parse user input string into an array list. Depends on Command's execute method to return an output
 * @apiNote Usage: CommandManager.execute() used by front-end to parse and execute  the user input
 */
public class CommandManager {
    public static final String ERROR_PATH = "config.Errors";
    public static final String COMMAND_PATH = "config.Commands";
    public static final String SYNTAX_PATH = "config.Syntax";
    private static final Map<String, Command> myCommands = new HashMap<>();
    private TextParser myParser;
    private VariableTracker myTracker;

    /**
     * default constructor
     */
    public CommandManager() {
        myParser = new TextParser();
        preloadCommands();

    }

    /**
     * Constructor with a predefined language
     *
     * @param path path to the resource bundle containing the language specific commands
     */
    public CommandManager(String path) {

        myParser = new TextParser(path);
        myTracker = new VariableTracker();
        preloadCommands();

    }

    /**
     *
     * @return map of all default commands
     */
    public static Map<String, Command> getCommands() {
        return myCommands;
    }

    /**
     *
     * @param tempList: List of strings corresponding to user input
     * @param tracker: variable tracker of the workspace
     * @return: Command object corresponding to command entered
     * @apiNote If the command is a user-defined command it is replaced by the List of strings corresponding to default commands
     * @throws CommandParsingException if a Command object cannot be instantiated
     *
     */
    public static Command getCommand(List<String> tempList, VariableTracker tracker) {
        ResourceBundle commandBundle = ResourceBundle.getBundle(COMMAND_PATH);
        if (tempList.get(0).charAt(0) == ':') {
            List<String> userCommand = tracker.getCommand(tempList.get(0).substring(1));

            String current = tempList.get(0);
            if (userCommand != null) {
                tempList.addAll(0, userCommand);
                tempList.remove(current);
            } else {
                throw new InvalidInputException(current);
            }
        }
        try {
            Class commandStr = Class.forName(commandBundle.getString(tempList.get(0)));
            Command command = (Command) commandStr.getDeclaredConstructor(VariableTracker.class).newInstance(tracker);
            return command;
        } catch (ClassNotFoundException e) {
            throw new CommandParsingException(tempList.get(0));
        } catch (IllegalAccessException e) {
            throw new CommandParsingException(tempList.get(0));
        } catch (InstantiationException e) {
            throw new CommandParsingException(tempList.get(0));
        } catch (NoSuchMethodException e) {
            throw new CommandParsingException(tempList.get(0));
        } catch (InvocationTargetException e) {
            throw new CommandParsingException(tempList.get(0));
        }
    }

    /**
     *
     * @param cmd: command string
     * @param tracker: varible tracker of the workspace
     * @return true if the cmd String corresponds to a Command subclass, false otherwise
     */
    public static boolean isCommand(String cmd, VariableTracker tracker) {
        if (myCommands.containsKey(cmd)) {
            return true;
        }
        if (cmd.charAt(0) == ':') {
            List<String> userCommand = tracker.getCommand(cmd.substring(1));
            String current = cmd;
            if (userCommand != null) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * @param path String value of the path to the language file
     */
    public void setLanguage(String path) {
        myParser.setLanguage(path);
    }

    /**
     *
     * @param userInput the String representation of text entered by the user
     * @return double value returned by the last command executed
     */
    public String execute(String userInput) {
        if (userInput.length() == 0) {
            return "";
        }
        String out = "";
        List<String> masterList = myParser.parse(userInput);
        while (masterList.size() > 0) {

            if (isCommand(masterList.get(0), myTracker)) {
                Command init = getCommand(masterList, myTracker);
                masterList.remove(0);
                out = init.execute(masterList);
            } else {
                out = getUserDefinedCmd(out, masterList);
            }
        }

        return out;
    }

    /**
     *
     * @param out: Should have been taken out
     * @param masterList: List of current user input
     * @return empty string or the double value of a user defined variable
     */
    private String getUserDefinedCmd(String out, List<String> masterList) {
        if (masterList.get(0).charAt(0) == ':') {
            Double val = (Double) myTracker.get(masterList.get(0).substring(1));
            if (val == null) {
                List<String> userCommand = myTracker.getCommand(masterList.get(0).substring(1));
                String commandName = masterList.get(0);
                if (userCommand != null) {
                    masterList.addAll(0, userCommand);
                    masterList.remove(commandName);
                } else {
                    throw new InvalidInputException(masterList.get(0));
                }
            } else {
                out = "" + val;
                masterList.remove(0);
            }
        } else {
            throw new InvalidVariableCallException();
        }
        return out;
    }

    /**
     *
     * @return map of user defined variables
     */
    public Map<String, Object> getUserVariables() {
        return myTracker.getVarMap();
    }
    /**
     *
     * @return map of user defined commands
     */
    public Map<String, List<String>> getUserCommands() {
        return myTracker.getCommandMap();
    }

    /**
     * @apiNote preloads commands, creating a single instance of each. This is done to catch any issues with incorrect command
     * paths defined in resource bundle during program startup so that they would not arise randomly when the user is trying to
     * execute certain commmands
     */
    private void preloadCommands() {
        try {
            ResourceBundle commandBundle = ResourceBundle.getBundle(COMMAND_PATH);
            for (String key : Collections.list(commandBundle.getKeys())) {
                List<String> myList = new ArrayList<>();
                myList.add(key);
                myCommands.put(key, getCommand(myList, myTracker));
            }
        } catch (MissingResourceException e) {
            throw new CommandParsingException("");
        }

    }

    /**
     *
     * @return the variable tracker unique to the workspace
     */
    public VariableTracker getMyTracker() {
        return myTracker;
    }

}
