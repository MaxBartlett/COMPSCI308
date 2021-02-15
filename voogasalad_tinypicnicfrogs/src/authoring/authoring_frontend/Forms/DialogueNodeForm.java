package authoring.authoring_frontend.Forms;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.FormBoxes.TextBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

/**
 * DialogueNodeForm
 *
 * Contains all of the FormBox elements and allows the
 * user to enter a name and saves a JSON Object representing
 * a Dialogue node
 *
 * @author brookekeene
 */
public class DialogueNodeForm extends Form {
    private TextBox myName;
    private TextBox myText;
    private CheckBox isRoot;

    public DialogueNodeForm(GameManager manager){
        super(manager);

        this.addAllFields();
    }

    @Override
    public void addAllFields(){
        myName = new TextBox(myResources.getString("name"));
        myName.setContent();
        myText = new TextBox(myResources.getString("text"));
        myText.setContent();

        HBox booleanBox = new HBox();
        isRoot = new CheckBox();
        isRoot.setText(myResources.getString("isRoot"));
        booleanBox.getChildren().add(isRoot);
        booleanBox.setPadding(new Insets(PADDING));

        HBox saveBox = new HBox();
        saveBox.setPadding(new Insets(PADDING));
        saveBox.setAlignment(Pos.CENTER);
        Button saveBtn = new Button(myResources.getString("Save")); // Save Button
        saveBox.getChildren().add(saveBtn);
        saveBtn.setOnAction(e -> saveFunction());

        this.getChildren().addAll(myName, myText, booleanBox, saveBtn);
    }

    @Override
    public void saveFunction() {
        JSONObject myObject = new JSONObject();
        myObject.put("name", myName.getField());
        myObject.put("text", myText.getField());

        if(isRoot.isSelected()) {
            myManager.addRoot(myObject);
        }
        else {
            myManager.addNode(myObject);
        }

        this.clearFields();
    }

    private void clearFields() {
        myName.clearInput();
        myText.clearInput();
        isRoot.setSelected(false);
    }
}
