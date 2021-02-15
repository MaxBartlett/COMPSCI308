package engine.backend.Commands;

import engine.backend.Actor;

import java.util.List;

/**
 * moves actor let
 * @author cl349
 */
public class MoveLeftCommand extends Command{
    @Override
    public void execute(List<Object> params) {
        int myAmt = (int) params.get(0);
        ((Actor) myTarget).moveLeft(myAmt);
    }
}
