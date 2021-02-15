package engine.backend;

import engine.backend.gameevent.GameMenuEvent;
import engine.backend.gameevent.InputSource;

/**
 * Runs the turn for an AI controlled opponent
 *
 * @author cl349
 */
public class AITurn extends Turn {

    /**
     *
     * @param controlledEnemy the enemy Actor that is being controlled by the AI
     */
    AITurn(CombatInteraction controlledEnemy){
        mySource = InputSource.AI;
        myInt = controlledEnemy;
    }

    /**
     * Sets up the AI turn. Note that it makes its move immediately.
     */
    @Override
    public void initializeTurn() {
        ServiceLocator.getAI().setOptions(myInt.getCommandList());
        var nextMove = ServiceLocator.getAI().getOption();
        ServiceLocator.getCombatManager().receiveInput(new GameMenuEvent(nextMove, InputSource.AI));
    }
}
