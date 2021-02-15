package Backend.Commands.Display;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Michael Glushakov
 *
 */
public class CommandSetBackground extends Command {
    public static final String key="SETBG";

public CommandSetBackground(VariableTracker tracker){
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        int index = (int)parseParameter(params);
        return String.valueOf(myTracker.getTurtleManager().changeBG(index));
    }
}
