package authoring.authoring_backend;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * @author Michael Glushakov
 * Purpose: Passed to authoring front-end to keep original Actors closed to modification
 * Dependencies: None
 * Usages: when an actor is created it creates an observable actor and puts it in a list. Front-end can then request accessto the list
 */

public class ObservableActor {
    public String myId;
    public int x,y,z;
    public Image myView;
    public ObservableActor (String id, int xc,int yc,int zc,Image view){
        x=xc;
        y=yc;
        z=zc;
        myId=id;
        myView=view;
    }
}
