package engine.backend;

import engine.backend.Commands.CombatMove;

import engine.backend.Commands.Command;
import engine.backend.gameevent.GameMenuEvent;
import engine.backend.gameevent.InputSource;
import engine.controller.Controller;
import engine.frontend.game_engine_UI.SplashScreen.Loss;
import engine.frontend.game_engine_UI.SplashScreen.Victory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Manages the combat state (taking turns, etc). Combat is able to proceed to the next step when
 * turnLock and animationLock are released. turnLock is released when either the player or the AI gives input
 * to combatManager. animationLock is released when the frontend finishes playing the combat animation.
 *
 * @author Christopher Lin cl349
 */
public class CombatManager {
    List<CombatInteraction> myAllies;
    List<CombatInteraction> initEnemies;
    List<CombatInteraction> myEnemies;
    AI myAI;
    private List<Turn> turnList;
    boolean turnLock;
    boolean animationLock;
    CombatMove nextMove;
    InputSource nextSource;


    /**
     *
     * @param allies List of CombatInteractions representing allies
     * @param enemies .. representing enemies
     * @param initiativeComparator A comparator used to sort the Turn list
     */
    CombatManager(List<CombatInteraction> allies, List<CombatInteraction> enemies, Comparator<Turn> initiativeComparator){
        myAllies = allies;
        myEnemies = enemies;
        initEnemies = new ArrayList<>();
        initEnemies.addAll(enemies);
        turnList = new ArrayList<>();
        turnLock = true;
        for(CombatInteraction a : myAllies){
            turnList.add(new PlayerTurn(a));
        }
        for(CombatInteraction e: enemies){
            turnList.add(new AITurn(e));
        }
        turnList.sort(initiativeComparator);
        nextMove = null;

    }

    /**
     *
     * @return Gets list of commands that allies can execute
     */
    public List<Command> getAllyCommandList() {
        return myAllies.get(0).getCommandList();
    }


    /**
     * Call this method every cycle during combat. This will advance the combat state when it is ready
     */
    public void combatTick(){
        if(!turnLock && !animationLock){
            //nextMove.execute(null);
            nextTurn();
        }
    }

    /**
     * This releases the lock and allows combat to move on
     *
     * @param e the GameMenuEvent to handle
     */
    public void receiveInput(GameMenuEvent e){
        if(e.getSource() == nextSource && myEnemies.size() > 0 && myAllies.size() > 0){
            System.out.println(e.getSource());
            if(e.getSource() == InputSource.PLAYER){
                e.getOption().bind(myEnemies.get(0));
                animationLock = true;
                var anim = ((CombatMove) e.getOption()).getAnimation("idle");
                ServiceLocator.getController().addMoveAnimation(anim, BattleTurn.Player);
            }
            else if(e.getSource() == InputSource.AI){
                animationLock = true;
                e.getOption().bind(myAllies.get(0));
                var anim = ((CombatMove) e.getOption()).getAnimation("idle");

                ServiceLocator.getController().addMoveAnimation(anim, BattleTurn.Enemy);

            }

            e.getOption().execute(null);

            turnLock = false;
        }
    }

    /**
     * Releases the AnimationLock so that combat can move to next turn
     */
    public void releaseAnimationLock(){
        animationLock = false;
    }


    /**
     * Runs the next turn of the combat
     */
    public void nextTurn(){
        turnLock = true;
        //run the current turn and put it on the end of the queue
        turnList.add(turnList.remove(0));
        //remove dead
        List<CombatInteraction> deadList = new ArrayList<>();
        for(CombatInteraction a : myAllies){
            System.out.println(a.getHealth());
            if(a.getHealth() <= 0){
                deadList.add(a);
            }
        }
        myAllies.removeAll(deadList);
        deadList.clear();
        for(CombatInteraction e : myEnemies){
            System.out.println(e.getHealth());
            if(e.getHealth() <= 0){
                deadList.add(e);
            }
        }
        myEnemies.removeAll(deadList);
        if(myAllies.size() < 1){
            playerDefeat();
        }
        else if(myEnemies.size()< 1){
            playerVictory();
        }

        nextSource = turnList.get(0).getExpectedSource();
        turnList.get(0).initializeTurn();
    }


    /**
     * Executes when the player loses a combat
     */
    private void playerDefeat() {
        System.out.println("PLAYER LOST");
        ServiceLocator.getGameWorld().activateOverWorld();
        Controller controller = ServiceLocator.getController();
        var loss = new Loss(controller);
        loss.setNextSceneHandler(() -> {controller.getStage().setScene(controller.getOverWorldView().getMyScene());});
        controller.getStage().setScene(loss.getMyScene());
    }

    /**
     * Triggers the player victory code.
     */
    private void playerVictory(){
        System.out.println("PLAYER WON");
        ServiceLocator.getGameWorld().activateOverWorld();
        Controller controller = ServiceLocator.getController();
        var win = new Victory(controller);
        win.setNextSceneHandler(() -> {controller.getStage().setScene(controller.getOverWorldView().getMyScene());});
        controller.getStage().setScene(win.getMyScene());
        for(CombatInteraction enemy : initEnemies){
            System.out.println(enemy.messageMap);
            ServiceLocator.getBroadcaster().broadcast(enemy.messageMap.get("ondefeat"));
        }
        ServiceLocator.getGameWorld().activateOverWorld();
    }

    /**
     * Return the allies health
     * @return List of allied health values
     */
    public List<Integer> getAlliesHealth(){
        var healthList = new ArrayList<Integer>();
        for(CombatInteraction a : myAllies){
            healthList.add(a.getHealth());
        }
        return healthList;
    }

    /**
     * Return the list of enemy healths
     * @return List of enemy health values
     */
    public List<Integer> getEnemiesHealth(){
        var healthList = new ArrayList<Integer>();
        for(CombatInteraction a : myEnemies){
            healthList.add(a.getHealth());
        }

        return healthList;
    }

    /**
     * Gets the idle animation objects for allies in the combat
     * @return list of ally animations
     */
    public List<AnimationObject> getAlliesIdleAnimation(){
        var animationList = new ArrayList<AnimationObject>();
        for(CombatInteraction a : myAllies){
            animationList.add(a.getCombatIdleAnimation());
        }
        return animationList;
    }


    /**
     * Gets the list of enemy idle animations
     * @return list of enemy idle animations
     */
    public List<AnimationObject> getEnemiesIdleAnimation(){
        var animationList = new ArrayList<AnimationObject>();
        for(CombatInteraction a : myEnemies){
            animationList.add(a.getCombatIdleAnimation());
        }
        //System.out.println(animationList.size());
        return animationList;
    }
}
