package FrontEnd;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

/*
    @author xp19
 */


public class UIManager {

    StackPane stackPane;

    public UIManager(Group root, Scene scene) {
        WorkspaceManager workspaceManager = new WorkspaceManager(scene);
        stackPane = workspaceManager.getWorkspace();

        root.getChildren().add(stackPane);
    }
}
