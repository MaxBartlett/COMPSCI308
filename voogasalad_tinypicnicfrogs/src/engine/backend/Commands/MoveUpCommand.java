package engine.backend.Commands;

import engine.backend.Actor;
import java.util.List;


/**
 * moves actor up
 * @author cl349
 */
public class MoveUpCommand extends Command {
    @Override
    public void execute(List<Object> params) {
        int myAmt = (int) params.get(0);
        ((Actor) myTarget).moveUp(myAmt);
    }
}
