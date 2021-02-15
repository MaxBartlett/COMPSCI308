package Backend.Commands.Turtle;

import Backend.Commands.BracketedCommand;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Christopher Lin
 */
public class CommandTell extends BracketedCommand {
    private static final String key = "Tell";

    public CommandTell(VariableTracker tracker) {
        super(tracker);
        setKey(key);
    }

    @Override
    public String execute(List<String> params) {
        int openBracket = params.indexOf("[");
        int closeBracket = params.indexOf("]");
        if (openBracket == -1 || closeBracket == -1 || closeBracket < openBracket) {
            throw new InvalidSyntaxException(key);
        }

        var idExp = params.subList(openBracket + 1, closeBracket);
        ArrayList<Double> idListDouble = new ArrayList<>();
        try {
            idListDouble = evaluateBrackets(idExp);
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }
        ArrayList<Integer> idList = new ArrayList<>();
        for (Double d : idListDouble) {
            idList.add(d.intValue());
        }
        myTracker.getTurtleManager().setActiveTurtlesByID(idList);
        return String.valueOf(idList.get(idList.size() - 1));
    }
}
