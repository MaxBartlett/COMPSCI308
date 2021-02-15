package authoring.authoring_frontend.PopupWindows;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.Forms.DialogueNodeForm;
import javafx.scene.control.ScrollPane;

/**
 * DialogueNodeWindow
 *
 * Creates a popup window that allows the user to create a
 * new Dialogue node with a name and text
 *
 * @author janice
 * @author brookekeene
 */

public class DialogueNodeWindow extends PopupWindow {

    public DialogueNodeWindow(GameManager gameManager, int n, boolean d){
        super(gameManager, n, d);

        this.display(myResources.getString("DialogueNode"));
        this.addContent();
    }

    public void addContent(){
        ScrollPane myPane = new ScrollPane();
        myContent = new DialogueNodeForm(myManager);
        myPane.setContent(myContent);
        myPane.setPrefSize(size, size);
        myPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        myRoot.getChildren().add(myPane);
    }

}
