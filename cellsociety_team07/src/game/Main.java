/**
 * The Main class contains functionality for the GUI of the Cell Society project.
 * It initializes the appropriate CA simulation based on parameters from a given
 * XML configuration file and displays the simulation along with buttons/sliders
 * that allow for user control.
 *
 * The labels for these GUI elements are read in from a file, labels.txt, which
 * contains one label on each line for each element in the GUI. It is assumed that
 * these file labels are given in the same order as the elements are added to mainBox,
 * which contains all the buttons, slider, and other user-controlled elements in the window.
 * If the number of labels in labels.txt is not equal to the number of elements in mainBox,
 * Main will throw an error.
 */

/**
 * TODO:
 *  Display a graph of the populations of all of the "kinds" of cells over the time of the simulation
 *      NOTE: an example is described in the predator-prey simulation
 *  Allow users to interact with the simulation dynamically to change the values of its parameters
 *      NOTE: this requires GUI components like sliders or text fields organized in a separate panel
 *  Allow users to interact with the simulation dynamically to create or change a state at a grid location
 *      NOTE: this requires listening to events directly occurring on the GUI component representing the grid
 *  Allow users to run multiple simulations at the same time so they can compare the results side by side (i.e., do not use tabs like a browser).
 *      NOTE: there are many UI decisions to be made here, such as: do you put them all in one window or separate (you control the arrangement or does the user)? do they need to be the same simulation type (makes graphing both easier)? can they be run independently or does pause stop all running simulations? etc., — document your decisions in your README

 */

package game;

import initialize.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;
import xml.XMLParser;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import nodes.Node;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

public class Main extends Application {
    public static final String WINDOW_TITLE = "Cell Society";
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 800;
    public static final double DELAY = 1.0;
    public static final int GRAPH_WINDOW_WIDTH = 500;
    public static final int GRAPH_WINDOW_HEIGHT = 500;

    public static final String FILECHOOSER_TITLE = "Select XML Config File";
    public static final File FILECHOOSER_DEFAULT_DIR = new File(System.getProperty("user.home"));

    public static final double SPEEDSLIDER_MIN = .05;
    public static final double SPEEDSLIDER_MAX = 5;
    public static final double SPEEDSLIDER_VALUE = 1;
    public static final double SLIDERBOX_SPACING = 50;

    public static final String labelsFilepath = "src/labels.txt";
    private ArrayList<String> labels;

    private Button startButton, pauseButton, stopButton, resetButton, stepButton;
    private Slider speedSlider;
    private HBox sliderBox, mainBox;
    private FileChooser fileChooser;
    private HashMap<Paint, Integer> cellTypeCount = new HashMap<>();
    LineChart lineChart;
    private int lineChartStep = 0;

    private String expectedXMLExtension = "xml";
    private String noLabelMessage = "Error: labels.txt File Not Found";
    private String noFileMessage = "File does not exist, please select another file.";
    private String wrongExtensionMessage = "File has incorrect extension, please select a .xml file.";

    private Scene scene, graphScene;
    private Stage stage, graphStage;
    private BorderPane pane, graphPane;
    private TilePane cellContainer;
    private File configFile;

    private Game game;
    private Timeline timeline;
    private Initializer init;
    private XMLParser xmlParser;


    /**
     * Initializes buttons
     */
    public void initButtons() {
        startButton = new Button();
        pauseButton = new Button();
        stopButton = new Button();
        resetButton = new Button();
        stepButton = new Button();
    }

    /**
     * Sets button actions
     */
    public void setButtonActions() {
        startButton.setOnAction(event -> {
            timeline.play();
            updateCellTypeCounts();
            System.out.println(cellTypeCount.entrySet().toString());
        });

        pauseButton.setOnAction(event -> timeline.pause());

        stopButton.setOnAction(event -> timeline.stop());

        resetButton.setOnAction(event -> {
                try {
                    pane.getChildren().remove(cellContainer);
                    initCells();
                    updateCellTypeCounts();
                    System.out.println(cellTypeCount.entrySet().toString());
                } catch (IOException e) {
                }
            });
        stepButton.setOnAction(event -> {
            game.step();
            updateCellTypeCounts();
        });
    }

    /**
     * Initialize speedSlider with parameters
     */
    public void initSlider() {
        speedSlider = new Slider(SPEEDSLIDER_MIN, SPEEDSLIDER_MAX, SPEEDSLIDER_VALUE);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
    }

    /**
     * Set speedSlider action (changing the speed of the animation)
     */
    public void setSliderAction() {
        speedSlider.valueProperty().addListener(
                (observable, oldvalue, newvalue) -> {
                    timeline.pause();
                    updateTimeline(newvalue.doubleValue());
                    timeline.play();
                });
    }

    /**
     * Sets button and slider actions
     */
    public void setActions() {
        setButtonActions();
        setSliderAction();
    }

    /**
     * Initialize sliderBox, which holds speedSlider in the window
     */
    public void initSliderBox() {
        initSlider();
        sliderBox = new HBox(SLIDERBOX_SPACING, speedSlider);
        sliderBox.setAlignment(Pos.CENTER);
    }

    /**
     * Initialize mainBox, which holds buttons and the slider in the window
     */
    public void initMainBox() {
        initButtons();
        initSliderBox();
        mainBox = new HBox();
        mainBox.getChildren().addAll(startButton, pauseButton, stopButton, resetButton, stepButton, sliderBox);
        pane.setTop(mainBox);
    }

    /**
     * Reads in a file containing the button/slider labels and sets
     * them to the appropriate buttons/slider in mainBox
     */
    public void setMainBoxLabels() {
        try {
            File file = new File(labelsFilepath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            labels = new ArrayList<>();
            String st;
            while ((st = br.readLine()) != null) {
                labels.add(st);
            }
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR, noLabelMessage, ButtonType.OK);
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    /**
     * Assigns labels to their respective children in mainBox
     * @throws IllegalArgumentException if the number of lines in labels.txt doesn't
     * match the number of children in mainBox
     */
    public void assignMainBoxLabels() throws IllegalArgumentException {
        setMainBoxLabels();
        if (mainBox.getChildren().size() != labels.size()) {
            String errorMessage = "Error: number of lines in labels.txt must match number of children in mainBox";
            Alert alert = new Alert(AlertType.ERROR, errorMessage, ButtonType.OK);
            alert.showAndWait();
            throw new IllegalArgumentException(errorMessage);
        } else {
            for (int i = 0; i < labels.size(); i++) {
                javafx.scene.Node node = mainBox.getChildren().get(i);
                String text = labels.get(i);
                if(node.getClass().equals(Button.class)) {
                    ((Button) node).setText(text);
                } else if(node.getClass().equals(HBox.class)) {
                    ((HBox) node).getChildren().add(new Text(text));
                }
            }
        }
    }

    /**
     * Initializes mainBox and sets labels
     */
    public void setupMainBox() {
        initMainBox();
        assignMainBoxLabels();
    }

    /**
     * Initializes the FileChooser
     */
    public void initFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setTitle(FILECHOOSER_TITLE);
        fileChooser.setInitialDirectory(FILECHOOSER_DEFAULT_DIR);
    }

    /**
     * Handles errors with the config
     * @param message
     * @param tryAgain
     * @return
     */
    public void handleConfigFileError (String message, boolean tryAgain) {
        Alert alert = new Alert(AlertType.WARNING, message, ButtonType.OK);
        alert.showAndWait();
        if (tryAgain){
            getConfigFile();
        }
    }

    /**
     * Get config file from fileChooser
     * @return
     */
    public void getConfigFile() {
        configFile = fileChooser.showOpenDialog(stage);
        if(configFile == null) {
            String errorMessage = "File not selected. Application closing.";
            handleConfigFileError(errorMessage, false);
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * Checks config filepath for errors and handle them
     */
    public void checkConfigFilepath() {
        //The following is adapted from https://stackoverflow.com/questions/20637865/javafx-2-2-get-selected-file-extension
        Boolean errors[];
        do {
            String fileName = configFile.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, configFile.getName().length());
            Boolean fileNotFound = !configFile.exists();
            Boolean wrongFileExtension = !fileExtension.equals(expectedXMLExtension);
            errors = new Boolean[]{fileNotFound, wrongFileExtension};
            if (wrongFileExtension) {
                handleConfigFileError(wrongExtensionMessage, true);
            }
            if (fileNotFound) {
                handleConfigFileError(noFileMessage, true);
            }
        } while (Arrays.asList(errors).contains(true));
    }

    /**
     * Handle file chooser methods
     */
    public void handleFileChooser() {
        initFileChooser();
        getConfigFile();
        checkConfigFilepath();
    }

    /**
     * Sets the initializer with the given cellContainer and xmlParser
     * @throws IOException
     */
    public void setInitializer() throws IOException {
        cellContainer = new TilePane();
        xmlParser = new XMLParser(new ArrayList<>());

        if(configFile == null) {
            handleFileChooser();
        }

        init = new Initializer(cellContainer, xmlParser, configFile);
    }

    /**
     * Initializes the cell grid
     * @throws IOException
     */
    public void initCells() throws IOException {
        setInitializer();
        File configFile = init.getConfigFile();

        String type = init.getSimType();
        Handle handler = init.getHandle(type);
        handler.setConfigFile(configFile);

        game = init.handleInitialize(handler);
        pane.setCenter(cellContainer);
    }

    public void updateCellTypeCounts() {
        cellTypeCount = new HashMap<>();
        for(Node n : game.myNodes) {
            Paint p = n.getMyCell().getMySquare().getFill();
            int newCount = 1;
            if(cellTypeCount.containsKey(p)) {
                newCount = cellTypeCount.get(p) + 1;
            }
            cellTypeCount.put(p, newCount);
        }
    }

    public void setupLineChart() {
        NumberAxis xAxis = new NumberAxis(0, 5, 5);
        NumberAxis yAxis = new NumberAxis(0, cellContainer.getChildren().size(), 1);
        lineChart = new LineChart(xAxis, yAxis);
        updateLineChart();
        graphPane = new BorderPane();
        graphPane.setTop(lineChart);
        graphScene = new Scene(graphPane, GRAPH_WINDOW_WIDTH, GRAPH_WINDOW_HEIGHT);
        graphStage = new Stage();
        graphStage.setTitle("Title");
        graphStage.setScene(graphScene);
        graphStage.show();
    }

    public void updateLineChart() {
        XYChart.Series series = new XYChart.Series<>();
        if(lineChartStep > 5) {
            //series.getData().remove(0);
        } else {
            lineChartStep++;
        }
        for(Paint p : cellTypeCount.keySet()) {
            series.getData().add(new XYChart.Data(lineChartStep, cellTypeCount.get(p)));
        }
        lineChart.getData().add(series);
    }

    /**
     * Initializes and sets up the stage
     */
    public void setupStage() {
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(WINDOW_TITLE);
        stage.show();
    }

    /**
     * Initializes and sets up the scene
     * @throws IOException
     */
    public void setupScene() throws IOException{
        pane = new BorderPane();
        scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        setupMainBox();
        setActions();
        initCells();
        setupLineChart();
        updateLineChart();
    }

    /**
     * Initializes the timeline with a given delay
     * @param delay The delay (in seconds) between each step of the animation
     */
    public void updateTimeline(double delay) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(delay), event -> game.step()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Sets up the scene, stage, and initializes the timeline
     * @throws IOException
     */
    public void setupTimeline() throws IOException {
        setupScene();
        setupStage();
        updateTimeline(DELAY);
    }

    @Override
    /**
     * Sets up timeline and starts the animation
     */
    public void start (Stage stage) throws IOException{
        setupTimeline();
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}