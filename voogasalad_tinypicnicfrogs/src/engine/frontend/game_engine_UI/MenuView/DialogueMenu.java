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
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DialogueMenu class that provides the player the options for dialogue
 *
 * @author Duy Trieu (dvt5)
 */
public class DialogueMenu extends HBox {
    private BorderPane pane = new BorderPane();
    Map<String, Command> map = new HashMap<>();
    ListView<String> listView = new ListView<>();

    /**
     * @param controller
     * @param text the text in the dialogue box
     */
    public DialogueMenu (Controller controller, String text) {
        ScrollPane scrollPane = new ScrollPane();
        Text messageText = new Text(text);
        scrollPane.setContent(messageText);
        scrollPane.setPrefViewportHeight(50);
        scrollPane.setPrefViewportWidth(1000);
        scrollPane.setLayoutY(670);
        pane.setLeft(scrollPane);
        this.getChildren().add(pane);
    }

    /**
     * run the selected commands the user choose
     */
    public void addListView (List<Command> commands) {
        List<String> nameList = new ArrayList<>();
        for (Command command:commands) {
            nameList.add(command.getName());
            map.put(command.getName(), command);
        }
        ObservableList<String> items = FXCollections.observableArrayList (nameList);
        listView.setPrefSize(100, 50);
        listView.setItems(items);
        pane.setRight(listView);
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + listView.getSelectionModel().getSelectedItem());
                String command = listView.getSelectionModel().getSelectedItem();
                GameMenuEvent e = new GameMenuEvent(map.get(command), InputSource.PLAYER);
                ServiceLocator.getGameWorld().handleInput(e);
            }
        });
    }
}
