package authoring.authoring_frontend.Forms;

import authoring.authoring_backend.GameManager;
import authoring.authoring_backend.SaveException;
import authoring.authoring_frontend.Actor;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * SaveForm
 *
 * @author brookekeene
 */
public class EditPrototypeForm extends Form {
    private static final int SIZE = 300;
    private static final int FIELD_SIZE = 250;
    private TextField gameName;
    private TextArea gameDescript;
    private String gamePath;
    private Actor thisActor;

    /**
     * Constructor
     */
    public EditPrototypeForm(GameManager manager, Actor a) {
        super(manager);
        gamePath = "";
        thisActor = a;

        this.setMaxSize(SIZE, SIZE);
        this.addAllFields();
    }

    /**
     * add UI elements for user to input game information
     */
    @Override
    public void addAllFields() {
        // Labels
        Label name = new Label(myResources.getString("name"));
        Label description = new Label(myResources.getString("description"));
        Label path = new Label(myResources.getString("gamePath"));

        // Name
        gameName = new TextField();
        gameName.setMaxWidth(FIELD_SIZE);
        this.getChildren().addAll(name, gameName);

        // Description
        gameDescript = new TextArea();
        gameDescript.setWrapText(true);
        gameDescript.setPrefSize(FIELD_SIZE, FIELD_SIZE);
        this.getChildren().addAll(description, gameDescript);

        // Path
        Button addPathBtn = new Button(myResources.getString("addPath"));
        addPathBtn.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle(myResources.getString("choosePath"));
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            if (selectedDirectory != null) {
                gamePath = selectedDirectory.getPath();
            }
        });

        this.getChildren().addAll(path, addPathBtn);

        // Save Button
        Button saveBtn = new Button(myResources.getString("Save"));
        saveBtn.setOnAction(e -> saveFunction());
        this.getChildren().add(saveBtn);
    }

    /**
     * creates a GameData object and calls the GameManager.saveGame method
     */
    @Override
    public void saveFunction () { //TODO: error check
        String title = gameName.getText();
        String description = gameDescript.getText();

        List<String> arr= Arrays.asList(gamePath.split(File.separator)); // Regex for non-Mac "\\\\"));
        int index=arr.indexOf("resources");
        String path=".";
        for(int i=index;i<arr.size();i+=1){path+="/"+arr.get(i);}

        try {
            myManager.saveGame(title,description,path);
        } catch (SaveException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(myResources.getString("error"));
            alert.getDialogPane().setContent(new VBox(new Text(e.getMessage())));
            alert.showAndWait();
        }
    }


}
