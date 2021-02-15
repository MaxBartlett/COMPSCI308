/**
 * Although making Brick an abstract class and onCollision abstract method
 * required few changes to my original code, I believe it is significant as
 * it supports the best practices we discussed in lecture. The main difference
 * between different kinds of bricks is that they have different behavior on
 * collision, and this is made clear by making onCollision abstract. Additionally,
 * a Brick object itself is never intended to be instantiated, so making it abstract
 * ensures that this will never be done on accident.
 *
 * I also added a default constructor, as this adds flexibility in making subclasses
 * that wasn't available previously.
 */

/**
 * Brick is a superclass of all different types of Bricks in the game.
 * It consists of an ImageView, brick, that is placed in given (x, y) coordinates
 * in the scene. Different bricks have different behavior when they collide with
 * a Ball object.
 *
 * There is also a boolean destroyed, associated with Brick objects
 * that changes state when a Brick has been destroyed.
 * @author Max Bartlett
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Brick {
    private Image image;
    private ImageView brick;
    private boolean destroyed = false;

    /**
     * The sole constructor for Brick initializes the ImageView, brick, to be
     * displayed in the game scene, and sets the brick's coordinates to x, y.
     * @param imageFilename The filename of the brick image
     * @param x the x coordinate of the brick to be placed in the game window
     * @param y the y coordinate of the brick to be placed in the game window
     */

    public Brick(String imageFilename) {
        image = new Image(getClass().getClassLoader().getResourceAsStream(imageFilename));
        brick = new ImageView(image);
        setInitialCoordinates(0, 0 );
    }

    public Brick(String imageFilename, double x, double y) {
        image = new Image(getClass().getClassLoader().getResourceAsStream(imageFilename));
        brick = new ImageView(image);
        setInitialCoordinates(x, y);
    }

    /**
     * @return the ImageView of the brick
     */
    public ImageView getImageView() {
        return brick;
    }

    /**
     * Sets the initial coordinates of the brick to x, y
     * @param x the x coordinate of the brick to be placed in the game window
     * @param y the y coordinate of the brick to be placed in the game window
     */
    public void setInitialCoordinates(double x, double y) {
        brick.setX(x);
        brick.setY(y);
    }

    /**
     * onCollision is to be called on every collision with a Ball object,
     * and is to be defined differently for each subclass
     */
    public abstract void onCollision();

    /**
     * @return the boolean destroyed
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * @param result the new value of destroyed
     */
    public void setDestroyed(boolean result) {
        destroyed = result;
    }
}
