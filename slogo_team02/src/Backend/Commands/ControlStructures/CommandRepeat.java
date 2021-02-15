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
public class CommandRepeat extends LoopCommand {
    private static final String mykey = "Repeat";

    public CommandRepeat(VariableTracker tracker) {
        super(tracker);
        setKey(mykey);
    }

    @Override
    public String execute(List<String> params) {
        start = 1;
        try {
            end = (int) parseParameter(params);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        increment = 1;
        key = "repCount";
        if (!params.get(0).equals("[") || params.indexOf("]") == -1) {
            throw new InvalidSyntaxException(mykey);
        }
        return super.execute(params);
    }
}
