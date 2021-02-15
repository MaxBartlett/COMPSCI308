package engine.backend.gameevent;

import javafx.event.Event;

/**
 * Represents an event that occurs in the game. This can be thrown by user input or by
 * the frontend. All GameEvents should be handled by GameWorld.handleInput(). It can encapsulate a java event
 * but does not require it.
 *
 * @author cl349
 */
public abstract class GameEvent {
    Event myEvent;
    InputSource mySource;

    /**
     * Creates a new gameEvent object
     * @param src Indicates if the source is from the player of an AI (only really useful for combat stuff(
     */
    GameEvent(InputSource src){
        mySource = src;
    }

    /**
     *
     * @return The input source
     */

    public InputSource getSource(){
        return mySource;
    }

    /**
     *
     * @return the actual java event
     */
    public Event getEvent(){
        return myEvent;
    }
}
