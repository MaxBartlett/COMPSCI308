package player;

import engine.frontend.game_engine_UI.Main;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ResourceBundle;

import static javafx.application.Application.launch;
import static player.SceneManager.DEFAULT_RESOURCE;

/**
 * @author Michael Glushakov
 * Purpose: Main Class in player package
 * Dependencies: SceneManager, UserProfile manager
 * Usages: Runs player
 */
public class PlayerMain extends Application {
    private SceneManager myManager;
    private Main engineMain;
    private UserProfileManager userProfileManager;
    public static final int SCREEN_SIZE=700;
    private authoring.authoring_frontend.Main authoringMain;
    private ResourceBundle myResources;

    @Override
    public void start(Stage stage){
        myResources=ResourceBundle.getBundle(DEFAULT_RESOURCE);
        stage.setTitle(myResources.getString("playerTitle"));
        userProfileManager= new UserProfileManager();
        myManager=new SceneManager(userProfileManager, stage);
        stage.show();

    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
