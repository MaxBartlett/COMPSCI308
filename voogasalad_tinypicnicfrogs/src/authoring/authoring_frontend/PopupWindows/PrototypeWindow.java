package authoring.authoring_frontend.PopupWindows;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.ActorManager;
import authoring.authoring_frontend.Forms.PrototypeForm;
import authoring.authoring_frontend.MapManager;
import javafx.scene.control.ScrollPane;

/**
 * Prototype Window
 *
 * Creates a popup window that allows the user to create a
 * new Actor Prototype
 *
 * @author brookekeene
 */
public class PrototypeWindow extends PopupWindow {
    private ActorManager actorManager;
    private MapManager mapManager;

    /**
     * Constructor
     */
    public PrototypeWindow(GameManager manager, int n, boolean d, ActorManager a, MapManager m) {
        super(manager, n, d);
        actorManager = a;
        mapManager = m;

        this.addContent();
        this.display(myResources.getString("NewPrototype"));
    }

    /**
     * creates the elements needed to get the data for a prototype
     */
    public void addContent() {
        ScrollPane mySP = new ScrollPane();
        myContent = new PrototypeForm(myManager, actorManager, mapManager.getCellSize());
        mySP.setContent(myContent);
        mySP.setPrefSize(size, size);
        myRoot.getChildren().add(mySP);
    }
}

