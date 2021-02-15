package engine.frontend.game_engine_UI.SplashScreen;

import engine.controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * @author pkp9
 * splash screen for paused game
 */

public class Pause extends SplashScreen {

    void addElements() {
        ImageView paused_screen = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("paused_screen.png")));
        pane.setCenter(paused_screen);
    }

    Pause(Controller controller) {
        super(controller);
    }

    protected void interpolate(double d) {

    }
}