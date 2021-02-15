package Backend.Commands.Turtle;

/**
 * @author Christopher Lin
 */

import Backend.Commands.BracketedCommand;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.Turtle;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.List;

public class CommandAskWith extends BracketedCommand {
    private static final String key = "AskWith";

    public CommandAskWith(VariableTracker tracker) {
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
        List<String> conditionExp = new ArrayList<>();

        var oldActiveTurtles = new ArrayList<Turtle>();
        oldActiveTurtles.addAll(myTracker.getTurtleManager().getActiveTurtles());

        var validTurtles = new ArrayList<Turtle>();

        for (Turtle t : myTracker.getTurtleManager().getAllTurtles()) {
            var activeTurtle = new ArrayList<Turtle>();
            activeTurtle.add(t);
            myTracker.getTurtleManager().setActiveTurtles(activeTurtle);
            conditionExp.addAll(params.subList(openBracket + 1, closeBracket));
            double conditionVal = evaluateBrackets(conditionExp).get(0);
            if (conditionVal > 0) {
                validTurtles.add(t);
            }
        }
        myTracker.getTurtleManager().setActiveTurtles(validTurtles);
        if (validTurtles.size() < 1) {
            return "";
        }
        var commandOutput = evaluateBrackets(params.subList(closeBracket, params.size() - 1));

        myTracker.getTurtleManager().setActiveTurtles(oldActiveTurtles);

        return Double.toString(commandOutput.get(commandOutput.size() - 1));
    }
}
