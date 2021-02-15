package player;

import authoring.authoring_frontend.AuthoringView;
import authoring.authoring_frontend.Main;
import engine.frontend.game_engine_UI.BattleWorld.OpponentSide;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Michael Glushakov
 * Purpose: Manages different scenes and UI components
 * Dependencies: UserPaneManager UserProfileManager,GamePaneManager
 * Usages: called by PlayerMain to return an initial scene, aftet that manages all scene changes
 */
public class SceneManager {
    private Stage myStage;
    public static final int BOX_SPACING=8;
    private final int INCEST_TP=15;
    private final int INCEST_RL=12;
    private UserProfileManager userManager;
    private UserPaneManager userPaneManager;
    private final int REGISTER_BOX_WIDTH=500;
    private final int REGISTER_BOX_HEIGHT=100;
    private BorderPane mainPane;
    private GamePaneManager gamePaneManager;
    public static final String DEFAULT_RESOURCE = "English";
    private ResourceBundle myResources;

    /**
     * @param manager: user profile manager
     * @param stage: stage to put scenes on
     */
    public SceneManager(UserProfileManager manager, Stage stage){
        userManager=manager;
        myStage=stage;
        userPaneManager= new UserPaneManager(userManager);
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE);
        myStage.setScene(getLoginScene());
        gamePaneManager= new GamePaneManager();
    }

    /**
     * @return Login scene
     */
    private Scene getLoginScene(){
        BorderPane borderPane= new BorderPane();
        borderPane.setPrefSize(500,100);
        Scene loginScene = new Scene(borderPane);
        getLoginScreen(borderPane);
        return loginScene;
    }

    /**
     *
     * @param pane border pne that will be set up to contain the login fields
     */
    private void getLoginScreen(BorderPane pane){
        VBox vBox=new VBox();
        pane.setCenter(vBox);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(8);
        setUpLoginBox(vBox);
    }

    /**
     * @param vbox vBox to be set up with login fields
     */
    private void setUpLoginBox(VBox vbox) {
        TextField emailField = new TextField(myResources.getString("email"));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(myResources.getString("password"));
        Button loginButton = new Button(myResources.getString("loginBTN"));
        Button registerButton = new Button(myResources.getString("registerBTN"));
        registerButton.setOnAction(event->{myStage.setScene(setUpRegisterScene());});
        vbox.getChildren().addAll(emailField, passwordField, loginButton,registerButton);
        loginButton.setOnAction(event -> {
            try {
                userManager.login(emailField.getText(),passwordField.getText());
            } catch (ServerException e) {
                launchErrorDialog(e.getException());
            }
            if(userManager.isPlayerLoggedIn()){
                myStage.setScene(setUpMainScene());}
        });
    }

    /**
     *
     * @return account creation scene
     */
    private Scene setUpRegisterScene(){
        BorderPane borderPane= new BorderPane();
        Scene registerScene = new Scene(borderPane);
        VBox vbox = new VBox();
        setUpRegisterBox(vbox);
        borderPane.setPrefSize(REGISTER_BOX_WIDTH,REGISTER_BOX_HEIGHT);
        borderPane.setCenter(vbox);
        return registerScene;
    }

    /**
     *
     * @param vBox Box to be set up with all register UI components
     */
    private void setUpRegisterBox(VBox vBox){
        vBox.setSpacing(BOX_SPACING);
        vBox.setPadding(new Insets(INCEST_TP, INCEST_RL
                , INCEST_TP, INCEST_RL));
        TextField emailField = new TextField(myResources.getString("email"));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(myResources.getString("password"));
        TextField bioField = new TextField(myResources.getString("bio"));
        TextField nameField= new TextField(myResources.getString("name"));
        Button registerButton = new Button(myResources.getString("registerBTN"));
        vBox.getChildren().addAll(emailField,passwordField,bioField,nameField,registerButton);
        registerButton.setOnAction(event->{
            try {
                userManager.register(emailField.getText(),passwordField.getText(),bioField.getText(),nameField.getText());
            } catch (ServerException e) {
                launchErrorDialog(e.getException());
            }
            if(userManager.isPlayerLoggedIn()){myStage.setScene(setUpMainScene());}
        });
    }

    /**
     *
     * @return Main Scene
     */
    private Scene setUpMainScene(){
        mainPane= new BorderPane();
        mainPane.setPrefSize(PlayerMain.SCREEN_SIZE,PlayerMain.SCREEN_SIZE);
        Scene mainScene= new Scene(mainPane);
        mainPane.setRight(userPaneManager.setUpAccountBox());
        HBox hBox= new HBox();
        hBox.getChildren().addAll(setUpMenuBar());
        mainPane.setTop(hBox);
        mainPane.setCenter(gamePaneManager.getGamesPane());
        return mainScene;
    }

    /**
     *
     * @return Menu bar for main scene
     */
    private MenuBar setUpMenuBar(){
        MenuBar menuBar= new MenuBar();
        menuBar.prefWidthProperty().bind(myStage.widthProperty());

        Menu fileMenu = new Menu(myResources.getString("File"));
        setFileMenu(fileMenu);

        Menu accountMenu = new Menu(myResources.getString("accountMenu"));
        setAccountMenu(accountMenu);

        Menu editMenu = new Menu(myResources.getString("editAuthoring"));
        setEditMenu(editMenu);
        menuBar.getMenus().addAll(fileMenu,accountMenu, editMenu);
        return menuBar;
    }

    /**
     *
     * @param menu menu that will be set up with file menu items
     */
    private void setFileMenu(Menu menu){
        MenuItem exitItem = new MenuItem(myResources.getString("exitMI"));
        exitItem.setOnAction(event->{System.exit(0);});
        MenuItem newGameItem= new MenuItem(myResources.getString("launchAuthoringItem"));
        newGameItem.setOnAction(event->{
            Main main= new Main();
            main.start(new Stage());
        });
        menu.getItems().addAll(exitItem,newGameItem);
    }

    /**
     *
     * @param menu menu that will be set up with account menu items
     */
    private void setAccountMenu(Menu menu){
        MenuItem logOutItem = new MenuItem(myResources.getString("logOutMI"));
        logOutItem.setOnAction(event->{
            userManager.clear();
            myStage.setScene(getLoginScene());
        });
        MenuItem editAccountItem = new MenuItem(myResources.getString("editAccountMI"));
        MenuItem socialItem=new MenuItem(myResources.getString("searchMI"));
        socialItem.setOnAction(event -> {setUserLookupDialog();});
        editAccountItem.setOnAction(event->{setEditAccountDialog();});
        menu.getItems().addAll(logOutItem,editAccountItem,socialItem);
    }

    /**
     * Launches edit account dialog
     */
    private void setEditAccountDialog(){

        TextField nameField=new TextField(userManager.getUserAttributes().get(myResources.getString("name")));
        TextField bioField= new TextField(userManager.getUserAttributes().get(myResources.getString("bio")));
        Button saveButton = new Button(myResources.getString("Save"));
        saveButton.setOnAction(event->{
            try {
                userManager.updateInfo(nameField.getText(), bioField.getText());
                mainPane.setRight(userPaneManager.setUpAccountBox());
            }catch(ServerException e){
                launchErrorDialog(e.getException());
            }
        });
        VBox vBox= new VBox();
        vBox.getChildren().addAll(new Text(myResources.getString("name")),nameField,new Text(myResources.getString("bio")),bioField,saveButton);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(myResources.getString("profile"));
        alert.getDialogPane().setContent(vBox);
        alert.showAndWait();

    }

    private void setEditMenu(Menu menu){
        MenuItem editGameItem = new MenuItem(myResources.getString("editAuthoringGame"));
        //FileChooser fileChooser = new FileChooser();
        editGameItem.setOnAction(event->{
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle(myResources.getString("choosePath"));
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            if (selectedDirectory != null) {
                //gamePath = selectedDirectory.getPath();
                Stage newStage = new Stage();
                AuthoringView environment = new AuthoringView(selectedDirectory.getPath());
                newStage.setTitle(environment.getProjectName());
                newStage.setScene(environment.getScene());
                newStage.show();
            }

        });
        menu.getItems().addAll(editGameItem);

    }

    /**
     * launches user lookup dialog
     */
    private void setUserLookupDialog(){
        TextInputDialog dialog= new TextInputDialog(myResources.getString("name"));
        dialog.setHeaderText(myResources.getString("socialPortal"));
        dialog.setHeaderText(myResources.getString("findUsers"));
        dialog.setContentText(myResources.getString("namePrompt"));
        Optional <String>result=dialog.showAndWait();
        result.ifPresent(name->{
            if(!name.equals(myResources.getString("name"))){
                try{
                    setUpUserListDialog(myResources.getString("socialPortal"),myResources.getString("users"),userManager.lookUpUsers(name));}
                catch(ServerException e){
                    launchErrorDialog(e.getException());
                }
            }
        });
    }

    /**
     *
     * @param title title
     * @param header header
     * @param entries JSON representation of entries
     *  launches list dialog of users found
     */
    private void setUpUserListDialog(String title, String header, List<JSONObject>entries){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        VBox vBox= new VBox();
        for(JSONObject user:entries){
            Button button= new Button((String)user.get("name"));
            button.setOnAction(event->{setUpUserDialog(user);});
            vBox.getChildren().add(button);
        }

        alert.getDialogPane().setContent(vBox);
        alert.showAndWait();
    }

    /**
     * Sets up dialog for a single user
     * @param user JSONObject with that user's information
     */
    private void setUpUserDialog(JSONObject user){
        VBox vBox= new VBox();
        vBox.setSpacing(BOX_SPACING);
        vBox.getChildren().addAll(new Text(myResources.getString("name")+": "+(String)user.get("name")),new Text(myResources.getString("email")+": "+(String)user.get(myResources.getString("email"))),new Text(myResources.getString("bio")+": "+(String)user.get("bio")));
        List<String>followers= new ArrayList<>();
        List<String>following= new ArrayList<>();
        userManager.parseArray((JSONArray)user.get("followers"),followers);
        userManager.parseArray((JSONArray)user.get("following"),following);
        Button followButton= new Button(myResources.getString("follow"));
        if(userManager.getFollowers().contains((String)user.get("email"))){followButton.setText(myResources.getString("following"));}
        else{
            followButton.setOnAction(event->{
                try {
                    userManager.followUser((String) user.get("email"));
                    mainPane.setRight(userPaneManager.setUpAccountBox());
                }catch(ServerException e){
                    launchErrorDialog(e.getException());
                }
            });

        }
        vBox.getChildren().addAll(userPaneManager.setUpFollowerBox(followers,following),followButton);
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(myResources.getString("socialPortal"));
        alert.setHeaderText(myResources.getString("users"));
        alert.getDialogPane().setContent(vBox);
        alert.showAndWait();
    }

    /**
     * launches error dialog
     * @param e exception thrown
     */
    private void launchErrorDialog(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(myResources.getString("error"));
        alert.getDialogPane().setContent(new VBox(new Text(e.getMessage())));
        alert.showAndWait();
    }
}