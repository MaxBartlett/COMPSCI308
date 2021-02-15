/**
 * @author Michael Glushakov (mg367)
 */
package Backend.Commands.ControlStructures;

import Backend.CommandManager;
import Backend.Commands.BracketedCommand;
import Backend.Exceptions.InvalidSyntaxException;
import Backend.VariableTracker;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Glushakov
 */
public class CommandTo extends BracketedCommand {

    private static final String myKey = "MakeUserInstruction";

    public CommandTo(VariableTracker tracker) {
        super(tracker);
        setKey(myKey);
    }

    @Override
    public String execute(List<String> params) {
        List<String> commandList;
        String varName;
        double varValue;
        if (params.get(0).equals(START_DELIMETER)) {
            throw new InvalidSyntaxException(myKey);
        }
        String key = params.remove(0);//[
        if (CommandManager.isCommand(key, myTracker)) {
            throw new InvalidSyntaxException(myKey);
        }
        if (myTracker.get(key) != null) {
            throw new InvalidSyntaxException(myKey);
        }

        int endIndex = getCloseIndex(params);
        storeVars(params, endIndex);
        commandList = new ArrayList<>(params.subList(params.indexOf(START_DELIMETER) + 1, getCloseIndex(params)));
        int end = getCloseIndex(params);
        myTracker.putCommand(key, commandList);

        for (int i = 0; i <= end; i += 1) {
            params.remove(0);
        }

        params.add(0, ":" + key);

        return "";
    }

    private void storeVars(List<String> params, int endIndex) {
        String varName;
        double varValue;
        for (int i = 1; i < endIndex; i += 2) {//Storing variables
            try {
                varName = params.get(i);
                varValue = Double.parseDouble(params.get(i + 1));
                myTracker.put(varName, varValue);
            } catch (NumberFormatException ne) {
                throw new InvalidSyntaxException(myKey);
            }
        }

        for (int i = 0; i < endIndex; i += 1) {//removing the variables
            params.remove(0);
        }
        params.remove(0);
    }
}
