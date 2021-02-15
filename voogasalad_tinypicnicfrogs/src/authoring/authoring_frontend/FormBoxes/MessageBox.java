package authoring.authoring_frontend.FormBoxes;

import authoring.authoring_backend.GameManager;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

/**
 * TextBox
 *
 * allows user to specify Messages for Interactions
 *
 * @author brookekeene
 */
public class MessageBox extends FormBox {
    private TextField myBody;
    private GameManager myManager;

    public MessageBox(String label, GameManager manager) {
        super(label);
        myManager = manager;
    }

    /**
     * creates a TextField for user input message and
     * a ChoiceBox containing the defined message types
     */
    @Override
    public void setContent() {
        myBody = new TextField();
        myBody.setPromptText(myResources.getString("messagePrompt"));

        this.getChildren().add(myBody);
    }

    /**
     * @return JSONObject storing the key, the user input,
     * and the messageKey, the type of message chosen
     */
    @Override
    public JSONObject getJSONContent() {
        JSONObject myObject = new JSONObject();
        myObject.put("key", myKey);
        myObject.put("messageKey", myBody.getText());
        myManager.createMessage(myKey, myBody.getText());
        return myObject;
    }

    /**
     * error checking for a all fields of a key and messageKey pair
     * @return true if user has input a key and selected a message
     */
    @Override
    public boolean hasValidEntry() {
        return !(myBody.getText().isEmpty());
    }
}
