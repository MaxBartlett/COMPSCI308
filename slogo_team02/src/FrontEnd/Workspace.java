
package FrontEnd;

import Backend.CommandManager;
import Backend.Turtle;
import Backend.VariableTracker;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author Xi Pu (xp19)
 *
 * Represents the content inside each individual tab (all UI elements contained in it)
 */

public class Workspace extends BorderPane {

    public static final int PADDING = 10;
    TurtlePlayground area;
    TurtleViewManager turtleViewManager;
    VariableTracker variableTracker;
    TabPane tabPane;

    /**
     * create a new workspace
     */
    public Workspace() {
        CommandManager commandManager = new CommandManager("languages.English");
        variableTracker = commandManager.getMyTracker();
        setUpTurtleDisplayArea();
        Controller controller = new Controller(area, turtleViewManager.turtleList.get(0), commandManager);

        for (Turtle turtle : commandManager.getMyTracker().getTurtleManager().getActiveTurtles()) {
            turtle.setController(controller);
        }

//        commandManager.getMyTracker().getTurtleManager().getActiveTurtles().setController(controller);
        ControlPanelView controlPanelView = new ControlPanelView(this, controller);
        controlPanelView.getRightMenu().getChildren().add(getHelperMenu());
        controlPanelView.setCommandManager(commandManager);
        controlPanelView.setTurtlePlayground(area);
        for (TurtleView view : turtleViewManager) {
            view.setTurtleStatusPane(controlPanelView.getTurtleStatus());
        }
//        turtleViewManager.setTurtleStatusTitlePane(controlPanelView.getTurtleStatus());

        CommandInputHandler commandInputHandler = controlPanelView.getCommandInputHandler();
        commandInputHandler.setCommandManager(commandManager);
        commandInputHandler.setVariableHistory(controlPanelView.definedVariables);
        commandInputHandler.setCommandHistory(controlPanelView.commandHistory);
        commandInputHandler.setUserDefinedCommands(controlPanelView.userDefinedCommands);
    }

    public Workspace(TabPane tabPane) {
        this();
        this.tabPane = tabPane;
    }

    // initialize the playground in the border pane
    // TODO: do the initialization inside the turtle class
    private void setUpTurtleDisplayArea() {
//        turtleView = new TurtleView();
        turtleViewManager = new TurtleViewManager(variableTracker);

        area = new TurtlePlayground(turtleViewManager);

        this.setCenter(area);
    }

    private TitledPane getHelperMenu() {
        VBox allCommands = new VBox();
        ScrollPane sp = new ScrollPane();
        sp.setContent(allCommands);
        for (String command : CommandManager.getCommands().keySet()) {
            allCommands.getChildren().add(UIFactory.createText(command + ": " + CommandManager.getCommands().get(command).getDescription()));
        }
        TitledPane helperMenu = new TitledPane("User Guide", sp);
        sp.setPadding(new Insets(PADDING));
        helperMenu.setExpanded(false);
        return helperMenu;
    }

}
