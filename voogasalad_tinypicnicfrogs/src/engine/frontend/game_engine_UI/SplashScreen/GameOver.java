package engine.frontend.game_engine_UI.SplashScreen;


import engine.controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * @author pkp9
 * splash screen for game over
 */

public class GameOver extends SplashScreen {


    @Override
    void addElements() {
        pane.setCenter(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("game_over.png"))));
    }

    GameOver(Controller controller) {
        super(controller);
    }

}