/**
 * @author Max Bartlett
 *
 * Handles all behaviors of Turtle
 */

package Backend;

import FrontEnd.Controller;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Turtle {
    public static final int DEFAULT_ID = 1;
    public static final double DEFAULT_X = 0;
    public static final double DEFAULT_Y = 0;
    public static final int DEFAULT_HEADING = 90; //default is facing up
    public static final boolean DEFAULT_PENDOWN = true;
    public static final boolean DEFAULT_VISIBILITY = true;
    private Controller controller;
    private Point2D coordinates;
    private int ID;
    private double heading; //in degrees
    private boolean penDown;
    private boolean visibility;

    /**
     * constructs a Turtle object with custom parameters
     *
     * @param ID         ID number
     * @param x          initial x coordinate
     * @param y          initial y coordinate
     * @param heading    initial heading
     * @param penDown    initial penDown
     * @param visibility initial visibility
     */
    private Turtle(int ID, double x, double y, int heading, boolean penDown, boolean visibility) {
        this.ID = ID;
        coordinates = new Point2D(x, y);
        this.heading = heading;
        this.penDown = penDown;
        this.visibility = visibility;
    }

    /**
     * constructs a Turtle object with a custom x, y
     *
     * @param x initial x coordinate
     * @param y initial y coordinate
     */
    public Turtle(int ID, double x, double y) {
        this(ID, x, y, DEFAULT_HEADING, DEFAULT_PENDOWN, DEFAULT_VISIBILITY);
    }

    /**
     * constructs a Turtle object with a custom ID
     * @param ID ID number
     */
    public Turtle(int ID) {
        this(ID, DEFAULT_X, DEFAULT_Y);
    }

    /**
     * constructs a Turtle object with default parameters
     */
    public Turtle() {
        this(DEFAULT_ID, DEFAULT_X, DEFAULT_Y, DEFAULT_HEADING, DEFAULT_PENDOWN, DEFAULT_VISIBILITY);
    }

    /**
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * sets ID
     *
     * @param ID new ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return x coordinate
     */
    public int getX() {
        return (int) coordinates.getX();
    }

    /**
     * @return y coordinate
     */
    public int getY() {
        return (int) coordinates.getY();
    }

    /**
     * @return heading
     */
    public double getHeading() {
        return heading;
    }

    /**
     * @return penDown
     */
    public boolean getPenDown() {
        return penDown;
    }

    /**
     * sets penDown
     *
     * @param penDown new penDown value
     */
    private void setPenDown(boolean penDown) {
        this.penDown = penDown;
        controller.setPenDown(penDown);
    }

    /**
     * @return visibility
     */
    public boolean getVisibility() {
        return visibility;
    }

    /**
     * sets visibility
     *
     * @param visibility new visibility value
     */
    private void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * moves coordinates a given distance in the direction of heading
     *
     * @param distance distance to move coordinates
     * @return distance
     */
    private double move(double distance) {
        int dx = (int) Math.floor(distance * Math.cos(Math.toRadians(heading)));
        int dy = (int) Math.floor(distance * Math.sin(Math.toRadians(heading)));
        this.coordinates.add(dx, dy);
        controller.update(distance * Math.cos(Math.toRadians(heading)), distance * Math.sin(Math.toRadians(heading)));
        return distance;
    }

    /**
     * moves coordinates a given distance in the direction of heading
     *
     * @param distance distance to move coordinates
     * @return distance
     */
    public double forward(double distance) {
        return move(distance);
    }

    /**
     * moves coordinates a given distance in the opposite direction of heading
     *
     * @param distance distance to move coordinates
     * @return distance
     */
    public double back(double distance) {
        return move(distance * -1);
    }

    /**
     * turns heading degrees counterclockwise
     *
     * @param degrees number of degrees to add to heading
     * @return degrees
     */
    public double left(double degrees) {
        setHeading(this.heading + degrees);
        return degrees;
    }

    /**
     * turns heading heading degrees clockwise
     *
     * @param degrees number of degrees to subtract from heading
     * @return degrees
     */
    public double right(double degrees) {
        setHeading(this.heading - degrees);
        return degrees;
    }

    /**
     * sets heading to heading
     *
     * @param heading new heading value
     * @return number of degrees moved
     */
    public double setHeading(double heading) {
        if (heading < 0 || heading > 360) {
            if (heading < 0) {
                heading += 360;
            }
            heading %= 360;
        }
        double degreesMoved = Math.abs(this.heading - heading);
        controller.rotateTurtle(this.heading, heading);
        this.heading = heading;
        System.out.println(this.toString() + heading);
        return degreesMoved;
    }

    /**
     * sets heading to face the point (x, y)
     *
     * @param x x coordinate of the point to turn towards
     * @param y y coordinate of the point to turn towards
     * @return angle between coordinates and (x, y)
     */
    public double towards(double x, double y) {
        double angle = Math.toDegrees(Math.atan2(y - coordinates.getY(), x - coordinates.getX()));
        setHeading(angle);
        return angle;
    }

    /**
     * sets coordinates to (x, y)
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return distance from current coordinates to (x, y)
     */
    public double setXY(double x, double y) {
        double distance = coordinates.distance(x, y);
        this.coordinates = new Point2D(x, y);
        controller.setTurtlePosition(x, y);
        return distance;
    }

    /**
     * sets penDown to true
     *
     * @return 1
     */
    public int penDown() {
        setPenDown(true);
        return 1;
    }

    /**
     * sets penDown to false
     *
     * @return 0
     */
    public int penUp() {
        setPenDown(false);
        return 0;
    }

    /**
     * sets visibility to true
     *
     * @return 1
     */
    public int show() {
        setVisibility(true);
        controller.showTurtle();
        return 1;
    }

    /**
     * sets visiblity to false
     *
     * @return 0
     */
    public int hide() {
        setVisibility(false);
        controller.hideTurtle();
        return 0;
    }

    /**
     * clears screen
     */
    public void clearScreen() {
        this.coordinates = new Point2D(DEFAULT_X, DEFAULT_Y);
        setHeading(DEFAULT_HEADING);
        controller.reset();
    }

    /**
     * sets x and y to 0
     *
     * @return distance from current point to (0, 0)
     */
    public double home() {
        controller.setTurtleToHome();
        return setXY(0, 0);
    }

    /**
     * Sets background color
     * @param index index of color
     */
    public void changeBgColor(int index) {
        controller.setTurtlePlaygroundBgColor(index);
    }

    /**
     * Sets pen color
     * @param index index of color
     */
    public void changePenColor(int index) {
        controller.setPenColor(index);
    }

    /**
     * Sets pen stroke width
     * @param width size of width
     */
    public void setPenStrokeWidth(double width) {
        controller.setPenThickness(width);
    }

    /**
     * Sets palette
     * @param index color index
     * @param r red value
     * @param g green value
     * @param b blue value
     */
    public void setPalette(int index, int r, int g, int b) {
        Color c = Color.rgb(r, g, b);
        controller.setPalette(index, c);
    }

    /**
     * Sets turtle shape
     * @param index index of shape
     */
    public void setTurtleShape(int index) {
        controller.setTurtleShape(index);
    }

    /**
     * @return pen color index
     */
    public int getPenColorIndex() {
        return controller.getPenColor();
    }

    /**
     * @return shape index
     */
    public int getShapeIndex() {
        return controller.getImageIndex();
    }

    /**
     * @return controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @param controller sets controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * @return String indicating turtle's ID
     */
    @Override
    public String toString() {
        return "Turtle with ID: " + getID();
    }
}
