package authoring.authoring_frontend;

import authoring.authoring_backend.GameManager;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static authoring.authoring_frontend.AuthoringView.WIDTH;

/**
 * TopMenu
 *
 * Creates a MenuBar that allows user to select from various menus
 * of settings and basic functions
 *
 * @author brookekeene
 */
class TopMenu extends HBox {
    private static final String DEFAULT_RESOURCE = "English";
    public static final String STYLESHEET = "default.css";
    private static final int PADDING_TEN = 10;
    private static final int PADDING_TWENTY = 20;
    private static final int PADDING_ONEFIFTY = 150;

    private GameManager myManager;
    private MenuBar myMenu;
    private ResourceBundle myResources;
    private MapManager mapManager;
    private ActorManager actorManager;
    private String programName;
    private Scene thisScene;
    private PopupFactory myFactory;
    private boolean dark;

    /**
     * Constructor
     */
    TopMenu(GameManager manager, MapManager maps, ActorManager actor, String pName, Scene scene) {
        myManager = manager;
        myMenu = new MenuBar();
        myMenu.setPrefWidth(WIDTH);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE);
        mapManager = maps;
        actorManager = actor;
        programName = pName;
        thisScene = scene;
        dark = true;
        myFactory = new PopupFactory();

        this.getChildren().add(myMenu);
        this.addAllMenus();
    }

    /**
     * calls methods which create dropdown menus within myMenu
     */
    private void addAllMenus() {
        addFileTab();
        addEditTab();
        addViewTab();
    }

    /**
     * creates new File menu with choices: New (Game, Prototype), Open
     */
    private void addFileTab() {
        Menu fileMenu = new Menu(myResources.getString("File"));

        // New Submenu
        Menu newSubmenu = new Menu(myResources.getString("New"));
        MenuItem newGame = new MenuItem(myResources.getString("Game"));
        MenuItem newActor = new MenuItem(myResources.getString("Prototype"));
        MenuItem newDialog = new MenuItem(myResources.getString("DialogKey"));
        MenuItem newDialogConnection = new MenuItem(myResources.getString("DialogConnection"));

        newSubmenu.getItems().addAll(newGame, newActor, newDialog, newDialogConnection);

        newGame.setOnAction(e -> {
            Dialog<List<String>> dialog = new Dialog<>();
            if(dark) {
                dialog.getDialogPane().getStylesheets().add(STYLESHEET);
            }
            dialog.setTitle("New Game");
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
            TextField cellSize = new TextField();
            cellSize.setPromptText("Cell Size");

            grid.add(new Label("Width:"), 0, 0);
            grid.add(xSize, 1, 0);
            grid.add(new Label("Height:"), 0, 1);
            grid.add(ySize, 1, 1);
            grid.add(new Label("Cell Size:"), 0, 2);
            grid.add(cellSize, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    ArrayList<String> thisResult = new ArrayList<>();
                    thisResult.add(xSize.getText());
                    thisResult.add(ySize.getText());
                    thisResult.add(cellSize.getText());
                    return thisResult;
                }
                return null;
            });

            Optional<List<String>> result = dialog.showAndWait();

            result.ifPresent(widthHeight -> {
                Stage newAuthoringView = new Stage();
                AuthoringView environment = new AuthoringView(Integer.parseInt(widthHeight.get(0)), Integer.parseInt(widthHeight.get(1)), Integer.parseInt(widthHeight.get(2)));
                newAuthoringView.setTitle(environment.getProjectName());
                newAuthoringView.setScene(environment.getScene());
                newAuthoringView.show();
            });
        });

        newActor.setOnAction(e -> myFactory.getPopup("prototype", myManager, actorManager, mapManager, programName));
        newDialog.setOnAction(e -> myFactory.getPopup("dialogueKey", myManager, actorManager, mapManager, programName));
        newDialogConnection.setOnAction(e -> myFactory.getPopup("dialogueConnect", myManager, actorManager, mapManager, programName));

        // Open Submenu
        MenuItem openItem = new MenuItem(myResources.getString("Open"));

        openItem.setOnAction(e -> myFactory.getPopup("open", myManager, actorManager, mapManager, programName));

        // Save Submenu
        MenuItem saveGame = new MenuItem(myResources.getString("Save"));

        saveGame.setOnAction(e -> myFactory.getPopup("save", myManager, actorManager, mapManager, programName));

        fileMenu.getItems().addAll(newSubmenu, openItem, saveGame);
        myMenu.getMenus().add(fileMenu);
    }


    /**
     * creates new Edit menu with choices: Map
     */
    private void addEditTab(){
        Menu editMenu = new Menu(myResources.getString("Edit"));

        // Map
        MenuItem mapItem = new MenuItem(myResources.getString("MapDim"));
        //MenuItem actorItem = new MenuItem(myResources.getString("Prototype"));

        mapItem.setOnAction(e -> {
            Dialog<List<String>> dialog = new Dialog<>();
            dialog.setTitle("Edit Map");
            dialog.setHeaderText("Enter the new dimensions of the map:");
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
            TextField cellSize = new TextField();
            cellSize.setPromptText("Cell Size");

            grid.add(new Label("Width:"), 0, 0);
            grid.add(xSize, 1, 0);
            grid.add(new Label("Height:"), 0, 1);
            grid.add(ySize, 1, 1);
            grid.add(new Label("Cell Size:"), 0, 2);
            grid.add(cellSize, 1, 2);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    ArrayList<String> myResults = new ArrayList<String>();
                    myResults.add(xSize.getText());
                    myResults.add(ySize.getText());
                    myResults.add(cellSize.getText());
                    return myResults;
                }
                return null;
            });

            Optional<List<String>> result = dialog.showAndWait();

            result.ifPresent(widthHeight -> {
                int newWidth = Integer.parseInt(widthHeight.get(0));
                int newHeight = Integer.parseInt(widthHeight.get(1));
                int cellSizeeee = Integer.parseInt(widthHeight.get(2));
                Map currentMap = mapManager.getMap(mapManager.getActiveMapName());
                if(newWidth <= 0 || newHeight <= 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Incorrect Dimensions");
                    alert.setContentText("Width or height must be above 0!");

                    alert.showAndWait();
                }
                else if(newWidth < currentMap.getWidth() || newHeight < currentMap.getHeight()){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirm Data Loss");
                    alert.setHeaderText("The new map width or height has decreased: some clipping will occur.");
                    alert.setContentText("Are you ok with this?");

                    Optional<ButtonType> clippingResult = alert.showAndWait();
                    if (clippingResult.isPresent() && clippingResult.get() == ButtonType.OK){
                        if(cellSizeeee != mapManager.getCellSize()){
                            actorManager.changeActorSizes(cellSizeeee);
                        }
                        mapManager.changeMapDimensions(newWidth, newHeight, cellSizeeee);
                    }
                }
                else {
                    if(cellSizeeee != mapManager.getCellSize()){
                        actorManager.changeActorSizes(cellSizeeee);
                    }
                    mapManager.changeMapDimensions(newWidth, newHeight, cellSizeeee);
                }
            });
        });

        editMenu.getItems().addAll(mapItem);

        myMenu.getMenus().add(editMenu);
    }

    /**
     * creates new View menu with choices: Theme (Light, Dark)
     */
    private void addViewTab(){
        Menu viewMenu = new Menu(myResources.getString("View"));

        // Theme
        Menu themeSubmenu = new Menu(myResources.getString("Theme"));
        CheckMenuItem lightTheme = new CheckMenuItem(myResources.getString("Light"));
        CheckMenuItem darkTheme = new CheckMenuItem(myResources.getString("Dark"));
        darkTheme.setSelected(true);

        lightTheme.setOnAction(e -> {
            thisScene.getStylesheets().remove(STYLESHEET);
            lightTheme.setSelected(true);
            darkTheme.setSelected(false);
            dark = false;
            myFactory.setDarkTheme(dark);
        });

        darkTheme.setOnAction(e -> {
            thisScene.getStylesheets().add(STYLESHEET);
            lightTheme.setSelected(false);
            darkTheme.setSelected(true);
            dark = true;
            myFactory.setDarkTheme(dark);
        });

        themeSubmenu.getItems().addAll(lightTheme, darkTheme);
        viewMenu.getItems().add(themeSubmenu);

        myMenu.getMenus().add(viewMenu);
    }
}
