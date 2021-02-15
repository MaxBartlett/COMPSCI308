/**
 * StoneBrick is a subclass of Brick, and is intended to never break no matter
 * how many times it collides with a Ball object.
 *
 * @author Max Bartlett
 */

public class StoneBrick extends Brick{
    public static final String IMAGE_FILENAME = "brick7.gif";
    public StoneBrick(double x, double y) {
        super(IMAGE_FILENAME, x, y);
    }

    @Override
    public void onCollision() {}
}
