package engine.backend;

import engine.backend.AI;
import engine.backend.Commands.Command;

import java.util.List;
import java.util.Random;


/**
 * A simple AI that randomly chooses an option from the list
 *
 * @author cl349
 */
public class RandomAI extends AI {
    @Override
    public Command getOption() {
        if(myOptions.size() == 0){
            return null;
        }

        Random rn = new Random();
        var optionIndex = rn.nextInt(myOptions.size());
        System.out.println(myOptions.get(optionIndex));
        return myOptions.get(optionIndex);
    }
}
