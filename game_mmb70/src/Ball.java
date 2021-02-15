/**
 * Ball contains methods relating to the behavior of the game's ball.
 *
 * The ball can move vertically and horizontally, and must stay within
 * the bounds of the game window.
 *
 * The direction of the ball is dependent on collision with both bricks and the paddle.
 *
 * The physics mechanics of the ball are not well defined, and require
 * further work to be implemented properly.
 *
 * @author Max Bartlett
 */

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    public static final String IMAGE_FILENAME = "ball.gif";
    public static final double SPEED = 2;
    private Image image;
    private ImageView ball;
    private int xDirection = 1;
    private int yDirection = 1;

    /**
     * Ball's constructor initializes the ImageView to be used in the game scene.
     */
    public Ball() {
        image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE_FILENAME));
        ball = new ImageView(image);
    }

    /**
     * @return the ImageView of the ball
     */
    public ImageView getImageView () {
        return ball;
    }

    /**
     * @return the xDirection of the ball
     */
    public int getXDirection() {
        return xDirection;
    }

    /**
     * Sets the xDirection of the ball
     * @param direction the new xDirection of the ball
     */
    public void setXDirection(int direction) {
        xDirection = direction;
    }

    /**
     * @return the yDirection of the ball
     */
    public int getYDirection() {
        return yDirection;
    }

    /**
     * Sets the yDirection of the ball
     * @param direction the new yDirection of the ball
     */
    public void setYDirection(int direction) { yDirection = direction; }

    /**
     * @return the speed of the ball
     */
    public double getBallSpeed() {
        return SPEED;
    }

    /**
     * @return the x coordinate of the ball
     */
    public double getX() { return ball.getX(); }

    /**
     * Sets the x coordinate of the ball
     * @param x the new x coordinate of the ball
     */
    public void setX(double x) { ball.setX(x); }

    /**
     * @return the y coordinate of the ball
     */
    public double getY() { return ball.getY(); }

    /**
     * Sets the y coordinate of the ball
     * @param y the new y coordinate of the ball
     */
    public void setY(double y) { ball.setY(y); }

    /**
     * Sets the coordinates of the ball based on current coordinates, speed, and direction
     */
    public void setCoordinates() {
        setX(getX() + SPEED * xDirection);
        setY(getY() + SPEED * yDirection);
    }

    /**
     * Sets the initial coordinates of the ball, to the middle of the game window
     * @param width width of the game window
     * @param height height of the game window
     */
    public void setInitialCoordinates(int width, int height) {
        setX(width / 2 - ball.getBoundsInLocal().getWidth() / 2);
        setY(height / 2 - ball.getBoundsInLocal().getHeight() / 2);
    }

    /**
     * Checks the bounds of the ball. If the ball is outside of the bounds of
     * the game window, the ball is moved back inside the window.
     * @param width width of the game window
     * @param height width of the game window
     */
    public void checkBounds(int width, int height) {
        Boolean xLessThan = getX() <= 0;
        Boolean xGreaterThan = getX() >= width - ball.getBoundsInParent().getWidth();
        Boolean yLessThan = getY() <= 0;
        Boolean yGreaterThan = getY() >= height - ball.getBoundsInParent().getHeight();

        if(xLessThan || xGreaterThan) {
            xDirection *= -1;
        }

        if(yLessThan || yGreaterThan) {
            yDirection *= -1;
        }
    }

    /**
     * Checks the ball's collision with a given brick.
     * If the ball intersects with a brick, the direction is reversed.
     * @param brick the brick with which to check collision
     * @return boolean indicating whether collision occurred or not
     */
    public boolean checkBrickCollision(Brick brick) {
        boolean intersectsBrick =  brick.getImageView().getBoundsInParent().intersects(ball.getBoundsInParent());
        if (intersectsBrick && getYDirection() < 0) {
            yDirection *= -1;
            return true;
        }
        return false;
    }

    /**
     * Checks the ball's collision with a given paddle.
     * If the ball intersects with a brick, the direction is reversed
     * @param paddle the paddle with which to check collision
     * @return boolean indicating whether collision occurred or not
     */
    public boolean checkPaddleCollision(Paddle paddle) {
        boolean intersectsPaddle = paddle.getImageView().getBoundsInParent().intersects(ball.getBoundsInParent());

        if (intersectsPaddle && getYDirection() > 0) {
            //xDirection += paddle.getXDirection();
            yDirection *= -1;
            return true;
        }
        return false;
    }
}
