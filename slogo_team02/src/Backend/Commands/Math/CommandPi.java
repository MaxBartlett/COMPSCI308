package Backend.Commands.Math;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;
/**
 * @author Michael Glushakov
 * @author Max Bartlett
 */
public class CommandPi extends Command {
    public static final String key = "Pi";

    public CommandPi(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        return String.valueOf(Math.PI);
    }
}
