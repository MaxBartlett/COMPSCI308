/**
 * Paddle contains methods relating to the behavior of the game's paddle.
 *
 * The paddle can only move horizontally, and must stay within
 * the bounds of the game's window
 *
 * The paddle is controlled using the arrow keys, specified in the main class.
 *
 * @author Max Bartlett
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {
    public static final String IMAGE_FILENAME = "paddle.gif";
    private Image image;
    private ImageView paddle;
    public static final int SPEED = 2;
    private int xDirection = 0;

    /**
     * Paddle's constructor initializes the ImageView to be used in the game scene.
     */
    Paddle() {
        image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        paddle = new ImageView(image);
    }

    /**
     * @return the ImageView of the paddle to be used in the game scene
     */
    public ImageView getImageView() {
        return paddle;
    }

    /**
     * @return The xDirection of the paddle.
     */
    public int getXDirection() {
        return xDirection;
    }

    /**
     * Sets the xDirection of the paddle
     * @param direction
     */
    public void setXDirection(int direction) {
        xDirection = direction;
    }

    /**
     * @return The speed of the paddle.
     */
    public int getSpeed() {
        return SPEED;
    }

    /**
     * @return The x coordinate of the paddle.
     */
    public double getX() {
        return paddle.getX();
    }

    /**
     * Sets the x coordinate of the paddle.
     * @param x the new x coordinate of the paddle.
     */
    public void setX(double x) {
        paddle.setX(x);
    }

    /**
     * @return the y coordinate of the paddle
     */
    public double getY() {
        return paddle.getY();
    }

    /**
     * Sets the y coordinate of the paddle
     * @param y the new y coordinate of the paddle.
     */
    public void setY(double y) {
        paddle.setY(y);
    }

    /**
     * Checks the bounds of the paddle.
     * If the paddle moves outside the bounds of the window, move it back
     * inside the window.
     * Since the paddle can only move horizontally, only the x coordinate is checked.
     * @param width the width of the window
     * @param height the height of the window
     */
    public void checkBounds(int width, int height) {
        if (paddle.getX() <= 0) {
            paddle.setX(0);
        }

        if (paddle.getX() >= width - paddle.getBoundsInParent().getWidth()) {
            paddle.setX(width - paddle.getBoundsInParent().getWidth());
        }
    }

    /**
     * Sets the x coordinate based on current coordinate, given speed and xDirection
     */
    public void setCoordinates() {
        setX(getX() + SPEED * xDirection);
    }

    /**
     * Sets the initial coordinates of the paddle
     * @param width the width of the window
     * @param height the height of the window
     */
    public void setInitialCoordinates(int width, int height) {
        setX(width / 2 - paddle.getBoundsInLocal().getWidth() / 2);
        setY(height - 50);
    }
}
