package engine.backend.gameevent;

/**
 * Thrown by the frontend when an animation has finished playing.
 * @author cl349
 */
public class AnimationFinishedEvent extends GameEvent {
    public AnimationFinishedEvent() {
        super(InputSource.PLAYER);
    }
}
