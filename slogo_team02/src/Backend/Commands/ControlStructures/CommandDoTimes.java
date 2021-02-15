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

public class CommandDoTimes extends LoopCommand {
    private static final String myKey = "DoTimes";

    /**
     * @param tracker
     */
    public CommandDoTimes(VariableTracker tracker) {
        super(tracker);
        setKey(myKey);
    }

    /**
     * @param params
     * @return
     */
    @Override
    public String execute(List<String> params) {
        if (!params.remove(0).equals(START_DELIMETER)) {
            throw new IllegalArgumentException(noBracketsErrorMsg);
        }
        if (!params.remove(2).equals(END_DELIMETER)) {
            throw new IllegalArgumentException(wrongNumOfArgsErrorMsg);
        }
        key = params.remove(0);
        start = 1;
        try {
            end = (int) parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        increment = 1;
        return super.execute(params);
    }
}
