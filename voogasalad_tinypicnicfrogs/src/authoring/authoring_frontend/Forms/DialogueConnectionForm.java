package authoring.authoring_frontend.Forms;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.FormBoxes.SelectBox;
import authoring.authoring_frontend.FormBoxes.TextBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

/**
 * DialogueConnectionForm
 *
 * Contains all of the FormBox elements and allows the
 * user to enter a prompt and specify parent and child
 * nodes and saves a JSON Object representing the
 * connection
 *
 * @author brookekeene
 */
public class DialogueConnectionForm extends Form {
    private TextBox myPrompt;
    private SelectBox myParent;
    private SelectBox myChild;

    public DialogueConnectionForm(GameManager manager) {
        super(manager);

        this.addAllFields();
    }

    @Override
    public void addAllFields() {
        myPrompt = new TextBox(myResources.getString("Prompt"));
        myPrompt.setContent();

        myParent = new SelectBox(myResources.getString("Parent"));
        myParent.setChoices(myManager.getAllNodes());
        myChild = new SelectBox(myResources.getString("Child"));
        myChild.setChoices(myManager.getAllNodes());

        HBox saveBox = new HBox();
        saveBox.setPadding(new Insets(PADDING));
        saveBox.setAlignment(Pos.CENTER);
        Button saveBtn = new Button(myResources.getString("Save")); // Save Button
        saveBox.getChildren().add(saveBtn);
        saveBtn.setOnAction(e -> saveFunction());

        this.getChildren().addAll(myPrompt, myParent, myChild, saveBox);
    }

    @Override
    public void saveFunction() {
        JSONObject myObject = new JSONObject();

        myObject.put("prompt", myPrompt.getField());
        myObject.put("parent", myParent.getChoice());
        myObject.put("child", myChild.getChoice());

        System.out.println(myObject);

        myManager.addConnection(myObject);

        this.clearFields();
    }

    private void clearFields() {
        myPrompt.clearInput();
        myParent.clearInput();
        myChild.clearInput();
    }
}
