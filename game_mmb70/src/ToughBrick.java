/**
 * ToughBrick is a subclass of Brick. It is similar to NormalBrick in that it
 * is to be removed from the scene after colliding with a Ball object.
 * But unlike a NormalBrick, it takes three collisions to be removed instead of one.
 *
 * hitCounter keeps track of the number of hits on a ToughBrick.
 *
 * @author Max Bartlett
 */

public class ToughBrick extends Brick {
    public static final String IMAGE_FILENAME = "brick4.gif";
    private int hitCounter = 0;

    /**
     * The constructor of ToughBrick only invokes the constructor of the superclass.
     * @param x the x coordinate of the brick to be placed in the game window
     * @param y the y coordinate of the brick to be placed in the game window
     */
    public ToughBrick(double x, double y) {
        super(IMAGE_FILENAME, x, y);
    }

    /**
     * onCollision is to be called on every collision with a Ball object.
     * The hitCounter is incremented and the ToughBrick is removed from the scene
     * if hitCounter >= 3.
     */
    @Override
    public void onCollision() {
        hitCounter++;
        if(hitCounter >= 3) {
            getImageView().setImage(null);
            setDestroyed(true);
        }
    }
}
