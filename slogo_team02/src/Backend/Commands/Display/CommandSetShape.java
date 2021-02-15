package Backend.Commands.Display;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 */
public class CommandSetShape extends Command {
    private static final String key = "SetShape";
    public CommandSetShape(VariableTracker tracker) {
        super(tracker);
    }

    @Override
    public String execute(List<String> params) {
        double index = parseParameter(params);
        myTracker.getTurtleManager().setShape(index);
        return String.valueOf(index);
    }
}
