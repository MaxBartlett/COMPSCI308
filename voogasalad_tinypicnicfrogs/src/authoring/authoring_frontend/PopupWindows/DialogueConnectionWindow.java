package authoring.authoring_frontend.PopupWindows;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.Forms.DialogueConnectionForm;
import javafx.scene.control.ScrollPane;

/**
 * DialogueConnectionWindow
 *
 * Creates a popup window that allows the user to create
 * connections between Dialogue nodes
 *
 * @author brookekeene
 */
public class DialogueConnectionWindow extends PopupWindow {

    public DialogueConnectionWindow(GameManager gameManager, int n, boolean d){
        super(gameManager, n, d);

        this.display(myResources.getString("DialogueConnection"));
        this.addContent();
    }

    public void addContent(){
        ScrollPane myPane = new ScrollPane();
        myContent = new DialogueConnectionForm(myManager);
        myPane.setContent(myContent);
        myPane.setPrefSize(size, size);
        myPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myRoot.getChildren().add(myPane);
    }
}
