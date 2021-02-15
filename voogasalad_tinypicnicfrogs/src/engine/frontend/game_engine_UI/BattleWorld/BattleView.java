package engine.frontend.game_engine_UI.BattleWorld;

import engine.backend.AnimationObject;
import engine.backend.BattleTurn;
import engine.backend.ServiceLocator;
import engine.backend.gameevent.AnimationFinishedEvent;
import engine.controller.Controller;
import engine.frontend.game_engine_UI.AnimationProcesser.AnimationManager;
import engine.frontend.game_engine_UI.AnimationProcesser.Sprite;
import engine.frontend.game_engine_UI.AnimationProcesser.SpriteProcessor;
import engine.frontend.game_engine_UI.MenuView.BattleMenu;
import engine.frontend.game_engine_UI.WorldView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;


/**
 * View class extended from WordlView, is responsible for updating the battle view
 * in the game
 *
 * @author Duy Trieu (dvt5)
 */
public class BattleView extends WorldView implements BattleViewAPI {
    private PlayerSide playerSide;
    private OpponentSide opponentSide;
    private AnimationObject myPlayer;
    private AnimationObject myEnemy;
    private BattleMenu menuView;
    private ImageView battle_background;
    private ImageView battleAnimation;

    private static final double SCREEN_WIDTH = 750;
    private static final double SCREEN_HEIGHT = 600;
    private static final double SPACING = 20;

    /**
     * @param controller Controller that will send information from the back end to be updated in the front end
     */
    public BattleView(Controller controller) {
        super(controller);
        setUpDisplay();
        addButtonPane();
        init();
        playAnimation();
    }
    /**
    * set up the display of the battle view
    */
    private void setUpDisplay() {
        clearView();
        this.myEnemy = myController.getBattleEnemyAnimation().get(0);
        this.myPlayer = myController.getBattlePlayerAnimation().get(0);
        playerSide = new PlayerSide(myPlayer, myController.getAlliesHealth().get(0));
        opponentSide = new OpponentSide(myEnemy, myController.getEnemiesHealth().get(0));
        battle_background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("battle_background.png")));
        battle_background.setFitWidth(SCREEN_WIDTH);
        battle_background.setFitHeight(SCREEN_WIDTH/2);
        displayPane.getChildren().add(battle_background);
        displayPane.setLeft(playerSide);
        displayPane.setRight(opponentSide);
        playAnimation();
    }
    /**
     * set up the button options for battle
     */
    private void addButtonPane () {
        HBox buttonBox = new HBox();
        ImageView fightButton = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("fight_button.png")));
        buttonBox.setSpacing(SPACING);
        buttonBox.getChildren().add(fightButton);
        displayPane.getChildren().add(buttonBox);
        buttonBox.setTranslateY(battle_background.getFitHeight());
        fightButton.setOnMouseClicked((event -> {
            fightButton.setVisible(false);
            menuView = new BattleMenu(myController);
            menuView.setPrefHeight(SCREEN_HEIGHT-battle_background.getFitHeight());
            menuView.setMaxHeight(SCREEN_HEIGHT-battle_background.getFitHeight());
            menuView.setMinWidth(SCREEN_WIDTH/2);
            displayPane.setCenter(menuView);
            displayPane.setAlignment(menuView, Pos.BOTTOM_RIGHT);
            menuView.setLayoutX(fightButton.getFitWidth());
            menuView.setSelectedCommand(fightButton);
            this.playerSide.setHealth(myController.getAlliesHealth().get(0));
            this.opponentSide.setHealth(myController.getEnemiesHealth().get(0));
        }));
    }
    /**
     * @return menuView
     */
    public BattleMenu getMenuView () {
        return menuView;
    }

    /**
     *
     * @param animationObject
     * @param turn
     * set up and run the move animation for every frame.
     * This method is used for both the player and enemy sides
     */
    private void addMoveAnimation (AnimationObject animationObject, BattleTurn turn) {
        if(frameCounter.get() >= 10000){
            frameCounter.set(0);
        }
        else{
            frameCounter.set(frameCounter.get() +1);
        }
        battleAnimation = new ImageView(animationObject.getAnimationView().getImage());
        if (turn == BattleTurn.Enemy) {
            playerSide.setImage(battleAnimation);
        }
        else if (turn == BattleTurn.Player) {
            opponentSide.setImage(battleAnimation);
        }
        //frameCounter.set((frameCounter.get() + 1) % (animationObject.getSpriteRows() * animationObject.getSpiteCols()));
        SpriteProcessor processor = new SpriteProcessor(battleAnimation.getImage(), animationObject.getSpriteRows(), animationObject.getSpiteCols());
        int animationFrame = frameCounter.get() % (animationObject.getSpriteRows() * animationObject.getSpiteCols());
        Sprite moveSprite = processor.getViewList()[animationFrame];
        Rectangle2D rec = new Rectangle2D(moveSprite.getX(), moveSprite.getY(), battleAnimation.getImage().getWidth()/animationObject.getSpiteCols(),
                battleAnimation.getImage().getHeight()/animationObject.getSpriteRows());
        battleAnimation.setPreserveRatio(true);
        battleAnimation.setViewport(rec);
        animationManager.getChildren().remove(animation);
    }
    /**
     *
     * @param animationObject
     * @param turn
     * method that is called by the backend that will play the correct animation
     * of the battle moves.
     */
    public void playMoveAnimation (AnimationObject animationObject, BattleTurn turn) {
        frameCounter.set(0);
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
            addMoveAnimation(animationObject, turn);
        });
        Timeline moveAnimation = new Timeline();
        animationManager.stop();
        animationManager.getChildren().add(moveAnimation);
        animationManager.play();
        moveAnimation.getKeyFrames().add(frame);
        moveAnimation.setCycleCount(animationObject.getSpiteCols() * animationObject.getSpriteRows());
        ServiceLocator.getGameWorld().handleInput(new AnimationFinishedEvent());
        displayPane.getChildren().remove(battleAnimation);
    }
    /**
     * update the game loop of battle view
     */
    private void updateView () {
        playBattle();
        if(myController.getAlliesHealth().size() != 0 && myController.getEnemiesHealth().size() != 0){
            this.playerSide.setHealth(myController.getAlliesHealth().get(0));
            this.opponentSide.setHealth(myController.getEnemiesHealth().get(0));
        }
    }
    /**
     * set up every frame of the battle view
     */
    private void playBattle () {
        if(frameCounter.get() >= 10000){
            frameCounter.set(0);
        }
        else{
            frameCounter.set(frameCounter.get() +1);
        }

        Collection<AnimationObject> animationObjects = new ArrayList<>();
        animationObjects.add(myPlayer);
        animationObjects.add(myEnemy);
        AnimationManager manager = new AnimationManager(animationObjects);
        for (AnimationObject animationObject : animationObjects) {
            ImageView animation = animationObject.getAnimationView();
            int animationFrame = frameCounter.get() % (animationObject.getSpriteRows() * animationObject.getSpiteCols());
            Sprite sprite = manager.getSpriteMap().get(animationObject)[animationFrame];
            Rectangle2D rec = new Rectangle2D(sprite.getX(), sprite.getY(), animation.getImage().getWidth()/animationObject.getSpiteCols(),
                    animation.getImage().getHeight()/animationObject.getSpriteRows());
            animation.setPreserveRatio(true);
            animation.setViewport(rec);
        }

    }
    /**
     * set up the main animation for battle view
     */
    private void init () {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> { updateView(); });
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animationManager.getChildren().add(animation);
    }

}
