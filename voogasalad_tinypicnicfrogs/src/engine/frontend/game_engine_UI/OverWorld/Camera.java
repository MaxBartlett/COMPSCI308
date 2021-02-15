package engine.frontend.game_engine_UI.OverWorld;

import engine.backend.Actor;
import engine.backend.Coordinate;
import javafx.scene.layout.Pane;

/**
 * Camera that controls the view inside OverWorldView
 *
 * @author Duy Trieu (dvt5)
 */
public class Camera extends Pane {
    Actor myPlayer;

    int xOffset;
    int yOffset;

    /**
     * @param player the player to focus the camera into
     */
    public Camera(Actor player) {
        this.myPlayer = player;
    }
    public void move() {
        Coordinate coor = myPlayer.getCoordinate();
        this.xOffset = coor.getX()-200;
        this.yOffset = coor.getY()-200;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }
}
