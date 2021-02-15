package engine.backend.gameevent;

import engine.backend.Commands.Command;

/**
 * Thrown by the Menu when the user selects an option
 *
 * @author cl349
 * */

public class GameMenuEvent extends GameEvent{

    Command myOption;

    /**
     * Constructs a new GameMenuEvent
     * @param opt the Command that was chosen from the menu
     * @param src source of the inut
     */
    public GameMenuEvent(Command opt, InputSource src){
        super(src);
        myOption = opt;
    }

    public Command getOption(){
        return myOption;
    }

}
