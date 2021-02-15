package Backend.Commands.Math;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;
/**
 * @author Michael Glushakov
 * @author Max Bartlett
 */
public class CommandRemainder extends Command {
    private static final String key = "Remainder";
    private double d1, d2;

    public CommandRemainder(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        try {
            d1 = parseParameter(params);
            d2 = parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        if (d2 == 0) {
            throw new IllegalArgumentException("Cannot divide by 0");
        }
        return String.valueOf(d1 % d2);
    }
}
