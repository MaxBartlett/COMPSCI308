package Backend.Commands.Math;

import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;
/**
 * @author Michael Glushakov
 * @author Christopher Lin
 * @author Max Bartlett
 */
public class CommandEqualP extends MultiInputCommand {
    private static final String key = "Equal";

    public CommandEqualP(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        try {
            parseAllParameters(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        return myVals.stream().distinct().count() > 1 ? "0" : "1";
    }
}
