package engine.frontend.game_engine_UI.SplashScreen;

import engine.controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author pkp9
 * splash screen after battle is lost
 */

public class Loss extends SplashScreen {

    @Override
    void addElements() {
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("black_screen.png")));
        pane.getChildren().add(background);
        background.setFitHeight(SCREEN_HEIGHT);
        background.setFitWidth(SCREEN_WIDTH);
        BorderPane borderPane = new BorderPane();
        Text text = new Text("You scurried to get help,\nprotecting your exhausted and fainted\nteam from further harm...");
        text.setFill(Color.WHITE);
        pane.setCenter(text);
    }

    public Loss(Controller controller) {
        super(controller);
    }
}