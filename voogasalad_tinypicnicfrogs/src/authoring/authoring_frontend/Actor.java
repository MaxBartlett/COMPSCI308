package authoring.authoring_frontend;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Actor class to represent any item that has any picture or interaction.
 *
 * @author Allen Qiu
 */
public class Actor {
    private Image actorImage;
    private String actorPrototypeID;
    private String url;

//    private int row;
//    private int col;

    /**
     * Constructor for the actor.
     * @param actor JSON object of the actor.
     */
    public Actor(JSONObject actor, int cellSize){
        actorPrototypeID = (String)actor.get("name");
        JSONArray animations = (JSONArray)actor.get("animations");
        JSONObject defaultSprite = (JSONObject)animations.get(0);
        url = (String) defaultSprite.get("path");
        actorImage = new Image(url, cellSize, cellSize, true, true);
    }

    /**
     * Constructor
     * @param id Actor prototype ID
     * @param image Image of the actor
     */
    public Actor(String id, Image image){
        actorPrototypeID = id;
        actorImage = image;
    }

    /**
     * Gets an image representation of the actor.
     * @return ImageView of the actor
     */
    ImageView getActorImage(){
        return new ImageView(actorImage);
    }

    /**
     * Gets the ID.
     * @return The ID of the actor prototype.
     */
    String getActorPrototypeID(){
        return actorPrototypeID;
    }

    /**
     * Resizes the image for this actor
     * @param newSize The new size of the image
     */
    void resizeImage(int newSize){
        System.out.println("Resized to " + newSize);
        actorImage = new Image(url, newSize, newSize, true, true);
    }

}
