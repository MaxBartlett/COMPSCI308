package authoring.authoring_frontend.FormBoxes;

import authoring.authoring_backend.GameManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * InteractionBox
 *
 * @author brookekeene
 */
public class InteractionBox extends FormBox {
    private VBox myFlexibleContent;
    private SelectBox myType;
    private String selectedType;
    private List<AnimationBox> myAnimations;
    private List<MessageBox> myMessages;
    private List<MoveBox> myMoves;
    private List<String> typeChoices;
    private CheckBox passThrough;
    private SelectBox myDialogue;
    private GameManager myManager;

    /**
     * Constructor
     */
    public InteractionBox(String label, GameManager manager) {
        super(label);
        myManager = manager;
        typeChoices = new ArrayList<>(List.of("Combat", "Dialogue", "Background"));
    }

    /**
     * creates SelectBox for the type of interaction which then triggers
     * the appearance of different fields for the specific type
     */
    @Override
    public void setContent() {
        myMessages = new ArrayList<>();
        myAnimations = new ArrayList<>();
        myMoves = new ArrayList<>();
        myFlexibleContent = new VBox();
        VBox myContent = new VBox();

        // Type
        myType = new SelectBox(myResources.getString("type"));
        myType.setChoices(typeChoices);
        Button typeBtn = new Button(myResources.getString("select"));
        myContent.getChildren().addAll(myType, typeBtn, myFlexibleContent);
        this.getChildren().add(myContent);

        typeBtn.setOnAction(e -> {
            selectedType = myType.getChoice();
            myFlexibleContent.getChildren().clear();
            if(selectedType != null) {
                if(selectedType.equalsIgnoreCase("Combat")) {
                    this.addCombatFields();
                }
                else if(selectedType.equalsIgnoreCase("Dialogue")) {
                    this.addDialogueFields();
                }
                else if(selectedType.equalsIgnoreCase("Background")) {
                    this.addBackgroundFields();
                }
            }
        });
    }

    private void addCombatFields() {
        // Animations
        VBox animationsBox = new VBox();
        Label animations = new Label(myResources.getString("animations"));
        AnimationBox idle = new AnimationBox("idle");
        idle.setContent();
        myAnimations.add(idle);
        animationsBox.getChildren().addAll(animations, idle);

        // Messages
        Label messages = new Label(myResources.getString("messages"));
        Button addMessageBtn = new Button(myResources.getString("AddNew"));
        VBox messagesBox = new VBox();
        messagesBox.setPadding(new Insets(PADDING));
        messagesBox.getChildren().addAll(messages, addMessageBtn);

        addMessageBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(myResources.getString("messages"));
            dialog.setHeaderText(myResources.getString("CreateNew") + myResources.getString("message"));
            dialog.setContentText(myResources.getString("EnterName") + myResources.getString("message"));
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                MessageBox temp = new MessageBox(result.get(), myManager);
                temp.setContent();
                myMessages.add(temp);
                messagesBox.getChildren().add(temp);
            }
        });

        // Moves
        Label moves = new Label(myResources.getString("moves"));
        Button addMoveBtn = new Button(myResources.getString("AddNew"));
        VBox movesBox = new VBox();
        movesBox.setPadding(new Insets(PADDING));
        movesBox.getChildren().addAll(moves, addMoveBtn);

        addMoveBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(myResources.getString("moves"));
            dialog.setHeaderText(myResources.getString("CreateNew") + myResources.getString("move"));
            dialog.setContentText(myResources.getString("EnterName") + myResources.getString("move"));
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                MoveBox temp = new MoveBox(result.get());
                temp.setContent();
                myMoves.add(temp);
                movesBox.getChildren().add(temp);
            }
        });

        myFlexibleContent.getChildren().addAll(animationsBox, messagesBox, movesBox);
    }

    private void addDialogueFields() {
        // Dialogue Root
        myDialogue = new SelectBox(myResources.getString("dialogueRoot"));
        myDialogue.setChoices(myManager.getAllRoots());

        // Animations
        VBox animationsBox = new VBox();
        Label animations = new Label(myResources.getString("animations"));
        AnimationBox idle = new AnimationBox("idle");
        idle.setContent();
        myAnimations.add(idle);
        animationsBox.getChildren().addAll(animations, idle);

        myFlexibleContent.getChildren().addAll(myDialogue, animationsBox);
    }

    private void addBackgroundFields() {
        // canPassThrough
        HBox boolBox = new HBox();
        passThrough = new CheckBox();
        passThrough.setText(myResources.getString("passThrough"));
        boolBox.getChildren().add(passThrough);
        boolBox.setPadding(new Insets(PADDING));

        // Animations
        VBox animationsBox = new VBox();
        Label animations = new Label(myResources.getString("animations"));
        AnimationBox idle = new AnimationBox("idle");
        idle.setContent();
        myAnimations.add(idle);
        animationsBox.getChildren().addAll(animations, idle);

        // Messages
        Label messages = new Label(myResources.getString("messages"));
        Button addMessageBtn = new Button(myResources.getString("AddNew"));
        VBox messagesBox = new VBox();
        messagesBox.setPadding(new Insets(PADDING));
        messagesBox.getChildren().addAll(messages, addMessageBtn);

        addMessageBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(myResources.getString("messages"));
            dialog.setHeaderText(myResources.getString("CreateNew") + myResources.getString("message"));
            dialog.setContentText(myResources.getString("EnterName") + myResources.getString("message"));
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                MessageBox temp = new MessageBox(result.get(), myManager);
                temp.setContent();
                myMessages.add(temp);
                messagesBox.getChildren().add(temp);
            }
        });

        myFlexibleContent.getChildren().addAll(passThrough, animationsBox, messagesBox);
    }


    /**
     * @return JSONObject containing the user input information
     */
    @Override
    public JSONObject getJSONContent() {
        JSONObject myObject = new JSONObject();
        JSONArray myAnimationJSON = new JSONArray();
        JSONArray myMessageJSON = new JSONArray();
        JSONArray myMoveJSON = new JSONArray();

        // Animations
        for(AnimationBox box: myAnimations) {
            myAnimationJSON.add(box.getJSONContent());
        }

        // Messages
        for(MessageBox box:myMessages) {
            myMessageJSON.add(box.getJSONContent());
        }


        myObject.put("name", myKey);
        myObject.put("type", myType.getChoice());
        myObject.put("animations", myAnimationJSON);
        myObject.put("messages", myMessageJSON);

        if(selectedType.equalsIgnoreCase("combat")) {
            // Moves
            for(MoveBox box:myMoves) {
                myMoveJSON.add(box.getJSONContent());

            }
            myObject.put("moves", myMoveJSON);
        }
        else if(selectedType.equalsIgnoreCase("background")) {
            myObject.put("canPassThrough", passThrough.isSelected());
        }
        else if(selectedType.equalsIgnoreCase("dialogue")) {
            myObject.put("root", myDialogue.getChoice());
        }

        return myObject;
    }

    /**
     * error checking for all fields of an interaction
     * @return true if user has input all necessary data
     */
    @Override
    public boolean hasValidEntry() { // TODO: finish
        if(!myType.hasValidEntry()) {
            return false;
        } else if (!validEntries()) {
            return false;
        }
        return true;
    }

    private boolean validEntries() {
        // Animations
        for(AnimationBox box: myAnimations) {
            if(!box.hasValidEntry()) {
                return false;
            }
        }

        // Messages
        for(MessageBox box:myMessages) {
            if(!box.hasValidEntry()) {
                return false;
            }
        }

        // Moves
        for(MoveBox box:myMoves) {
            if(!box.hasValidEntry()) {
                return false;
            }
        }
        return true;
    }

    public String getType() {
        return myType.getChoice();
    }
}
