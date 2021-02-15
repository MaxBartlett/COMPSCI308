package Backend.Commands.Display;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 */
public class CommandSetPalette extends Command {
    public final String key = "setPalette";
    public CommandSetPalette(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        double index;
        try{
            index = parseParameter(params);
            double r = parseParameter(params);
            double g = parseParameter(params);
            double b = parseParameter(params);
            myTracker.getTurtleManager().setPalette(index, r, g, b);
        }
        catch (Exception e){
            throw new InvalidSyntaxException(key);
        }
        return String.valueOf(index);
    }
}
