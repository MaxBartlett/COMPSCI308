package FrontEnd;

import Backend.CommandManager;
import Backend.Turtle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the UI view for all the settings like workspace setting,
 * defined variables and commands, command history that appear in the right side of
 * @author xp19, mmb70
 **/
public class ControlPanelView {

    public static final String DEFAULT_HEADING = "90";
    public static final String DEFAULT_POSITION = "0,0";
    public static final String DEFAULT_ID = "1";
    private static final String WORKSPACE_SETTING_TITLE = "Workspace Setting";
    private static final String DEFINED_COMMANDS_TITLE = "Defined Commands";
    private static final String DEFINED_VARIABLES_TITLE = "Defined Variables";
    private static final String COMMAND_HISTORY_TITLE = "Command History";
    private static final String COMMAND_OUTPUT_TITLE = "Command Output";
    private static final String TURTLE_STATUS_TITLE = "Turtle Status";
    private static final double VERTICAL_SPACING = 10.0;
    private static final String IMAGE_FILE_EXTENSION = "*.png";
    private static final String LOGO_FILE_EXTENSION = "*.logo";
    VBox vBox;
    Workspace workspace;
    TitledPane workspaceSetting;
    TitledPane commandHistory;
    TitledPane commandOutput;
    TitledPane userDefinedCommands;
    TitledPane definedVariables;
    TitledPane turtleStatus;
    TitledPane turtleAction;
    TitledPane colorIndexes;
    Controller controller;
    CommandInputHandler commandInputHandler;
    CommandManager commandManager;
    TurtlePlayground turtlePlayground;
    private Image turtleImage = new Image(this.getClass().getClassLoader().getResourceAsStream("turtle_green.png"));
    private Image alTurtleImage = new Image(this.getClass().getClassLoader().getResourceAsStream("turtle_dark_green.png"));

    public ControlPanelView(Workspace workspace, Controller controller) {
        this.workspace = workspace;
        this.controller = controller;
        //set up UI
        setUpTextInputArea(workspace);// add text input field
        setUpWorkspaceSetting();
        workspaceSetting.setExpanded(false);
        commandHistory = setUpScrollingTitlePane(COMMAND_HISTORY_TITLE);
        setUpTurtleStatus();
        setUpTurtleAction();
        setUpColorIndexes();
        userDefinedCommands = setUpScrollingTitlePane(DEFINED_COMMANDS_TITLE);
        definedVariables = new TitledPane(DEFINED_VARIABLES_TITLE, new VBox());
        definedVariables.setExpanded(false);
        commandOutput = setUpScrollingTitlePane(COMMAND_OUTPUT_TITLE);
        vBox = new VBox(workspaceSetting, commandHistory, commandOutput, userDefinedCommands, definedVariables, turtleStatus, turtleAction, colorIndexes);
        workspace.setRight(vBox);
    }

    public CommandInputHandler getCommandInputHandler() {
        return commandInputHandler;
    }

    private void setUpWorkspaceSetting() {
        // add bg color picker
        ColorPicker bgColorPicker = new ColorPicker();
        bgColorPicker.setOnAction(event -> {
            controller.setTurtleDisplayAreaColor(bgColorPicker.getValue());
        });
        HBox bgColorBox = UIFactory.createInputFieldWithLabel("Background color: ", bgColorPicker);

        // add pen color picker
        ColorPicker penColorPicker = new ColorPicker(Color.BLACK);
        penColorPicker.setOnAction(event -> {
            controller.setPenColor(penColorPicker.getValue());
        });

        HBox penColorBox = UIFactory.createInputFieldWithLabel("Pen's color: ", penColorPicker);

        // add pen down button
        HBox upDown = addPenDownButton();
        upDown.setAlignment(Pos.CENTER_RIGHT);

        // add pen thickness
        TextField textField = new TextField(String.valueOf(TurtlePlayground.INIT_STROKE_WIDTH));
        textField.setOnAction(event -> {
            controller.setPenThickness(Double.parseDouble(textField.getText()));
        });
        HBox penThicknessBox = UIFactory.createInputFieldWithLabel("Pen Thickness: ", textField);

        // add turtle image setter
        Button button = UIFactory.createButton("Choose..", event -> {
            FileChooser chooser = UIFactory.createFileChooser(IMAGE_FILE_EXTENSION);
            File file = chooser.showOpenDialog(null);
            if (file != null) {
                controller.setTurtleImage(file.getName());
            }
        });
        HBox imageSetter = UIFactory.createInputFieldWithLabel("Change turtle image: ", button);

        // add language setting
        HBox languageSetter = UIFactory.createInputFieldWithLabel("Language: ", addLanguageComboBox());

        // put everything inside a vbox
        VBox setting = new VBox(bgColorBox, penColorBox, upDown, penThicknessBox, imageSetter, languageSetter);
        setting.setSpacing(VERTICAL_SPACING);
        workspaceSetting = new TitledPane(WORKSPACE_SETTING_TITLE, setting);
    }

    private HBox addPenDownButton() {
        final ToggleGroup group = new ToggleGroup();

        ToggleButton tb2 = new ToggleButton("Down");
        tb2.setToggleGroup(group);
        tb2.setSelected(true);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                controller.togglePenDown();
            }
        });
        return new HBox(tb2);
    }

    private ComboBox addLanguageComboBox() {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "English", "Chinese", "French", "German",
                        "Italian", "Portuguese", "Russian", "Spanish", "Urdu"
                );
        ComboBox comboBox = new ComboBox(options);
        comboBox.getSelectionModel().selectFirst();
        comboBox.setOnAction(event -> {
            String s = comboBox.getSelectionModel().getSelectedItem().toString();
            commandInputHandler.setLanguage(s);
        });
        return comboBox;
    }

    // add text input field and related buttons
    private void setUpTextInputArea(Workspace workspace) {
        commandInputHandler = new CommandInputHandler(controller);
        Button runButton = UIFactory.createButton("Run", event -> {
            run(commandInputHandler.getText());
        });
        Button clearHistoryButton = UIFactory.createButton("Clear History", event -> clearHistory());
        Button newTabButton = UIFactory.createButton("New Tab", event -> {
            addNewTab();
        });

        Button loadButton = UIFactory.createButton("Load", event -> {
            FileChooser chooser = UIFactory.createFileChooser(LOGO_FILE_EXTENSION);
            File file = chooser.showOpenDialog(null);
            if (file != null) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String input = "";
                    String line;
                    while ((line = br.readLine()) != null) {
                        input += line;
                        input += "\n";
//                        controller.commandManager.execute(line);
                    }
                    controller.commandManager.execute(input);
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "File not found.", ButtonType.OK);
                    alert.showAndWait();
                    e.printStackTrace();
                }
            }
        });

        Button saveButton = UIFactory.createButton("Save", event -> {
            FileChooser chooser = UIFactory.createFileChooser(LOGO_FILE_EXTENSION);
            File file = chooser.showSaveDialog(null);
            FileOutputStream fos;
            BufferedWriter bw;
            try {
                fos = new FileOutputStream(file);
                bw = new BufferedWriter(new OutputStreamWriter(fos));
                ObservableList commandHistoryNodes = ((VBox) ((ScrollPane) commandHistory.getContent()).getContent()).getChildren();
                for (Object node : commandHistoryNodes) {
                    bw.write(((Text) node).getText());
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "File not found.", ButtonType.OK);
                alert.showAndWait();
                e.printStackTrace();
            }
        });

        Button addNewTurtleButton = UIFactory.createButton("New Turtle", event -> {
            Point2D position = UIFactory.createDialogBox();
            if (position != null) {
                TurtleView newTurtle = controller.addNewTurtle(position);
                controller.getTurtleManager().createTurtle(newTurtle.getId(), new Controller(turtlePlayground, newTurtle, commandManager));
                controller.getTurtleManager().getTurtleByID(newTurtle.getId()).setXY(position.getX(), position.getY());
                List<Turtle> list = controller.getTurtleManager().getActiveTurtles();
                List<Integer> activeList = new ArrayList<>();
                for (Turtle t : list) {
                    activeList.add(t.getID());
                }
                activeList.add(newTurtle.getId());
                controller.getTurtleManager().setActiveTurtlesByID(activeList);
            }
        });

        HBox buttonsGroup = new HBox(runButton, clearHistoryButton, newTabButton, loadButton, saveButton, addNewTurtleButton);
        VBox textInput = new VBox(buttonsGroup, commandInputHandler);
        workspace.setBottom(textInput);
    }

    private TitledPane setUpScrollingTitlePane(String title) {
        VBox allCommands = new VBox();
        ScrollPane sp = new ScrollPane();
        sp.setContent(allCommands);
        TitledPane titledPane = new TitledPane(title, sp);
        sp.setPadding(new Insets(Workspace.PADDING));
        titledPane.setExpanded(false);
        return titledPane;
    }

    private void setUpTurtleStatus() {
        turtleStatus = new TitledPane(TURTLE_STATUS_TITLE, UIFactory.createTurtleStatusVBox());
        turtleStatus.setExpanded(false);
    }

    private void setUpColorIndexes() {

        HBox red = new HBox(new Label("Red: 123"));
        HBox blue = new HBox(new Label("Blue: 456"));
        HBox yellow = new HBox(new Label("Yellow: 789"));
        HBox black = new HBox(new Label("Black: 666"));
        ImageView dimage = new ImageView(turtleImage);
        dimage.setFitHeight(30);
        dimage.setFitWidth(30);
        HBox defaultImage = new HBox(new Label("111"), dimage);

        ImageView aimage = new ImageView(alTurtleImage);
        aimage.setFitHeight(30);
        aimage.setFitWidth(30);
        HBox alterImage = new HBox(new Label("222"), aimage);

        VBox vBox = new VBox(red, blue, yellow, black, defaultImage, alterImage);
        colorIndexes = new TitledPane("UI Indexes", vBox);

    }

    private void setUpTurtleAction() {
        Button forward = new Button("Forward");
        TextField fdText = new TextField("50.0");
        forward.setOnAction(event -> {
            commandManager.execute("fd " + fdText.getText());
        });
        HBox fd = new HBox(forward, fdText);
        Button back = new Button("Back");
        TextField backText = new TextField("50.0");
        back.setOnAction(event -> {
            commandManager.execute("bk " + backText.getText());
        });
        HBox bk = new HBox(back, backText);
        Button left = new Button("Left");
        TextField leftText = new TextField("90.0");
        left.setOnAction(event -> {
            commandManager.execute("left " + leftText.getText());
        });
        HBox lft = new HBox(left, leftText);
        Button right = new Button("Right");
        TextField rightText = new TextField("90.0");
        right.setOnAction(event -> {
            commandManager.execute("right " + rightText.getText());
        });
        HBox rght = new HBox(right, rightText);
        VBox xbox = new VBox(fd, bk, lft, rght);
        turtleAction = new TitledPane("Turtle Action", xbox);
        turtleAction.setExpanded(false);
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void setTurtlePlayground(TurtlePlayground turtlePlayground) {
        this.turtlePlayground = turtlePlayground;
    }

    private void clearHistory() {
        VBox historyBox = (VBox) ((ScrollPane) commandHistory.getContent()).getContent();
        VBox outputBox = (VBox) ((ScrollPane) commandOutput.getContent()).getContent();
        historyBox.getChildren().clear();
        outputBox.getChildren().clear();
    }

    public void run(String input) {
        String output = commandInputHandler.run(input);
        VBox historyBox = (VBox) ((ScrollPane) commandHistory.getContent()).getContent();
        VBox outputBox = (VBox) ((ScrollPane) commandOutput.getContent()).getContent();
        historyBox.getChildren().add(UIFactory.createText(input));
        outputBox.getChildren().add(UIFactory.createText(output));
    }

    private void addNewTab() {
        Tab newTab = new Tab("New Tab");
        newTab.setContent(new Workspace(workspace.tabPane));
        workspace.tabPane.getTabs().add(newTab);
    }

    public VBox getRightMenu() {
        return vBox;
    }

    public TitledPane getTurtleStatus() {
        return turtleStatus;
    }
}
