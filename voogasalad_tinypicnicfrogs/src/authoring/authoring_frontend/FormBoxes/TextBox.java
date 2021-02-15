package authoring.authoring_frontend.FormBoxes;

import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

/**
 * TextBox
 *
 * allows user to input text
 *
 * @author brookekeene
 */
public class TextBox extends FormBox {
    private TextField myText;

    public TextBox(String label) {
        super(label);
    }

    /**
     * creates TextField for user to enter text into
     */
    @Override
    public void setContent() {
        myText = new TextField();
        this.getChildren().add(myText);
    }

    /**
     * @return JSONObject storing the key and value of the user input
     */
    @Override
    public JSONObject getJSONContent() {
        JSONObject myObject = new JSONObject();
        myObject.put("value", myText.getText());
        myObject.put("key", myKey);
        return myObject;
    }

    /**
     * @return String representing the user input
     */
    public String getField() {
        return myText.getText();
    }

    /**
     * error checking for valid text entry
     * @return true if the user has input text
     */
    @Override
    public boolean hasValidEntry() {
        return !myText.getText().isEmpty();
    }

    /**
     * clears the TextField
     */
    public void clearInput() {
        myText.clear();
    }
}
