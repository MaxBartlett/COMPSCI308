package Backend.Commands.Math;

import Backend.Command;
import Backend.Exceptions.InvalidInputException;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Glushakov
 * @apiNote Purpose: Abstract class that contains logic for managing any number of inputs
 * @apiNote Assumptions: Command.java's parseParameter method
 * @apiNote Dependencies: CommandManager's isCommand and getCommand methods
 * @apiNote Usage: parseAllParameters() called by it's subclasses
 */
public class MultiInputCommand extends Command {
    protected List<Double> myVals;

    public MultiInputCommand(VariableTracker tracker) {
        super(tracker);
        myVals = new ArrayList<>();
    }

    /**
     *
     * @param params array list of strings corresponding to user input following the command that's being parsed
     * @return the return value of the last command executed
     */
    protected void parseAllParameters(List<String> params) {
        if (params.get(0).equals("(")) {
            if (!params.contains(")")) {
                throw new InvalidInputException();
            }
            params.remove(0);
            int end = params.indexOf(")");
            for (int i = 0; i < end; i += 1) {
                if (params.get(0).equals(")")) {
                    params.remove(0);
                    break;
                }
                myVals.add(parseParameter(params));
            }
            if (params.size() > 0 && params.get(0).equals(")")) {
                params.remove(0);
            }
        } else {
            myVals.add(parseParameter(params));
            myVals.add(parseParameter(params));
        }
    }

    @Override
    public String execute(List<String> params) {
        return null;
    }
}