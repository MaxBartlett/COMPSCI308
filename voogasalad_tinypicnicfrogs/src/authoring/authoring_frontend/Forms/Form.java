package authoring.authoring_frontend.Forms;

import authoring.authoring_backend.GameManager;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

/**
 * Form
 *
 * Creates a Form that displays fields to take
 * in user input information
 *
 * @author brookekeene
 */
public abstract class Form extends VBox {
    private static final String DEFAULT_RESOURCE = "English";
    protected int PADDING;
    protected GameManager myManager;
    protected ResourceBundle myResources;

    /**
     * Constructor
     */
    public Form(GameManager manager) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE);
        myManager = manager;

        PADDING = Integer.parseInt(myResources.getString("padding"));
        this.setPadding(new Insets(PADDING));
    }

    /**
     * Sets content of the Form
     */
    public abstract void addAllFields();

    /**
     * Saves the content of the Form
     */
    public abstract void saveFunction();

    /**
     * Closes the window containing the Form
     */
    protected void closeWindow() {
        this.getScene().getWindow().hide();
    }
}
