package authoring.authoring_frontend.FormBoxes;

import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * BoundsBox
 *
 * allows the user to enter information about the
 * bounds of the prototype
 *
 * @author brookekeene
 */
public class BoundsBox extends FormBox {
    private TextBox relX;
    private TextBox relY;
    private TextBox width;
    private TextBox height;

    public BoundsBox(String label) {
        super(label);
    }

    /**
     * creates TextBox elements to contain user input
     * information about relative X and Y and size
     */
    @Override
    public void setContent() {
        VBox myContent = new VBox();
        relX = new TextBox(myResources.getString("relX"));
        relX.setContent();
        relY = new TextBox(myResources.getString("relY"));
        relY.setContent();
        width = new TextBox(myResources.getString("width"));
        width.setContent();
        height = new TextBox(myResources.getString("height"));
        height.setContent();
        myContent.getChildren().addAll(relX, relY, width, height);
        this.getChildren().add(myContent);
    }

    /**
     * @return JSONObject containing the user input information
     */
    @Override
    public JSONObject getJSONContent() {
        JSONObject myObject = new JSONObject();
        myObject.put("relX", relX.getField());
        myObject.put("relY", relY.getField());
        myObject.put("width", width.getField());
        myObject.put("height", height.getField());
        return myObject;
    }

    /**
     * error checking for valid integer entries in all fields
     * @return true if user has input valid integers
     */
    @Override
    public boolean hasValidEntry() {
        ArrayList<TextBox> iterable = new ArrayList(List.of(relX, relY, width, height));
        for(TextBox box: iterable) {
            try {
                Integer.parseInt(box.getField());
            } catch(NumberFormatException e) {
                return false;
            } catch(NullPointerException e) {
                return false;
            }
        }
        return true;
    }
}
