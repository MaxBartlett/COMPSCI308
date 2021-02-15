package engine.controller;

import engine.backend.*;
import engine.backend.Commands.Command;
import engine.frontend.game_engine_UI.MenuView.DialogueMenu;
import engine.frontend.game_engine_UI.OverWorld.OverWorldView;
import engine.frontend.game_engine_UI.StateView;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * Controller class that sends the appropriate information to both the front end and
 * back end of game engine. This class with ServiceLocator from the back end manage
 * all the communication necessary inside game engine
 *
 * @author Duy Trieu (dvt5)
 */
public class Controller {
    private StateView myView;

    public Controller (StateView view) {
        ServiceLocator.provideController(this);
        this.myView = view;
    }
    /**
     * supplies the Stage of the Engine
     */
    private Supplier<Stage> stageSupplier =() -> myView.getMyStage();
    public Stage getStage () { return stageSupplier.get(); }
    /**
     * supplies the overWorldView
     */
    private Supplier<OverWorldView> viewSupplier = () -> myView.getMyView();
    public OverWorldView getOverWorldView () { return viewSupplier.get(); }
    /**
     * supplies the list of AnimationObjects to the front end to animate
     */
    private Supplier<Collection<AnimationObject>> animationObjectSupplier = () -> ServiceLocator.getActorManager().getAnimationObjects();
    public Collection<AnimationObject> getAnimation () { return animationObjectSupplier.get(); }
    /**
     * supplies the the player to the front end
     */
    private Supplier<Actor> playerActorSupplier = () -> ServiceLocator.getActorManager().getPlayerActor();
    public Actor getPlayer () { return playerActorSupplier.get(); }

    /**
     * receive the list of Commands
     */
    private Supplier<List<Command>> allCommandSupplier = () -> ServiceLocator.getCombatManager().getAllyCommandList();
    public List<Command> getAllCommand () { return allCommandSupplier.get(); }

    /**
     * supplies the active GameWorld
     */
    private Supplier<GameWorld> gameWorldSupplier = () -> ServiceLocator.getGameWorld();
    public GameWorld getGameWorld () { return gameWorldSupplier.get(); }
    /**
     * supplies the list of battle animations for the player
     */
    private Supplier<List<AnimationObject>> battlePlayerAnimationSupplier = () -> ServiceLocator.getCombatManager().getAlliesIdleAnimation();
    public List<AnimationObject> getBattlePlayerAnimation () { return battlePlayerAnimationSupplier.get(); }

    /**
     * supplies the list of battle animations for the enemy
     */
    private Supplier<List<AnimationObject>> battleEnemyAnimationSupplier = () -> ServiceLocator.getCombatManager().getEnemiesIdleAnimation();
    public List<AnimationObject> getBattleEnemyAnimation () { return battleEnemyAnimationSupplier.get(); }

    /**
     * supplies the list of health for the player during battle
     */
    private Supplier<List<Integer>> alliesBattleHealthSupplier = () -> ServiceLocator.getCombatManager().getAlliesHealth();
    public List<Integer> getAlliesHealth() { return alliesBattleHealthSupplier.get(); }

    /**
     * supplies the list of health for the enemy during battle
     */
    private Supplier<List<Integer>> enemyBattleHealthSupplier = () -> ServiceLocator.getCombatManager().getEnemiesHealth();
    public List<Integer> getEnemiesHealth () {
        //System.out.println("Health in controller " + enemyBattleHealthSupplier.get().get(0));
        return enemyBattleHealthSupplier.get(); }
    /**
     * supplies the dialogue box in the overworld
     */
    private Supplier<DialogueMenu> dialogueMenuSupplier = () -> myView.getMyView().getDialogueMenu();
    public DialogueMenu getDialogueMenu () { return  dialogueMenuSupplier.get(); }

    /**
     * set the BattleView in the front end
     */
    public void setBattleView() { myView.setBattleView(); }
    /**
     * add the dialogue in overworld
     */
    public void addDialogue(String m) { myView.getMyView().addDialogue(m); }
    /**
     * begin the animation for specific moves in battle view
     * @param moveAnimation the specific move animation
     * @param turn the turn of either the enemy or player
     */
    public void addMoveAnimation (AnimationObject moveAnimation, BattleTurn turn) {
        myView.getBattleView().playMoveAnimation(moveAnimation, turn);
    }
}
