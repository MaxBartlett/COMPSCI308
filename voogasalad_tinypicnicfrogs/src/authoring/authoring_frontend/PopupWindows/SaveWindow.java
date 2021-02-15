package authoring.authoring_frontend.PopupWindows;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.Forms.SaveForm;
import javafx.scene.control.ScrollPane;


/**
 * SaveWindow
 *
 * @author brookekeene
 */
public class SaveWindow extends PopupWindow{

    /**
     * Constructor
     */
    public SaveWindow(GameManager manager, int n, boolean d) {
        super(manager, n, d);

        this.display(myResources.getString("GameInfo"));
        this.addContent();
    }

    /**
     * creates the UI elements needed to save a game
     */
    public void addContent() {
        ScrollPane myPane = new ScrollPane();
        myContent = new SaveForm(myManager);
        myPane.setContent(myContent);
        myPane.setPrefSize(size, size);
        myRoot.getChildren().add(myPane);
    }
}
