package authoring.authoring_frontend.FormBoxes;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.json.simple.JSONObject;

import java.util.ResourceBundle;


/**
 * FormBox
 *
 * abstract class that defines how a user inputs
 *
 * @author brookekeene
 */
public abstract class FormBox extends HBox {
    public static final String DEFAULT_RESOURCE = "English";
    public static final int PADDING = 10;
    protected ResourceBundle myResources;
    protected String myKey;

    public FormBox(String label) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE);
        Label myLabel = new Label(label);
        myLabel.setPadding(new Insets(PADDING));
        myKey = label.toLowerCase();
        this.getChildren().add(myLabel);
        this.setPadding(new Insets(PADDING));
    }

    /**
     * defines the control for the FormBox
     */
    public abstract void setContent();

    /**
     * @return JSONObject representing the information given by the user
     */
    public abstract JSONObject getJSONContent();

    /**
     * @return true if the information is valid, false otherwise
     */
    public abstract boolean hasValidEntry();


}
