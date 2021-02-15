package FrontEnd;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/*
    @author xp19
 */

public class Pen {
    private TurtlePlayground display;
    private SimpleBooleanProperty isDownProperty;
    private Color color;
    private double thickness;
    private int colorIndex;

    protected Pen(TurtlePlayground canvas, boolean isDown, Color initialColor, double initialWidth) {
        display = canvas;
        isDownProperty = new SimpleBooleanProperty(isDown);
        color = initialColor;
        thickness = initialWidth;
    }

    protected Line drawLine(Point2D start, Point2D finish) {
        Line trail = new Line(start.getX(), start.getY(), finish.getX(), finish.getY());
        trail.setStroke(color);
        trail.setStrokeWidth(thickness);
        return trail;
    }

    protected ReadOnlyBooleanProperty readOnlyIsDownProperty() {
        return ReadOnlyBooleanProperty.readOnlyBooleanProperty(isDownProperty);
    }

    protected boolean isDown() {
        return isDownProperty.get();
    }

    protected void setDown(boolean down) {
        isDownProperty.set(down);
    }

    protected void togglePenDown() {
        isDownProperty.set(!isDown());
    }

    protected Color getColor() {
        return color;
    }

    protected void setColor(Color c) {
        color = c;
    }

    protected void setThickness(double width) {
        thickness = width;
    }
}
