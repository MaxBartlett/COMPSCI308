package engine.frontend.game_engine_UI.OverWorld;

import javafx.scene.layout.Pane;

public interface OverWorldViewAPI {
    void setCamera (Camera camera);
    Camera getCamera ();
    Pane getView ();
    void moveCamera();
}
