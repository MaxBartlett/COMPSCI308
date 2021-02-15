package Backend.Commands.Turtle;

import Backend.Command;
import Backend.VariableTracker;
import javafx.scene.Node;

import java.util.List;

/**
 * @author Max Bartlett (mmb70)
 */

public class CommandClearStamps extends Command {
	public static final String key = "ClearStamps";

	public CommandClearStamps(VariableTracker tracker) {
		super(tracker);
		setKey(key);
	}

	@Override
	public String execute(List<String> params) {
		return String.valueOf(myTracker.getTurtleManager().getActiveTurtles().get(0).getController().removeStamps());
	}
}