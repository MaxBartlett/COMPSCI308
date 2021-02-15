package Backend.Commands.Turtle;

import Backend.Command;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;
import Backend.Turtle;

import java.util.List;

/**
 * @author Max Bartlett (mmb70)
 */

public class CommandStamp extends Command {
	public static final String key = "Stamp";
	private int id;

	public CommandStamp(VariableTracker tracker) {
		super(tracker);
		setKey(key);
	}

	@Override
	public String execute(List<String> params) {
		try {
			id = (int) parseParameter(params);
		} catch (Exception e) {
			throw new InvalidSyntaxException(key);
		}
		Turtle t = myTracker.getTurtleManager().getTurtleByID(id);
		int index = t.getController().addNewStamp(id);
		return String.valueOf(index);
	}
}