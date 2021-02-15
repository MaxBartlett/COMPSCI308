package engine.backend;

import engine.backend.gameevent.InputSource;

import static java.lang.Thread.sleep;

/**
 * Executes a player controlled turn
 * @author Christopher Lin cl349
 */
public class PlayerTurn extends Turn {

    PlayerTurn(CombatInteraction controlledAlly){
        mySource = InputSource.PLAYER;
        myInt = controlledAlly;
    }


    /**
     * Since we wait for the menu to throw an event, there is nothing to do here.
     */
    @Override
    public void initializeTurn() {
        return;
    }
}
