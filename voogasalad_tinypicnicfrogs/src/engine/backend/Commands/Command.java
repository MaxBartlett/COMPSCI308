package engine.backend.Commands;

import java.util.List;

/**
 * An abstract class that reifies method calls
 * @author Christopher Lin
 */
public abstract class Command {
    String myName;
    Object myTarget;

    //TODO: consider refactoring to interface
    Command(){
        myName = "default name";
    }
    Command(String name){
        myName =name;
    }

    /**
     * Runs the method
     * @param params A list of parameters
     */

    public abstract void execute(List<Object> params);

    public void bind(Object target){
        myTarget = target;
    }

    /**
     *
     * @return name of the command. Useful for the menu
     */
    public String getName(){
        return myName;
    }

    /**
     * for testing purposese
     */
    public void serialize(){System.out.println(getName());}
}
