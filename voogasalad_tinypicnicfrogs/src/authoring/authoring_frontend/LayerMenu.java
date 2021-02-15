package authoring.authoring_frontend;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Currently unused Layer menu.
 *
 * @author Allen Qiu
 */
public class LayerMenu extends HBox {
    private VBox layerList = new VBox();
    private ListView<Layer> layerView = new ListView<>();
    private HBox buttonView = new HBox();
    int numberOfLayers = 1;

    /**
     * Constructor
     */
    public LayerMenu() {
        this.getChildren().add(new Label("layer"));
    }

    /**
     * Sets up the list for the first time.
     * @return Returns a list of the layers.
     */
    public ListView<Layer> setupList(){
        layerView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        layerView.getItems().add(new Layer());
        layerView.setPrefHeight(200);
        layerView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> ActiveLayer.setActiveLayer(newValue));
        return layerView;
    }

    /**
     * Creates the buttons at the top.
     * @return The HBox containing the layers.
     */
    public HBox setupButtons(){
        Button newLayer = new Button("New Layer");
        newLayer.setOnAction(event -> {
            numberOfLayers++;
            layerView.getItems().add(0, new Layer(numberOfLayers));
        });
        Button deleteLayer = new Button("Delete Layer");
        deleteLayer.setOnAction(event -> {
            ObservableList<Layer> selectedLayers = layerView.getSelectionModel().getSelectedItems();
            layerView.getItems().removeAll(selectedLayers);
        });
        buttonView.getChildren().addAll(newLayer, deleteLayer);
        return buttonView;
    }

    /**
     * Returns a tab with a list of layers.
     * @return Tab with the list of layers.
     */
    public Tab getLayerList(){
        layerList.getChildren().addAll(setupButtons(), setupList());
        Tab layerTab = new Tab();
        layerTab.setText("Layers");
        layerTab.setContent(layerList);
        return layerTab;
    }

    /**
     * Gets the current layer.
     * @return The current layer.
     */
    public Layer getCurrentLayer(){
        ObservableList<Layer> selectedLayers = layerView.getSelectionModel().getSelectedItems();
        if(selectedLayers.size() > 0){
            return selectedLayers.get(0);
        }
        return null;
    }
}
