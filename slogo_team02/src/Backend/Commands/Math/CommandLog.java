package Backend.Commands.Math;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;
/**
 * @author Michael Glushakov
 * @author Max Bartlett
 */
public class CommandLog extends Command {
    private static String key = "NaturalLog";
    private double d1;

    public CommandLog(VariableTracker tracker) {
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
        if (d1 < 0) {
            throw new IllegalArgumentException("Log input must be positive");
        }
        return String.valueOf(Math.log(d1));
    }
}
