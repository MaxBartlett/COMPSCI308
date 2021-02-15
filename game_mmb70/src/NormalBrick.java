/**
 * NormalBrick is a subclass of Brick, and it represents a brick that is
 * to be removed from the scene after colliding with a Ball object once.
 *
 * @author Max Bartlett
 */

public class NormalBrick extends Brick {
    public static final String IMAGE_FILENAME = "brick1.gif";

    /**
     * The constructor of ToughBrick only invokes the constructor of the superclass.
     * @param x the x coordinate of the brick to be placed in the game window
     * @param y the y coordinate of the brick to be placed in the game window
     */

    public NormalBrick(double x, double y) {
        super(IMAGE_FILENAME, x, y);
    }

    /**
     * onCollision is to be called on collision with a Ball object,
     * and removes the NormalBrick from the scene
     */
    @Override
    public void onCollision() {
        getImageView().setImage(null);
        setDestroyed(true);
    }
}
