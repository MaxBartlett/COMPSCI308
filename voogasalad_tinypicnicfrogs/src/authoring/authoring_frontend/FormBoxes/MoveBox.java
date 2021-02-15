package authoring.authoring_frontend.FormBoxes;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MoveBox
 *
 * allows user to input all the information
 * related to a Move under an Interaction
 *
 * @author brookekeene
 */
public class MoveBox extends FormBox {
    private TextBox targetStat;
    private TextBox actorNum;
    private SelectBox actorType;
    private TextBox targetVal;
    private SelectBox targetType;
    private List<AnimationBox> myAnimations;

    public MoveBox(String label) {
        super(label);
        myAnimations = new ArrayList<>();
    }

    /**
     * creates a VBox containing all the fields for a Move
     * including its target statistic, actor number, actor type
     * target value, target type, and animations
     */
    @Override
    public void setContent() { //TODO: remove hardcoded strings
        VBox myContent = new VBox();

        // Target Statistic
        targetStat = new TextBox("Target Statistic");
        targetStat.setContent();

        // Target Actor Number
        actorNum = new TextBox("Target Actor Number");
        actorNum.setContent();

        // Target Actor Type
        actorType = new SelectBox("Target Actor Type");
        actorType.setChoices(new ArrayList(List.of("friend", "enemy")));
        actorType.setContent();

        // Target Value
        targetVal = new TextBox("Target Value");
        targetVal.setContent();

        // Target Type
        targetType = new SelectBox("Target Type");
        targetType.setChoices(new ArrayList(List.of("constant", "percent")));
        targetType.setContent();

        // Animations
        Label animations = new Label(myResources.getString("animations"));
        AnimationBox idle = new AnimationBox("idle");
        idle.setContent();
        myAnimations.add(idle);

        myContent.getChildren().addAll(targetStat, actorNum, actorType, targetVal, targetType, animations, idle);
        this.getChildren().add(myContent);
    }

    /**
     * @return JSONObject storing the keys: name, targetStat,
     * targetActorNumber, targetActor type, targetValue, targetType,
     * animations
     */
    @Override
    public JSONObject getJSONContent() {
        JSONObject myObject = new JSONObject();
        JSONArray moveAnimations = new JSONArray();

        for (AnimationBox box : myAnimations) {
            moveAnimations.add(box.getJSONContent());
        }

        myObject.put("name", myKey);
        myObject.put("targetStat", targetStat.getField());
        myObject.put("targetActorNumber", actorNum.getField());
        myObject.put("targetActorType", actorType.getChoice());
        myObject.put("targetValue", targetVal.getField());
        myObject.put("targetType", targetType.getChoice());
        myObject.put("animations", moveAnimations);
        return myObject;
    }

    /**
     * error checking for all fields of a move
     * @return true if user has input all necessary data
     */
    @Override
    public boolean hasValidEntry() {
        return targetStat.hasValidEntry() &&
                actorNum.hasValidEntry() &&
                actorType.hasValidEntry() &&
                targetVal.hasValidEntry() &&
                targetType.hasValidEntry() &&
                validAnimations();
    }

    private boolean validAnimations() {
        for (AnimationBox box : myAnimations) {
            if(!box.hasValidEntry()) {
                return false;
            }
        }
        return true;
    }
}
