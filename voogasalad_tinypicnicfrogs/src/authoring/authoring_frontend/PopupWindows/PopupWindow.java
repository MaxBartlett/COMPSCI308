package authoring.authoring_frontend.PopupWindows;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.Forms.Form;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * PopupWindow
 *
 * Creates a popup window that contains content
 * separate from the main AuthoringView
 *
 * @author brookekeene
 */
public abstract class PopupWindow {
    public static final String DEFAULT_RESOURCE = "English";
    public static final String STYLESHEET = "default.css";
    protected ResourceBundle myResources;
    protected GameManager myManager;
    protected Group myRoot;
    protected Scene myScene;
    protected int size;
    protected Form myContent;

    public PopupWindow(GameManager manager, int n, boolean dark) {
        myManager = manager;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE);
        myRoot = new Group();
        size = n;
        myScene = new Scene(myRoot, size, size);
        if(dark) {
            myScene.getStylesheets().add(STYLESHEET);
        }
    }

    /**
     * creates a new Stage for a PopupWindow
     */
    public void display(String label) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(label);

        window.setScene(myScene);
        window.setResizable(false);
        window.show();
    }

    /**
     * specifies the content of the PopupWindow
     */
    public abstract void addContent();
}
