package engine.backend.Commands;

import engine.backend.DialogueInteraction;


import java.util.List;

/**
 * Unused
 * @author cl349
 */

public class DialogueSelectCommand extends Command{

    String myOption;

    public DialogueSelectCommand(String optionName){
        myOption = optionName;
    }

    @Override
    public void execute(List<Object> params) {
        ((DialogueInteraction) myTarget).getNextNode(myOption);
    }

    @Override
    public void bind(Object target) {
        if(target instanceof DialogueInteraction){
            super.bind((DialogueInteraction) target);
        }
        else{
            throw new IllegalArgumentException("Not a dialogue interaction");
        }
    }
}
