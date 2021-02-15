package Backend.Commands.Math;

import Backend.VariableTracker;

import java.util.List;
/**
 * @author Michael Glushakov
 * @author Max Bartlett
 */
public class CommandDiff extends MultiInputCommand {
    private static final String key = "Difference";

    public CommandDiff(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        try {
            parseAllParameters(params);
        } catch (Exception e) {
            throw new IllegalArgumentException(key);
        }
        return String.valueOf(myVals.stream().mapToDouble(d -> d).reduce((a, b) -> a - b).getAsDouble());
    }
}
