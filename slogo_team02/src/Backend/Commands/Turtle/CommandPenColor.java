package Backend.Commands.Turtle;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 */
public class CommandPenColor extends Command {
    private static final String key = "SETPENCOLOR";

    public CommandPenColor(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        int color = (int) parseParameter(params);
        return String.valueOf(myTracker.getTurtleManager().penColor(color));
    }
}
