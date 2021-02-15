package Backend;

import Backend.Exceptions.InvalidInputException;
import Backend.Exceptions.ParameterAmountException;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Michael Glushakov
 * @author Christopher Lin
 * @apiNote Purpose: Abstract class that contains some of the key logic needed by all commands
 * @apiNote Assumptions: CommandManager.getCommand is a public static method
 * @apiNote Dependencies: CommandManager's isCommand and getCommand methods
 * @apiNote Usage: Command.execute() should take care of parsing and executing command

 */
public abstract class Command {

    public static final String DESCRIPTION_PATH = "Config.CommandDescriptions";
    protected VariableTracker myTracker;
    private String myKey = "Default";

    public Command(VariableTracker tracker) {
        myTracker = tracker;
    }

    /**
     *
     * @param key key value of the command in a resource bundle. Called by all subclasses in constructor
     */
    protected void setKey(String key) {
        myKey = key;
    }
    /**
     * @return description of what the command does to the user
     */
    public String getDescription() {
        return ResourceBundle.getBundle(DESCRIPTION_PATH).getString(myKey);
    }

    /**
     * @param  params: array list of strings corresponding to user input following the command that's being parsed
     * @throws IllegalArgumentException
     * @apiNote parses a single parameter needed for command to execute. If a parameter is an putput of another command,
     * recursively calls that command
     * @return the double value of the parameter
     */
    public double parseParameter(List<String> params) throws IllegalArgumentException {
        double param;
        if (params.size() == 0) {
            throw new ParameterAmountException();
        }
        if (CommandManager.isCommand(params.get(0), myTracker)) {

            Command nextCmd = CommandManager.getCommand(params, myTracker);
            params.remove(0);
            param = Double.parseDouble(nextCmd.execute(params));
        } else {
            //handle user-defined variables
            if (params.get(0).charAt(0) == ':') {
                return handleCustomInput(params);
            }
            try {
                param = Double.parseDouble(params.get(0));
            } catch (NumberFormatException e) {
                throw new InvalidInputException(params.get(0));

            }
            params.remove(0);
        }
        return param;
    }

    /**
     *
     * @param params array list of strings corresponding to user input following the command that's being parsed
     * @apiNote helper method that handles parsing of the user defined variables and commands
     * @return value of the parameter
     */
    private double handleCustomInput(List<String> params) {
        double param;
        Double temp = (Double) myTracker.get(params.get(0).substring(1));
        if (temp == null) {
            List<String> userCommand = myTracker.getCommand(params.get(0).substring(1));
            String commandName = params.get(0);
            if (userCommand != null) {
                params.addAll(0, userCommand);
                params.remove(commandName);
                return parseParameter(params);

            } else {
                throw new InvalidInputException(params.get(0));
            }
        } else {
            params.remove(0);
            param = temp;
        }
        return param;
    }

    /**
     *
     * @param params array list of strings corresponding to user input following the command that's being parsed
     * @return the return value of a command
     */
    public abstract String execute(List<String> params);


}
