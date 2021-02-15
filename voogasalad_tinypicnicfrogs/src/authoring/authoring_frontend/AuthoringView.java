package authoring.authoring_frontend;

import authoring.authoring_backend.GameManager;
import authoring.authoring_frontend.Forms.LoadForm;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * AuthoringView
 *
 * main frontend class that connects all UI elements
 * of the Game Authoring Environment
 *
 * @author brookekeene, Allen Qiu
 */
public class AuthoringView {
    static final int WIDTH = 1000;
    private static final int HEIGHT = 620;
    private static final String STYLESHEET = "default.css";
    private Group myRoot;
    private Scene myScene;
    private BorderPane myMainView;

    private GameManager myManager;
    private MapManager mapManager;
    private ActorManager actorManager;
    private String projectName;
    private int numberUnsavedProjects = 1;

    /**
     * Constructor
     */
    public AuthoringView() {
        myManager = new GameManager();
        projectName = "Project " + numberUnsavedProjects;
        numberUnsavedProjects++;
        this.initializeUI();
}

    AuthoringView(int width, int height, int size){
        myManager = new GameManager();
        projectName = "Project " + numberUnsavedProjects;
        numberUnsavedProjects++;
        this.initializeUI(width, height, size);
    }

    /**
     * Opens an AuthoringView project without prompting for a new map size
     * @param path Project path
     */
    public AuthoringView(String path){
        //initialize game authoring environment with preloaded file
        myManager = new GameManager();
        projectName = "Project" + numberUnsavedProjects;
        numberUnsavedProjects++;
        this.initializeUI(path);
    }

    /**
     * Gets the scene
     * @return Returns the AuthoringView scene
     */
    public Scene getScene() {
        return myScene;
    }

    /**
     * Initializes UI without prompting for a map size
     * @param path Path of the game to load
     */
    private void initializeUI(String path){
        myRoot = new Group();
        myScene = new Scene(myRoot, WIDTH, HEIGHT);
        myMainView = new BorderPane();
        mapManager = new MapManager(projectName, myManager);
        mapManager.setInitialHeight(5);
        mapManager.setInitialWidth(5);
        mapManager.setCellSize(5);
        actorManager = new ActorManager(myManager, projectName, mapManager.getCellSize());
        myScene.getStylesheets().add(STYLESHEET);

        new LayerMenu();
        MapMenu myMaps = new MapMenu(projectName, mapManager, myManager, mapManager.getInitialWidth(), mapManager.getInitialHeight());
        VBox leftSide = new VBox();
        leftSide.setMaxHeight(600);
        leftSide.setMaxWidth(400);

        leftSide.getChildren().addAll(actorManager.getActorMenu(), myMaps.getMapPane());
        TopMenu topBar = new TopMenu(myManager, mapManager, actorManager, projectName, myScene);

        new LoadForm(myManager, actorManager, mapManager, path);

        myMainView.setCenter(new ScrollPane(mapManager.getActiveMap()));
        myMainView.setLeft(leftSide);
        myMainView.setTop(topBar);
        myRoot.getChildren().add(myMainView);
    }

    /**
     * creates main components needed for JavaFx layouts and accesses
     * appropriate resources
     */
    private void initializeUI() {
        myRoot = new Group();
        myScene = new Scene(myRoot, WIDTH, HEIGHT);
        myMainView = new BorderPane();
        mapManager = new MapManager(projectName, myManager);
        PopupFactory.getSetupPopup(myManager, mapManager, true);
        actorManager = new ActorManager(myManager, projectName, mapManager.getCellSize());
        myScene.getStylesheets().add(STYLESHEET);

        new LayerMenu();
        MapMenu myMaps = new MapMenu(projectName, mapManager, myManager, mapManager.getInitialWidth(), mapManager.getInitialHeight());
        VBox leftSide = new VBox();
        leftSide.setMaxHeight(600);
        leftSide.setMaxWidth(400);

        leftSide.getChildren().addAll(actorManager.getActorMenu(), myMaps.getMapPane());
        TopMenu topBar = new TopMenu(myManager, mapManager, actorManager, projectName, myScene);

        myMainView.setCenter(new ScrollPane(mapManager.getActiveMap()));
        myMainView.setLeft(leftSide);
        myMainView.setTop(topBar);
        myRoot.getChildren().add(myMainView);

    }

    /**
     * Creates UI with set size and width and height without prompting
     * @param width Width of initial map
     * @param height Height of initial map
     * @param size Cell size
     */
    private void initializeUI(int width, int height, int size) {
        myRoot = new Group();
        myScene = new Scene(myRoot, WIDTH, HEIGHT);
        myMainView = new BorderPane();
        mapManager = new MapManager(projectName, myManager);
        mapManager.setInitialWidth(width);
        mapManager.setInitialHeight(height);
        mapManager.setCellSize(size);
        actorManager = new ActorManager(myManager, projectName, mapManager.getCellSize());

        new LayerMenu();
        MapMenu myMaps = new MapMenu(projectName, mapManager, myManager, mapManager.getInitialWidth(), mapManager.getInitialHeight());
        VBox leftSide = new VBox();
        leftSide.setMaxHeight(600);
        leftSide.setMaxWidth(400);

        leftSide.getChildren().addAll(actorManager.getActorMenu(), myMaps.getMapPane());
        TopMenu topBar = new TopMenu(myManager, mapManager, actorManager, projectName, myScene);

        myMainView.setLeft(leftSide);
        myMainView.setCenter(mapManager.getActiveMap());
        myMainView.setTop(topBar);
        myRoot.getChildren().add(myMainView);
    }

    public String getProjectName(){
        return projectName;
    }

}
