package Backend.Commands.Math;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;
/**
 * @author Michael Glushakov
 * @author Max Bartlett
 */
public class CommandATan extends Command {
    private static final String key = "ArcTangent";
    private double d1;

    public CommandATan(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        try {
            d1 = parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        return String.valueOf(Math.toDegrees(Math.atan(d1)));
    }
}
