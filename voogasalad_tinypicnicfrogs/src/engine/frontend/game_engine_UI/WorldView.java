package engine.frontend.game_engine_UI;

import engine.backend.gameevent.GameKeyEvent;
import engine.controller.Controller;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Super class that manages the view in the game. This class is extended by OverWorldView
 * and BattleWorldView. Main functionality includes adding all AnimationObjects to the
 * Pane, updating the game loop, and switch between two world views.
 *
 * To run the game loop, a ParallelTransition is used to update all the animation needed for the
 * engine view.
 *
 * @author Duy Trieu (dvt5)
 */
public abstract class WorldView {
    protected Timeline animation;
    protected Scene myScene;
    protected BorderPane displayPane;
    protected Controller myController;
    protected ParallelTransition animationManager = new ParallelTransition();
    protected final IntegerProperty frameCounter = new SimpleIntegerProperty(0);

    private static final int FRAMES_PER_SECOND = 1;
    protected static final int MILLISECOND_DELAY = 100 / FRAMES_PER_SECOND;

    /**
     * @param controller Controller that will send information from the back end to be updated in the front end
     */
    public WorldView (Controller controller) {
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        this.myController = controller;
        displayPane = new BorderPane();
        myScene = new Scene(displayPane, 750 , 600, Color.BLACK);
        myScene.setOnKeyPressed(e -> myController.getGameWorld().handleInput(new GameKeyEvent(e)));
    }

    /**
     * main method to run the game loop
     */
    protected void playAnimation () {
        animationManager.play();
    }
    /**
     * Clear the view
     */
    public void clearView () {
        displayPane.getChildren().clear();
    }

    /**
     * return myScene
     */
    public Scene getMyScene () {
        return myScene;
    }

}
