package Backend.Commands.Turtle;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;


/**
 * @author Christopher Lin cl349
 */

public class CommandBK extends Command {
    public static final String key = "Backward";
    private double amountBack;

    public CommandBK(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        try {
            amountBack = parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        return String.valueOf(myTracker.getTurtleManager().back(amountBack));
    }
}
