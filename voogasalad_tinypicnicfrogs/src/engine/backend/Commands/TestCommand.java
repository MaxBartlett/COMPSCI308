package engine.backend.Commands;

import java.util.List;

/**
 * Just for testing
 * @author cl349
 */

public class TestCommand extends Command {
    TestCommand(){
        super("Test Command");
    }

    @Override
    public void execute(List<Object> params) {
        System.out.println("Test command executed");
    }


}
