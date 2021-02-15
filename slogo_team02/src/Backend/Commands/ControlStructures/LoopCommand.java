/**
 * @author Michael Glushakov (mg367)
 */
package Backend.Commands.ControlStructures;

import Backend.Command;
import Backend.CommandManager;
import Backend.Exceptions.InvalidInputException;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Glushakov
 * @apiNote Purpose: Abstract class that contains logic for executing loop logic
 * @apiNote Assumptions: start, end, key and increment are set by the subclasses before super.execute() is called
 * @apiNote Command's parse parameters method
 * @apiNote Usage: super.execute() called by loop commands
 */
public abstract class LoopCommand extends Command {
    protected static final String noBracketsErrorMsg = "Limit must be enclosed by brackets []";
    protected static final String wrongNumOfArgsErrorMsg = "Wrong number of arguments given";
    protected final String END_DELIMETER = "]";
    protected final String START_DELIMETER = "[";
    protected int start, end, increment;
    protected String key;
    public LoopCommand(VariableTracker tracker) {
        super(tracker);
    }

    /**
     *
     * @param params array list of strings corresponding to user input following the command that's being parsed
     * @return the return value of the last command executed
     */
    @Override
    public String execute(List<String> params) {
        List<String> tempList = new ArrayList<>();
        double out = 0;

        for (int i = start; i <= end; i += increment) {
            super.myTracker.put(key, (double) i);
            tempList = new ArrayList<>(params);
            if (!tempList.get(0).equals(START_DELIMETER)) {
                throw new InvalidSyntaxException(key);
            }
            tempList.remove(START_DELIMETER);
            while (tempList.size() > 0) {
                if (tempList.get(0).equals(END_DELIMETER)) {
                    tempList.remove(0);
                    break;
                }
                if (!CommandManager.isCommand(tempList.get(0), myTracker) && tempList.get(0).charAt(0) != ':') {
                    throw new InvalidInputException(tempList.get(0));
                }
                Command loopCmd = CommandManager.getCommand(tempList, myTracker);
                tempList.remove(0);
                out = Double.parseDouble(loopCmd.execute(tempList));
            }
        }
        params.clear();
        for (String s : tempList) {
            params.add(s);
        }
        return String.valueOf(out);
    }
}
