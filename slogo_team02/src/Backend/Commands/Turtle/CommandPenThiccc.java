package Backend.Commands.Turtle;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Michael Glushakov
 */
public class CommandPenThiccc extends Command {
    private static final String key = "THICCCCCC";

    public CommandPenThiccc(VariableTracker tracker) {
        super(tracker);
    }

    @Override
    public String execute(List<String> params) {
        int index = (int) parseParameter(params);
        return String.valueOf(myTracker.getTurtleManager().penSize(index));
    }
}
