package Backend.Commands.Display;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

/**
 * @author Christopher Lin
 */
public class CommandShape extends Command {

    CommandShape(VariableTracker tracker){
        super(tracker);
    }

    @Override
    public String execute(List<String> params) {
        return null;
    }
}
