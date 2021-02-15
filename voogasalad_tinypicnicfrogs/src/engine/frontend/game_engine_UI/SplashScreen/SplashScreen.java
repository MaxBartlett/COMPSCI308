package engine.frontend.game_engine_UI.SplashScreen;

import engine.controller.Controller;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * @author pkp9
 * splash screen super class
 */

abstract class SplashScreen {
    BorderPane pane = new BorderPane();
    Scene myScene;
    private Runnable nextSceneHandler;
    protected Controller controller;

    static final double SCREEN_WIDTH = 750;
    static final double SCREEN_HEIGHT = 600;


    SplashScreen(Controller controller) {
        this.controller = controller;
        myScene = new Scene(pane, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
        pane.setPrefHeight(SCREEN_HEIGHT);
        pane.setPrefWidth(SCREEN_WIDTH);
        this.addElements();
        myScene.setOnKeyPressed(e -> {
                    if (nextSceneHandler != null) {
                        if (e.getCode() == KeyCode.SPACE) {
                            nextSceneHandler.run();
                        }
                    }
                }
        );
    }

    abstract void addElements();
    void clear() {
        pane.getChildren().clear();
    }

    public void setNextSceneHandler (Runnable handler) {
        nextSceneHandler = handler;
    }
    public Scene getMyScene () {
        return myScene;
    }
}