package Backend.Commands;

import Backend.Command;
import Backend.CommandManager;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: handle commands that take bracketed lists as parameters
 * Assumptions: Subclasses can handle the output of evaluateBrackets
 * Dependencies: Command
 *
 *
 * @author Christopher Lin
 * @author Michael Glushakov
 */

public abstract class BracketedCommand extends Command {
    protected final String END_DELIMETER = "]";
    protected final String START_DELIMETER = "[";

    public BracketedCommand(VariableTracker tracker) {
        super(tracker);
    }

    @Override
    public abstract String execute(List<String> params);


    /**
     *Gets the last close bracket in a series of nested brackets
     *
     * @param str   String to search for end bracket in
     * @return      Index of the end bracket
     */
    protected int getCloseIndex(List<String> str) {
        int startIndex = str.indexOf(START_DELIMETER);
        if (startIndex <= -1 || str.indexOf(END_DELIMETER) < startIndex) {
            throw new IllegalArgumentException("Need [");
        }
        int numOpen = 1;
        int numClosed = 0;
        for (int index = startIndex + 1; index < str.size(); index += 1) {
            if (str.get(index).equals(START_DELIMETER)) {
                numOpen += 1;
            }
            if (str.get(index).equals(END_DELIMETER)) {
                numClosed += 1;
            }
            if (numOpen == numClosed) {
                return index;
            }
        }
        throw new IllegalArgumentException("Brackets don't match");
    }

    /**
     * Evaluates the expression inside brackets (values and commands)
     * @param exp   Expression to evaluate
     * @return      A list of values contained in exp after evaluating
     */
    protected ArrayList<Double> evaluateBrackets(List<String> exp) {
        var retList = new ArrayList<Double>();
        while (exp.size() > 0) {
            if (CommandManager.isCommand(exp.get(0), myTracker)) {
                var cmd = CommandManager.getCommand(exp, myTracker);
                exp.remove(0);
                try {
                    var doubVal = Double.parseDouble(cmd.execute(exp));
                    retList.add(doubVal);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException("Invalid params for bracketed expression");
                }
            } else {
                try {
                    retList.add(Double.parseDouble(exp.get(0)));
                    exp.remove(0);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid params for bracketed expression");
                }
            }
        }
        return retList;
    }
}
