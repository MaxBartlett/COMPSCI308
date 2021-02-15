package engine.frontend.game_engine_UI.BattleWorld;

import engine.backend.AnimationObject;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author pkp9
 * @author dvt5
 * super class for each side of the battle screen (player, enemy)
 */

abstract class SideView extends HBox {

    private ProgressBar hpBar;
    private AnimationObject myAnimation;
    private double myHealth;
    private double myMaxHealth;
    BorderPane view;
    private ImageView image;

    /**
     * @param actorAnimation the animation for battle
     */
    public SideView(AnimationObject actorAnimation, double myMaxHealth) {
        this.myAnimation = actorAnimation;
        this.myMaxHealth = myMaxHealth;
        myHealth = myMaxHealth;
        setUpView();
    }
    /**
     * set up the view
     */
    private void setUpView() {
        view = new BorderPane();
        hpBar = new ProgressBar();
        VBox box = new VBox();
        image = myAnimation.getAnimationView();
        image.setFitHeight(150);
        image.setFitWidth(600);
        image.setPreserveRatio(true);
        box.getChildren().add(image);
        box.getChildren().add(hpBar);
        view.setCenter(box);
        view.setTranslateX(75);
        view.setTranslateY(80);
        this.getChildren().add(view);
    }

    /**
     * @param health
     * set the health for both the player and enemy
     */
    public void setHealth(double health) {
        myHealth = health;
        hpBar.setProgress(myHealth / myMaxHealth);
        //System.out.println("New health is " + health + " and max health is " + myMaxHealth);
    }

    /**
     * @return  image
     */
    public ImageView getImage () {
        return image;
    }

    public void setImage (ImageView img) { this.image = img; }
}
