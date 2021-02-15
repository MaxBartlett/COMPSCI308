/**
 * @author Michael Glushakov (mg367)
 */
package Backend.Commands.ControlStructures;

import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Michael Glushakov
 */
public class CommandFor extends LoopCommand {
    private static final String myKey = "For";

    public CommandFor(VariableTracker tracker) {
        super(tracker);
        setKey(myKey);
    }

    @Override
    public String execute(List<String> params) {
        if (!params.remove(0).equals(START_DELIMETER)) {
            throw new InvalidSyntaxException(myKey);
        }
        if (!params.remove(4).equals(END_DELIMETER)) {
            throw new InvalidSyntaxException(myKey);
        }
        key = params.remove(0);
        try {
            start = (int) parseParameter(params);
            end = (int) parseParameter(params);
            increment = (int) parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(myKey);
        }
        return super.execute(params);
    }
}
