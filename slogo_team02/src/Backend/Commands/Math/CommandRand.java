/**
 * @author Michael Glushakov (mg367), Max Bartlett (mmb70)
 */
package Backend.Commands.Math;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;
/**
 * @author Michael Glushakov
 * @author Max Bartlett
 */

public class CommandRand extends Command {
    private static final String key = "Random";
    private int upperBound;

    public CommandRand(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        try {
            upperBound = (int) parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }

        if (upperBound <= 0) {
            throw new IllegalArgumentException("Random ceiling must be positive");
        }
        return String.valueOf((int) (Math.random() * upperBound));
    }
}
