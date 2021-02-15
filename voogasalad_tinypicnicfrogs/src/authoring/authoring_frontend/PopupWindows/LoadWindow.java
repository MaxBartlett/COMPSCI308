package authoring.authoring_frontend.PopupWindows;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.ActorManager;
import authoring.authoring_frontend.Forms.LoadForm;
import authoring.authoring_frontend.MapManager;
import javafx.scene.control.ScrollPane;

/**
 * LoadWindow
 *
 * @author brookekeene
 */
public class LoadWindow extends PopupWindow{
    private ActorManager actorManager;
    private MapManager mapManager;

    /**
     * Constructor
     */
    public LoadWindow(GameManager manager, int n, boolean d, ActorManager a, MapManager m) {
        super(manager, n, d);
        actorManager = a;
        mapManager = m;

        this.display(myResources.getString("GameInfo"));
        this.addContent();
    }

    /**
     * creates the UI elements needed to save a game
     */
    public void addContent() {
        ScrollPane myPane = new ScrollPane();
        myContent = new LoadForm(myManager, actorManager, mapManager);
        myPane.setContent(myContent);
        myPane.setPrefSize(size, size);
        myRoot.getChildren().add(myPane);
    }
}
