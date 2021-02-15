package engine.backend;

import engine.backend.Commands.Command;

import java.util.List;

/**
 * Chooses an option from a list of commands
 * @author Christopher Lin cl349
 */
public abstract class AI {
    protected List<Command> myOptions;

    /**
     * Provide a list of Commands for the Ai to choose from
     * @param options List of possible commands to choose
     */
    public void setOptions(List<Command> options){
        myOptions = options;
    }

    /**
     * AI chooses an option from its list
     * @return Command option
     */
    public abstract Command getOption();

}
