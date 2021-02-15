package engine.frontend.game_engine_UI.AnimationProcesser;

/**
 * Sprite class that represent a window inside the
 * sprite sheet
 *
 * @author Duy Trieu (dvt5)
 */
public class Sprite {
    double x,y,width,height;
    public Sprite (double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    /**
     * @return x
     */
    public double getX () { return x; }
    /**
     * @return y
     */
    public double getY () { return y; }
    /**
     * @return width
     */
    public double getWidth () { return width; }
    /**
     * @return height
     */
    public double getHeight () { return height; }
}
