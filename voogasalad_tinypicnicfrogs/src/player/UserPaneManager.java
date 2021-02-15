package player;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static player.SceneManager.DEFAULT_RESOURCE;
/**
 * @author Michael Glushakov
 * Purpose: Manages UI for displaying user info
 * Dependencies: UserProfileManager
 * Usages: Used by SceneManager
 */
public class UserPaneManager {
    private UserProfileManager myManager;
    private final int IMAGE_SIDE_LENGTH=100;
    private final int FONT_SIZE=20;
    private ResourceBundle myResources;

    /**
     *
     * @param manager UserprofileManager to get info from
     */
    public UserPaneManager(UserProfileManager manager){
        myManager=manager;
        myResources= ResourceBundle.getBundle(DEFAULT_RESOURCE);
    }

    /**
     *
     * @return VBOX with account info
     */

    public VBox setUpAccountBox(){
        VBox accountBox=new VBox();
        accountBox.setSpacing(SceneManager.BOX_SPACING);
        accountBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        ImageView profileView = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("profile_icon.png")));
        profileView.setFitWidth(IMAGE_SIDE_LENGTH);
        profileView.setFitHeight(IMAGE_SIDE_LENGTH);
        accountBox.getChildren().add(profileView);
        for(String key:myManager.getUserAttributes().keySet()){
            Text text = new Text(key+": "+myManager.getUserAttributes().get(key));
            text.setFont(Font.font(myResources.getString("fontFamily"),FONT_SIZE));
            accountBox.getChildren().add(text);
        }
        accountBox.getChildren().addAll(setUpFollowerBox(myManager.getFollowers(),myManager.getFollowing()));
        return accountBox;
    }

    /**
     *
     * @param followersList
     * @param followingList
     * @return fox with follower and following buttons
     */
    public HBox setUpFollowerBox(List<String>followersList,List<String>followingList){
        HBox hbox = new HBox();
        Button followers= new Button(myResources.getString("followers")+": "+followersList.size());
        Button following = new Button(myResources.getString("following")+": "+followingList.size());
        followers.setOnAction(event->setUpListAlert(myResources.getString("followers"),myResources.getString("followers"),followersList));
        following.setOnAction(event->setUpListAlert(myResources.getString("following"),myResources.getString("following"),followingList));
        hbox.getChildren().addAll(followers,following);
        hbox.setSpacing(8);
        return hbox;
    }

    /**
     * launches list dialog
     * @param title
     * @param header
     * @param list
     */
    public void setUpListAlert(String title,String header, List<String> list){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        String text="";
        for(String s:list){text+=s+"\n";}
        alert.setContentText(text);
        alert.showAndWait();
    }



}
