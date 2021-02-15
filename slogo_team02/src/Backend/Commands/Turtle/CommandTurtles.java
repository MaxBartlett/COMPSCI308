package Backend.Commands.Turtle;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @Christopher Lin
 */

public class CommandTurtles extends Command {
    private static final String key = "Turtles";

    public CommandTurtles(VariableTracker tracker) {

        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        return String.valueOf(myTracker.getTurtleManager().getNumTurtles());
    }
}
