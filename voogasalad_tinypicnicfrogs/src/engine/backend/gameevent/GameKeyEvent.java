package engine.backend.gameevent;

import javafx.scene.input.KeyEvent;

/**
 * Represents an event where a key is pressed by the user
 *
 * @author cl349
 */
public class GameKeyEvent extends GameEvent{
    public GameKeyEvent(KeyEvent e){
        super(InputSource.PLAYER);
        myEvent = e;
    }
}
