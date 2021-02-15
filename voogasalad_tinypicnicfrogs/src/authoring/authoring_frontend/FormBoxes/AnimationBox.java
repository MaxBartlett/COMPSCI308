package authoring.authoring_frontend.FormBoxes;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.Optional;

/**
 * AnimationBox
 *
 * allows user to choose a file for animation
 *
 * @author brookekeene
 */
public class AnimationBox extends FormBox {
    public static int DEFAULT_SIZE = 100;
    private String fileName;
    private int myRows;
    private int myCols;
    private boolean btnClicked;

    /**
     * Constructor
     */
    public AnimationBox(String label) {
        super(label);
        fileName = null;
        btnClicked = false;
    }

    /**
     * creates FileChooser and Buttons for user to select an image
     * and to set its dimensions
     */
    @Override
    public void setContent() {
        VBox myContent = new VBox();

        FileChooser myFC = new FileChooser();
        myFC.setInitialDirectory(new File(System.getProperty("user.dir") + File.separator + "resources"));
        FileChooser.ExtensionFilter filter =new FileChooser.ExtensionFilter("Image Files","*.bmp", "*.gif", "*.jpeg", "*.png");
        myFC.getExtensionFilters().add(filter);
        myFC.setTitle(myResources.getString("NewFile"));

        ImageView fileIm = new ImageView();

        Button fileBtn = new Button(myResources.getString("NewFile"));
        fileBtn.setOnAction(e -> {
            File file = myFC.showOpenDialog(getScene().getWindow());
            if(file != null) {
                fileName = file.toString();
                String[]arr=fileName.split(File.separator);
                fileName=arr[arr.length-1];
                System.out.println(fileName);
                fileIm.setImage(new Image(file.toURI().toString(), DEFAULT_SIZE, DEFAULT_SIZE, true, false));
            }
        });

        Button spriteBtn= new Button(myResources.getString("setBtn"));
        spriteBtn.setOnAction(event -> {
            btnClicked = true;
            launchDialog();
        });

        myContent.getChildren().addAll(fileBtn, fileIm, spriteBtn);
        this.getChildren().add(myContent);
    }

    /**
     * @return JSONObject storing the key and value of the user input
     */
    @Override
    public JSONObject getJSONContent() {
        JSONObject myObject = new JSONObject();
        myObject.put("path", fileName);
        myObject.put("key", myKey);
        myObject.put("spriteRows",myRows);
        myObject.put("spriteCols",myCols);
        return myObject;
    }

    /**
     * error checking for a valid file format and sprite dimensions set
     * @return true if user has selected a file and set dimensions
     */
    @Override
    public boolean hasValidEntry() {
        return fileName != null && btnClicked;
    }

    /**
     * opens an alert that allows user to input the animation dimensions
     */
    private void launchDialog(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(myResources.getString("setBtn"));
        TextField rowText = new TextField();
        rowText.setPromptText(myResources.getString("spriteRows"));
        TextField colText = new TextField();
        colText.setPromptText(myResources.getString("spriteCols"));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(rowText, colText);
        alert.getDialogPane().setContent(vBox);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            try {
                myRows = Integer.parseInt(rowText.getText());
                myCols = Integer.parseInt(colText.getText());
            } catch (NumberFormatException e) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText(myResources.getString("numErrorHeader"));
                alert1.setContentText(myResources.getString("numErrorBody"));
                alert1.showAndWait();
            }
        }
    }
}
