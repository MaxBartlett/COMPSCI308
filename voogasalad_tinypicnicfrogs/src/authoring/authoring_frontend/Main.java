package authoring.authoring_frontend;

import authoring.authoring_backend.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.backend.Message;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Main
 */
public class Main extends Application {

    public void start(Stage primaryStage) {
        AuthoringView environment = new AuthoringView();
        primaryStage.setTitle(environment.getProjectName());
        primaryStage.setScene(environment.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
