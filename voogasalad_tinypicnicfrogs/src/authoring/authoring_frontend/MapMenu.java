package authoring.authoring_frontend;

import authoring.authoring_backend.GameManager;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.Optional;

/**
 * Menu of maps on the left side of the window.
 *
 * @author Allen Qiu
 */
public class MapMenu extends HBox {
    private VBox mapList = new VBox();
    private ListView<String> mapView = new ListView<>();
    private HBox buttonView = new HBox();
    private MapManager mapManager;
    private GameManager gameManager;
    private int width;
    private int height;
    private static final int WINDOW_HEIGHT = 200;
    private static final int PADDING_TEN = 10;
    private static final int PADDING_TWENTY = 20;
    private static final int PADDING_ONEFIFTY = 150;

    /**
     * Constructor
     * @param pName Program name
     * @param manager MapManager of the game
     * @param gm GameManager of the game
     */
    MapMenu(String pName, MapManager manager, GameManager gm, int initialWidth, int initialHeight) {
        this.getChildren().add(new Label("map"));
        mapManager = manager;
        gameManager = gm;
        width = initialWidth;
        height = initialHeight;
    }

    /**
     * Initializes the list for the first time.
     * @return ListView of map names.
     */
    private ListView<String> setupList(){
        mapView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        String newMap = mapManager.createMap(width, height);
        gameManager.setUpMap(mapManager.getCellSize()*width, mapManager.getCellSize()*height, width, height);
        mapView.getItems().add(newMap);
        mapManager.setActiveMap(newMap);
        mapView.setPrefHeight(WINDOW_HEIGHT);
        mapView.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2){
                mapManager.setActiveMap(mapView.getSelectionModel().getSelectedItem());
            }
        });
        return mapView;
    }

    /**
     * Creates the buttons.
     * @return HBox with buttons.
     */
    private HBox setupButtons(){
        Button newMap = new Button("New Map");
        newMap.setOnAction(event -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("New Map");
            dialog.setHeaderText("Enter the dimensions of the map:");
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
            GridPane grid = new GridPane();
            grid.setHgap(PADDING_TEN);
            grid.setVgap(PADDING_TEN);
            grid.setPadding(new Insets(PADDING_TWENTY, PADDING_ONEFIFTY, PADDING_TEN, PADDING_TEN));
            TextField xSize = new TextField();
            xSize.setPromptText("Width");
            TextField ySize = new TextField();
            ySize.setPromptText("Height");

            grid.add(new Label("Width:"), 0, 0);
            grid.add(xSize, 1, 0);
            grid.add(new Label("Height:"), 0, 1);
            grid.add(ySize, 1, 1);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(xSize.getText(), ySize.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(widthHeight -> {
                if(Integer.parseInt(widthHeight.getKey()) <= 0 || Integer.parseInt(widthHeight.getValue()) <= 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Incorrect Dimensions");
                    alert.setContentText("Width or height must be above 0!");

                    alert.showAndWait();
                }
                else {
                    String thisNewMap = mapManager.createMap(Integer.parseInt(widthHeight.getKey()), Integer.parseInt(widthHeight.getValue()));
                    mapView.getItems().add(0, thisNewMap);
                    mapManager.setActiveMap(thisNewMap);
                }
            });
        });
        Button deleteMap = new Button("Delete Map");
        deleteMap.setOnAction(event -> {
            ObservableList<String> selectedMaps = mapView.getSelectionModel().getSelectedItems();
            if(selectedMaps.contains(mapManager.getActiveMapName())){
                mapManager.findNewActiveMap(selectedMaps);
            }
            mapView.getItems().removeAll(selectedMaps);
            mapManager.removeMap(selectedMaps);
        });
        Button connectMaps = new Button("Connect Maps");
        connectMaps.setOnAction(event -> {
            if(mapManager.getMapList().size() >= 1){
                new MapConnector(mapManager);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("You have no maps to connect!");

                alert.showAndWait();
            }
        });
        buttonView.getChildren().addAll(newMap, deleteMap, connectMaps);
        return buttonView;
    }

    /**
     * Gets pane with all the tabs.
     * @return VBox with all the tabs.
     */
    VBox getMapPane(){
        mapList.getChildren().addAll(setupButtons(), setupList());
        return mapList;
    }

    /**
     * Get the currently selected map.
     * @return Currently selected map.
     */
    public Map getCurrentMap(){
        ObservableList<String> selectedMaps = mapView.getSelectionModel().getSelectedItems();
        if(selectedMaps.size() > 0){
            return mapManager.getMap(selectedMaps.get(0));
        }
        return null;
    }
}
