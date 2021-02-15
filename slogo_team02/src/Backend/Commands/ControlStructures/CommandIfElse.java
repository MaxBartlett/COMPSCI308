/**
 * @author Michael Glushakov (mg367)
 */
package Backend.Commands.ControlStructures;

import Backend.Command;
import Backend.CommandManager;
import Backend.Commands.BracketedCommand;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christopher Lin
 */
public class CommandIfElse extends BracketedCommand {
    private static final String myKey = "Ifelse";

    public CommandIfElse(VariableTracker tracker) {
        super(tracker);
        setKey(myKey);
    }

    @Override
    public String execute(List<String> params) {
        List<Integer> testList = new ArrayList<Integer>();

        int openBracket = params.indexOf(START_DELIMETER);

        var expressionParams = params.subList(0, openBracket);
        var firstExpressionString = expressionParams.get(0);
        Command firstExpressionCommand = null;
        double expressionValue = -1;
        if (CommandManager.isCommand(firstExpressionString, myTracker)) {
            firstExpressionCommand = CommandManager.getCommand(expressionParams, myTracker);
        } else {
            //indicates that first expression is a value, not a command
            expressionValue = Double.parseDouble(firstExpressionString);
        }
        expressionParams.remove(0);
        if (expressionValue == -1) {
            expressionValue = Double.parseDouble(firstExpressionCommand.execute(expressionParams));
        }

        if (expressionValue == 0) {
            openBracket = params.indexOf(START_DELIMETER);
            int closeBracket = getCloseIndex(params);

            if (openBracket == -1 || closeBracket == -1 || closeBracket < openBracket) {
                throw new InvalidSyntaxException(myKey);
            }
            try {
                for (int i = 0; i <= closeBracket + 1; i++) {
                    params.remove(0);
                }
            } catch (Exception e) {
                throw new InvalidSyntaxException(myKey);
            }
            params.remove(getCloseIndex(params));

            List<Double> retVals = evaluateBrackets(params);

            return String.valueOf(retVals.get(0));
        } else {
            int closeBracket = getCloseIndex(params);
            int paramSize = params.size();
            //var commandParams = params.subList(1, closeBracket);
            for (int i = closeBracket; i < paramSize; i++) {
                params.remove(closeBracket);
            }
            params.remove(0);
            params.remove(params.subList(getCloseIndex(params), params.size()));

            List<Double> retVals = evaluateBrackets(params);
            //params.remove(getCloseIndex(params));
            //params.remove(params.indexOf(START_DELIMETER));

            return String.valueOf(retVals.get(0));
        }
    }
}
