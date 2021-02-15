package authoring.authoring_frontend.PopupWindows;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.ActiveItem;
import authoring.authoring_frontend.Actor;
import authoring.authoring_frontend.ActorManager;
import authoring.authoring_frontend.Forms.EditPrototypeForm;
import authoring.authoring_frontend.Forms.PrototypeForm;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

/**
 * Prototype Window
 *
 * Creates a popup window that allows the user to create a
 * new Actor Prototype
 *
 * @author brookekeene
 */
public class PrototypeEditorWindow extends PopupWindow {
    private ActorManager actorManager;
    String programName;

    /**
     * Constructor
     */
    public PrototypeEditorWindow(GameManager manager, int n, boolean d, ActorManager a, String pName) {
        super(manager, n, d);
        actorManager = a;
        programName = pName;

        this.addContent();
        this.display(myResources.getString("EditPrototype"));
    }

    /**
     * creates the elements needed to get the data for a prototype
     */
    public void addContent() {
        ScrollPane mySP = new ScrollPane();
        mySP.setContent(actorManager.getActorMenu());
        //myContent = new PrototypeForm(myManager, actorManager);
        //mySP.setContent(myContent);
        mySP.setPrefSize(size, size);
        Button nextButton = new Button(myResources.getString("Next"));
        nextButton.setOnAction(event -> {
            Actor activeActor = ActiveItem.getActiveItem(programName);
            if(activeActor != null){
                mySP.setContent(new EditPrototypeForm(myManager, activeActor));
            }
        });
        myRoot.getChildren().addAll(mySP, nextButton);
    }
}

