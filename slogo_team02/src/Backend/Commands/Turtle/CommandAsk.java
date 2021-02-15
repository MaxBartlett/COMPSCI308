/**
 * Implements the Ask command
 *
 *
 * @author Christopher Lin
 *
 */

package Backend.Commands.Turtle;

import Backend.Commands.BracketedCommand;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.Turtle;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.List;

public class CommandAsk extends BracketedCommand {
    private static final String key = "Ask";

    public CommandAsk(VariableTracker tracker) {
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
        var turtleExp = params.subList(openBracket + 1, closeBracket);

        var turtleIdDoubles = evaluateBrackets(turtleExp);

        var turtleIDs = new ArrayList<Integer>();

        for (Double d : turtleIdDoubles) {
            turtleIDs.add(d.intValue());
        }

        var oldTurtleList = new ArrayList<Turtle>();
        oldTurtleList.addAll(myTracker.getTurtleManager().getActiveTurtles());

        myTracker.getTurtleManager().setActiveTurtlesByID(turtleIDs);
        List<String> commandExp = new ArrayList<>();
        var retList = new ArrayList<Double>();
        try {
            commandExp = params.subList(closeBracket + 1, params.size() - 1);
            commandExp.remove("[");
            commandExp.remove("]");
        } catch (Exception e) {
            throw new InvalidSyntaxException(key);
        }

        retList = evaluateBrackets(commandExp);


        myTracker.getTurtleManager().setActiveTurtles(oldTurtleList);

        return String.valueOf(retList.get(retList.size() - 1));
    }
}
