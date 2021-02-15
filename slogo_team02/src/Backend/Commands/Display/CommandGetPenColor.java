package Backend.Commands.Display;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Micheal Glushakov
 */
public class CommandGetPenColor extends Command {
    public final static String key = "GetPenColor";
    public CommandGetPenColor(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        return String.valueOf(myTracker.getTurtleManager().getPenColor());
    }
}
