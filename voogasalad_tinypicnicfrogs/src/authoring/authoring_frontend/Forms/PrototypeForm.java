package authoring.authoring_frontend.Forms;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.Actor;
import authoring.authoring_frontend.ActorManager;
import authoring.authoring_frontend.FormBoxes.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * PrototypeForm
 *
 * Creates a VBox that contains all of the FormBox
 * elements and that saves a JSON Object representing
 * the ActorPrototype created
 *
 * @author brookekeene
 */
public class PrototypeForm extends Form {
    private static int SIZE = 500;
    private static int SMALL_FIELD = 150;
    private HashMap<String, List> myForms;
    private List<TitledPane> myPanes;
    private String myType;
    private List<FormBox> myAnimationForms;
    private List<FormBox> myStatisticsForms;
    private List<FormBox> myInteractionForms;
    private List<FormBox> myActivateForms;
    private List<FormBox> myDeactivateForms;
    private TextField prototypeName;
    private CheckBox isPlayer;
    private BoundsBox myBounds;
    private ActorManager actorManager;
    private int cellSize;
    private Accordion root;

    /**
     * Constructor
     */
    public PrototypeForm(GameManager manager, ActorManager a, int size) {
        super(manager);
        actorManager = a;
        cellSize = size;

        myPanes = new ArrayList<>();
        myAnimationForms = new ArrayList<>();
        myStatisticsForms = new ArrayList<>();
        myInteractionForms = new ArrayList<>();
        myActivateForms = new ArrayList<>();
        myDeactivateForms = new ArrayList<>();

        this.addAllFields();
    }

    /**
     * adds all sections of FormBoxes to PrototypeForm
     */
    @Override
    public void addAllFields() {
        root = new Accordion();
        root.setPrefSize(SIZE,SIZE);
//        root.getPanes().addAll(myPanes);

        HBox saveBox = new HBox();
        saveBox.setPrefWidth(SIZE);
        saveBox.setPadding(new Insets(PADDING));
        saveBox.setAlignment(Pos.CENTER);
        Button saveBtn = new Button(myResources.getString("Save")); // Save Button
        saveBox.getChildren().add(saveBtn);
        saveBtn.setOnAction(e -> saveFunction());

        // Name
        makePane(myResources.getString("basic"), addBasicPane());

        // Animations
        makePane(myResources.getString("animations"), addAnimationPane());

        // Bounds
        makePane(myResources.getString("bounds"), addBoundsPane());

        // Statistics
        makePane(myResources.getString("stats"), addStatisticsPane());

        // Messages
        makePane(myResources.getString("messages"), addMessagesPane());

        // Interactions
        makePane(myResources.getString("interactions"), addInteractionsPane());

        this.getChildren().addAll(root, saveBox);
    }

    /**
     * creates an expandable TitledPane containing a VBox
     * @param name title of the pane
     * @param box content of the pane
     */
    private void makePane(String name, VBox box) {
        TitledPane pane = new TitledPane();
        pane.setText(name);
        pane.setContent(box);
        root.getPanes().add(pane);
    }

    /**
     * @return VBox that contains the following fields: Name, isPlayer
     */
    private VBox addBasicPane() {
        VBox basicBox = new VBox();

        Label name = new Label(myResources.getString("name"));
        name.setPadding(new Insets(PADDING));

        prototypeName = new TextField();
        prototypeName.setMaxWidth(SMALL_FIELD);

        isPlayer = new CheckBox();
        isPlayer.setText(myResources.getString("isPlayer"));

        basicBox.getChildren().addAll(name, prototypeName, isPlayer);

        return basicBox;
    }

    /**
     * @return VBox that contains the following fields: AnimationBoxes
     */
    private VBox addAnimationPane() {
        VBox animationBox = new VBox();

        Label animations = new Label(myResources.getString("animations"));
        animations.setPadding(new Insets(PADDING));

        AnimationBox idle = new AnimationBox("Idle");
        AnimationBox top = new AnimationBox("Top");
        AnimationBox bottom = new AnimationBox("Bottom");
        AnimationBox left = new AnimationBox("Left");
        AnimationBox right = new AnimationBox("Right");
        myAnimationForms.add(idle);
        myAnimationForms.add(top);
        myAnimationForms.add(bottom);
        myAnimationForms.add(left);
        myAnimationForms.add(right);
        for(FormBox box:myAnimationForms) {
            box.setContent();
        }

        animationBox.getChildren().addAll(animations, idle, top, bottom, left, right);

        return animationBox;
    }

    /**
     * @return VBox that contains the following fields: Bounds
     */
    private VBox addBoundsPane() {
        VBox boundsBox = new VBox();

        Label bounds = new Label(myResources.getString("bounds"));
        bounds.setPadding(new Insets(PADDING));

        myBounds = new BoundsBox("");
        myBounds.setContent();

        boundsBox.getChildren().addAll(bounds, myBounds);

        return boundsBox;
    }

    /**
     * @return VBox that contains the following fields: Activate and Deactivate Messages
     */
    private VBox addMessagesPane() {
        VBox messageBox = new VBox();

        Label activate = new Label(myResources.getString("activate"));
        Label deactivate = new Label(myResources.getString("deactivate"));
        activate.setPadding(new Insets(PADDING));
        deactivate.setPadding(new Insets(PADDING));

        // Activate Messages
        Button addActBtn = new Button(myResources.getString("AddNew"));
        VBox activateBox = new VBox();
        activateBox.setPadding(new Insets(PADDING));

        addActBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(myResources.getString("messages"));
            dialog.setHeaderText(myResources.getString("CreateNew") + myResources.getString("message"));
            dialog.setContentText(myResources.getString("EnterName") + myResources.getString("message"));
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                MessageBox temp = new MessageBox(result.get(), myManager);
                temp.setContent();
                myActivateForms.add(temp);
                activateBox.getChildren().add(temp);
            }
        });

        // Deactivate Messages
        Button addDeBtn = new Button(myResources.getString("AddNew"));
        VBox deactivateBox = new VBox();
        deactivateBox.setPadding(new Insets(PADDING));

        addDeBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(myResources.getString("messages"));
            dialog.setHeaderText(myResources.getString("CreateNew") + myResources.getString("message"));
            dialog.setContentText(myResources.getString("EnterName") + myResources.getString("message"));
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                MessageBox temp = new MessageBox(result.get(), myManager);
                temp.setContent();
                myDeactivateForms.add(temp);
                deactivateBox.getChildren().add(temp);
            }
        });

        messageBox.getChildren().addAll(activate, activateBox, addActBtn, deactivate, deactivateBox, addDeBtn);

        return messageBox;
    }

    /**
     * @return VBox that contains the following fields: Statistics
     */
    private VBox addStatisticsPane() {
        VBox statsBox = new VBox();

        Label stats = new Label(myResources.getString("stats"));
        stats.setPadding(new Insets(PADDING));

        Button addBtn = new Button(myResources.getString("AddNew"));
        VBox flexibleBox = new VBox();
        flexibleBox.setPadding(new Insets(PADDING));

        addBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(myResources.getString("stats"));
            dialog.setHeaderText(myResources.getString("CreateNew") + myResources.getString("stat"));
            dialog.setContentText(myResources.getString("EnterName") + myResources.getString("stat"));
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                TextBox temp = new TextBox(result.get());
                temp.setContent();
                myStatisticsForms.add(temp);
                flexibleBox.getChildren().add(temp);
            }
        });

        statsBox.getChildren().addAll(stats, flexibleBox, addBtn);

        return statsBox;
    }

    /**
     * @return VBox that contains the following fields: Interactions
     */
    private VBox addInteractionsPane() {
        VBox interBox = new VBox();

        Label interactions = new Label(myResources.getString("interactions"));
        interactions.setPadding(new Insets(PADDING));

        Button addBtn = new Button(myResources.getString("AddNew"));
        VBox flexibleBox = new VBox();
        flexibleBox.setPadding(new Insets(PADDING));

        addBtn.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(myResources.getString("interactions"));
            dialog.setHeaderText(myResources.getString("CreateNew") + myResources.getString("interaction"));
            dialog.setContentText(myResources.getString("EnterName") + myResources.getString("interaction"));
            Optional<String> result = dialog.showAndWait();
            if(result.isPresent()){
                InteractionBox temp = new InteractionBox(result.get(), myManager);
                temp.setContent();
                myInteractionForms.add(temp);
                flexibleBox.getChildren().add(temp);
            }
        });

        interBox.getChildren().addAll(interactions, flexibleBox, addBtn);

        return interBox;
    }

    /**
     * saves all information as a JSON object
     */
    @Override
    public void saveFunction() {
        JSONObject myPrototype = new JSONObject();
        JSONArray myAnimations = new JSONArray();
        JSONArray myStats = new JSONArray();
        JSONArray myActivations = new JSONArray();
        JSONArray myDeactivations = new JSONArray();
        JSONArray myInteractions = new JSONArray();

        if(prototypeName.getText().isEmpty()) {
            invalidDataAlert("name");
            return;
        }
        myPrototype.put("name", prototypeName.getText());

        for(int i = 0; i < myAnimationForms.size(); i++) {
            FormBox tempBox = myAnimationForms.get(i);
            if(tempBox.getJSONContent().get("key").equals("idle")) {
                if(!tempBox.hasValidEntry()) {
                    invalidDataAlert("animations");
                    return;
                }
            }
            myAnimations.add(tempBox.getJSONContent());
        }
        myPrototype.put("animations", myAnimations);

//        if(!myBounds.hasValidEntry()) {
//            invalidDataAlert("bounds");
//            return;
//        }
        myPrototype.put("bounds", myBounds.getJSONContent());

        for(int i = 0; i < myStatisticsForms.size(); i++) {
//            if(!myStatisticsForms.get(i).hasValidEntry()) {
//                invalidDataAlert("statistics");
//                return;
//            }
            myStats.add(myStatisticsForms.get(i).getJSONContent());
        }
        myPrototype.put("stats", myStats);

        myPrototype.put("isPlayer", isPlayer.isSelected());

        //  Activate Messages
        for(int i = 0; i < myActivateForms.size(); i++) {
//            if(!myActivateForms.get(i).hasValidEntry()) {
//                invalidDataAlert("messages");
//                return;
//            }
            myActivations.add(myActivateForms.get(i).getJSONContent());
        }
        myPrototype.put("ActivateMessages", myActivations);


        //  Dectivate Messages
        for(int i = 0; i < myDeactivateForms.size(); i++) {
//            if(!myDectivateForms.get(i).hasValidEntry()) {
//                invalidDataAlert("messages");
//                return;
//            }
            myDeactivations.add(myDeactivateForms.get(i).getJSONContent());
        }
        myPrototype.put("DeactivateMessages", myDeactivations);

        for(int i = 0; i < myInteractionForms.size(); i++) {
            if(!myInteractionForms.get(i).hasValidEntry()) {
                invalidDataAlert("interactions");
                return;
            }
            myInteractions.add(myInteractionForms.get(i).getJSONContent());
            InteractionBox tempBox = (InteractionBox) myInteractionForms.get(i);
            myType = tempBox.getType();
        }
        myPrototype.put("interactions", myInteractions);

        System.out.println(myPrototype); // TESTING
        myManager.createActorPrototype(myPrototype);
        actorManager.addActor(new Actor(myPrototype, cellSize), !myType.equalsIgnoreCase("background"));
        actorManager.setupTabs();

        this.closeWindow();
    }

    /**
     * creates an AlertBox with the appropriate message when the user inputs data incorrectly
     */
    private void invalidDataAlert(String issue) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(myResources.getString("error"));
        errorAlert.setContentText(myResources.getString("errorMessage") + issue);
        errorAlert.showAndWait();
    }
}
