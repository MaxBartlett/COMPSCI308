package Backend.Commands.Turtle;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 */
public class CommandTowards extends Command {
    private static final String key = "SetTowards";
    int pointX;
    int pointY;

    public CommandTowards(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    public String execute(List<String> params) {
        try {
            pointX = (int) parseParameter(params);
            pointY = (int) parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        var turtleMan = myTracker.getTurtleManager();
        return String.valueOf(turtleMan.towards(pointX, pointY));

    }
}
