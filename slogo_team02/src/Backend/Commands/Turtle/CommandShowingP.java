package Backend.Commands.Turtle;

import Backend.Command;
import Backend.VariableTracker;

import java.util.List;

public class CommandShowingP extends Command {
    private static final String key= "IsShowing";

    public CommandShowingP(VariableTracker tracker){
        super(tracker);
    }

    @Override
    public String execute(List<String> params) {
        return myTracker.getTurtleManager().getVisibility() ? "1" : "0";
    }
}
