package menu;

import engine.backend.Commands.Command;

import java.util.List;

public abstract class Menu{
    protected List<Command> myOptions;

    /**
     * Constructor takes in all the different command objects
     * @param options
     */
    Menu(List<Command> options){
        myOptions = options;
    }

    public abstract List<Command> getChoices();
}
