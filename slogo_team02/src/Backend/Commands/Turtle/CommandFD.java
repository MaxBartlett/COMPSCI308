package Backend.Commands.Turtle;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 *
 * Implements the fd command
 */

public class CommandFD extends Command {
    private static final String key = "Forward";
    private double amountFD;

    public CommandFD(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    public String execute(List<String> params) {
        var turtleMan = myTracker.getTurtleManager();
        try {
            amountFD = parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        return String.valueOf(turtleMan.forward(amountFD));
    }
}
