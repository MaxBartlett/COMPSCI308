package engine.frontend.game_engine_UI.SplashScreen;

import engine.controller.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author pkp9
 * splash screen that appears before a battle
 */

public class FightScene extends SplashScreen {


    void addElements() {
        ImageView vs_screen = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("VS_screen.png")));
        pane.getChildren().add(vs_screen);

        vs_screen.setFitHeight(SCREEN_HEIGHT);
        vs_screen.setFitWidth(SCREEN_WIDTH);

        //gets player image
        ImageView playerActorImageView = controller.getBattlePlayerAnimation().get(0).getAnimationView();
        pane.getChildren().add(playerActorImageView);
        playerActorImageView.setX(SCREEN_WIDTH / 4 - playerActorImageView.getFitWidth() / 2);
        playerActorImageView.setY(SCREEN_HEIGHT / 2 - playerActorImageView.getFitHeight() / 2);
        
        //gets opponent image
        ImageView opponentActorImageView = controller.getBattleEnemyAnimation().get(0).getAnimationView();
        pane.getChildren().add(opponentActorImageView);
        opponentActorImageView.setX(playerActorImageView.getX() + SCREEN_WIDTH / 2);
        opponentActorImageView.setY(SCREEN_HEIGHT / 2 - opponentActorImageView.getFitHeight());


    }
    public FightScene(Controller controller) {
        super(controller);
    }
}
