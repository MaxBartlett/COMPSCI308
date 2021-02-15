package FrontEnd;

import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.File;
import java.util.Optional;

/*
    A utility class for creating JavaFX UI elements including HBox, Button and FileChooser
    @author xp19
 */

public class UIFactory {

    public static HBox createInputFieldWithLabel(String label, Node node) {
        HBox hBox = new HBox(new Label(label), node);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        return hBox;
    }

    public static Button createButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        return button;
    }

    public static Text createText(String text) {
        Text ret = new Text(text);
        ret.setWrappingWidth(250);
        return ret;
    }

    public static HBox createTextLabelWithValue(String label, String value) {
        Text lab = new Text(label);
        Text val = new Text(value);
        return new HBox(lab, val);
    }

    public static HBox createTextFieldWithLabel(String label, String value, EventHandler<ActionEvent> handler) {
        Text lab = new Text(label);
        Button textField = new Button();
        textField.setText(value);
        textField.setOnAction(handler);
        HBox ret = new HBox(lab, textField);
        ret.setAlignment(Pos.CENTER);
        return ret;
    }

    public static HBox createTextFieldWithLabel(String label, String value, ObservableMap<String, Object> map) {
        Text lab = new Text(label);
        TextField textField = new TextField();
        textField.setText(value);
        textField.setOnAction(event -> {
            map.put(label, textField.getText());
        });
        HBox ret = new HBox(lab, textField);
        ret.setAlignment(Pos.CENTER);
        return ret;
    }

    // create a file chooser which starts from the project's root directory
    public static FileChooser createFileChooser(String extension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Choose a " + extension + " file", extension));
        return fileChooser;
    }

    public static VBox createTurtleStatusVBox() {
        HBox ID = UIFactory.createTextLabelWithValue("ID: ", ControlPanelView.DEFAULT_ID);
        HBox position = UIFactory.createTextLabelWithValue("Position: ", ControlPanelView.DEFAULT_POSITION);
        HBox heading = UIFactory.createTextLabelWithValue("Heading: ", ControlPanelView.DEFAULT_HEADING);
        return new VBox(ID, position, heading);
    }

    public static VBox createTurtleStatusVBox(int id, double x, double y, double heading) {
        HBox ID = UIFactory.createTextLabelWithValue("ID: ", String.valueOf(id));
        HBox position = UIFactory.createTextLabelWithValue("Position: ", String.format("(%s, %s)", String.valueOf(x), String.valueOf(y)));
        HBox headingBox = UIFactory.createTextLabelWithValue("Heading: ", String.valueOf(heading));
        return new VBox(ID, position, headingBox);
    }

    public static Point2D createDialogBox() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New Turtle Position");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField from = new TextField();
        from.setPromptText("X Position");
        TextField to = new TextField();
        to.setPromptText("Y Position");

        gridPane.add(from, 0, 0);
//        gridPane.add(new Label("To:"), 1, 0);
        gridPane.add(to, 2, 0);

        dialog.getDialogPane().setContent(gridPane);

        // Request focus on the username field by default.
        Platform.runLater(() -> from.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(from.getText(), to.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        final Point2D[] point2D = new Point2D[1];
        result.ifPresent(pair -> {
            point2D[0] = new Point2D(Double.parseDouble(pair.getKey()), Double.parseDouble(pair.getValue()));
        });
        return point2D[0];
    }
}
