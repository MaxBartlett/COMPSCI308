package Backend.Commands.Display;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 */
public class CommandPenColor extends Command {
    public final String key = "SetShape";
    public CommandPenColor(VariableTracker tracker) {
        super(tracker);
    }

    @Override
    public String execute(List<String> params) {
        return null;
    }
}
