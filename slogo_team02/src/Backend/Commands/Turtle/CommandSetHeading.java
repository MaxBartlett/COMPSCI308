package Backend.Commands.Turtle;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 */

public class CommandSetHeading extends Command {
    private static final String key = "SetHeading";
    private double heading;

    public CommandSetHeading(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        try {
            heading = parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        var turtleMan = myTracker.getTurtleManager();
        return String.valueOf(turtleMan.setHeading(heading));

    }
}
