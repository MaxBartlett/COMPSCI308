package Backend.Commands.Turtle;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 */

public class CommandPenDownP extends Command {

    private static final String key = "IsPenDown";

    public CommandPenDownP(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        return myTracker.getTurtleManager().getPenDown() ? "1" : "0";
    }
}
