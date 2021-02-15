package engine.frontend.game_engine_UI;

import engine.frontend.game_engine_UI.BattleWorld.BattleView;
import engine.frontend.game_engine_UI.OverWorld.OverWorldView;
import engine.controller.Controller;
import engine.frontend.game_engine_UI.SplashScreen.FightScene;
import javafx.stage.Stage;

/**
 * class that manages the switching between different views. There are two functions setWorldView
 * and setBattleView that will be called by the back end to change the scenes
 *
 * @author Duy Trieu (dvt5)
 */
public class StateView {
    private Controller myController;
    private OverWorldView myView;
    private BattleView battleView;
    private Stage myStage;

    /**
     * @param stage
     */
    public StateView(Stage stage) {
        this.myStage = stage;
        myController = new Controller(this);
        myView = new OverWorldView(myController);
        setUpView();
        setUpStage();
    }
    /**
     * set the init view of the game
     */
    private void setUpView () {
        myStage.setScene(myView.getMyScene());
    }
    /**
     * set the battle view as the main view
     */
    public void setBattleView () {
        var fightScene = new FightScene(myController);
        battleView = new BattleView(myController);
        fightScene.setNextSceneHandler(()->{myStage.setScene(battleView.getMyScene());});
        myStage.setScene(fightScene.getMyScene());
    }
    private void setUpStage () {
        myStage.setTitle("VoogaSalad");
        myStage.setMinWidth(600);
        myStage.setMinHeight(300);
        myStage.setScene(myView.getMyScene());
        myStage.show();
    }
    /**
     * @return myView
     */
    public OverWorldView getMyView () {
        return myView;
    }
    /**
     * @return battleView
     */
    public BattleView getBattleView () {
        return battleView;
    }
    /**
     * @return myStage
     */
    public Stage getMyStage () { return myStage; }

}
