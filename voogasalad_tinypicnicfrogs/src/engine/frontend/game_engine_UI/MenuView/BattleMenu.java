package engine.frontend.game_engine_UI.MenuView;

import engine.backend.Commands.Command;
import engine.backend.ServiceLocator;
import engine.backend.gameevent.GameMenuEvent;
import engine.backend.gameevent.InputSource;
import engine.controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BattleMenu class that provides the player the options for battle
 *
 * @author Duy Trieu (dvt5)
 */
public class BattleMenu extends HBox {
    protected BorderPane pane;
    protected List<Command> commandList;
    protected List<Command> activeCommands;
    protected ListView<String> listView;
    Map<String, Command> map = new HashMap<>();
    protected Controller myController;

    /**
     * @param controller Controller that will get the appropriate information
     */
    public BattleMenu (Controller controller) {
        this.myController = controller;
        pane = new BorderPane();
        activeCommands = new ArrayList<>();
        addListView();
        this.getChildren().add(listView);

    }
    /**
     * build the listview with all the commands
     */
    private void addListView () {
        listView = new ListView<>();
        commandList = myController.getAllCommand();
        List<String> nameList = new ArrayList<>();
        for (Command command:commandList) {
            nameList.add(command.getName());
            map.put(command.getName(), command);
        }
        ObservableList<String> items = FXCollections.observableArrayList (nameList);
        //listView.setPrefSize(100, 50);
        //listView.setOrientation(Orientation.HORIZONTAL);
        listView.setItems(items);
    }
    /**
     * run the selected commands the user choose
     */
    public void setSelectedCommand (ImageView prevButton) {
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String command = listView.getSelectionModel().getSelectedItem();
                System.out.println("select: " + command);
                if (command != null) {
                    activeCommands.add(map.get(command));
                    GameMenuEvent e = new GameMenuEvent(activeCommands.get(0), InputSource.PLAYER);
                    ServiceLocator.getGameWorld().handleInput(e);
                }
                listView.setVisible(false);
                prevButton.setVisible(true);
            }
        });
    }
}
