package engine.backend;


import engine.backend.Commands.*;
import engine.backend.gameevent.AnimationFinishedEvent;
import engine.backend.gameevent.GameEvent;
import engine.backend.gameevent.GameKeyEvent;
import engine.backend.gameevent.GameMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;


/**
 * Holds information and methods that are used for the entire game, including collision detection
 * and launching interactions
 *
 * @author Christopher Lin cl349
 */
public class GameWorld {

    private static int myMapHeight;
    private static int myMapWidth;
    private GameState myGameState;

    private Map<KeyCode, Command> keyMap;

    /**
     *
     * @param mapHeight height of the game world
     * @param mapWidth width of the game world
     */
    public GameWorld(int mapHeight, int mapWidth){
        System.out.println("Game world size: " + mapHeight + ", " + mapWidth);
        myMapHeight = mapHeight;
        myMapWidth = mapWidth;
        myGameState = GameState.Overworld;
        keyMap = new HashMap<>();
        var myPlayer = ServiceLocator.getActorManager().getPlayerActor();
//        var overWorldView = ServiceLocator.getController().getOverWorldView();

        //Default keybinds. TODO: let these be player controlled
        var PlayerMoveUp = new MoveUpCommand();
        PlayerMoveUp.bind(myPlayer);
        var PlayerMoveDown = new MoveDownCommand();
        PlayerMoveDown.bind(myPlayer);
        var PlayerMoveLeft = new MoveLeftCommand();
        PlayerMoveLeft.bind(myPlayer);
        var PlayerMoveRight = new MoveRightCommand();
        PlayerMoveRight.bind(myPlayer);

        keyMap.put(KeyCode.W, PlayerMoveUp);
        keyMap.put(KeyCode.A, PlayerMoveLeft);
        keyMap.put(KeyCode.S, PlayerMoveDown);
        keyMap.put(KeyCode.D, PlayerMoveRight);
    }

    public int getMapHeight(){
        return myMapHeight;
    }
    public int getMapWidth(){
        return myMapWidth;
    }

    /**
     * is called every turn by the frontend. Executes all tasks that need to happen every tick.
     */
    public void onTick(){
        //System.out.println(ServiceLocator.getActorManager().getPlayerActor().getCoordinate().getX());
        if(myGameState == GameState.Overworld){
            detectCollisions();
        }
        if(myGameState == GameState.Combat){
            ServiceLocator.getCombatManager().combatTick();
        }
    }

    /**
     * Called by frontend on update cycle to detect collisions and trigger the
     * correct interactions
     */
    public void detectCollisions(){
        var actorList = ServiceLocator.getActorManager().getActiveActors();
        var collisionList = new ArrayList<Actor>();
        var playerActor = ServiceLocator.getActorManager().getPlayerActor();
        for(Actor a : actorList){
            if(overlaps(a, playerActor)){
                //TODO: get rid of magic values
                collisionList.add(a);
                if(a.getInteraction() instanceof  BackgroundInteraction){
                    if(((BackgroundInteraction) a.getInteraction()).isCanPassThrough()){
                        continue;
                    }
                }
                rebound(playerActor);
            }
        }
        for(Actor c : collisionList){

            if(!(c.getInteraction() instanceof BackgroundInteraction)){
                System.out.println("COLLIDE LAUNCH");
                System.out.println(c.getInteraction());
            }
            launchInteraction(c.getInteraction());
            //Delete actors you collide with
            //TODO: fix this shit
            //ServiceLocator.getActorManager().inactivate(c);
        }
        collisionList.clear();
    }

    /**
     * Moves the player actor outside of the bounds whatever it's colliding with.
     * @param playerActor the player actor
     */
    private void rebound(Actor playerActor) {
        System.out.println("REBOUND");
        if(playerActor.getHeading() == Heading.LEFT){
            playerActor.getCoordinate().setX(playerActor.getCoordinate().getX()+30);
        }
        else if(playerActor.getHeading() == Heading.RIGHT){
            playerActor.getCoordinate().setX(playerActor.getCoordinate().getX()-30);
        }
        else if(playerActor.getHeading() == Heading.UP){
            playerActor.getCoordinate().setY(playerActor.getCoordinate().getY()+30);
        }
        else if(playerActor.getHeading() == Heading.DOWN){
            playerActor.getCoordinate().setY(playerActor.getCoordinate().getY()-30);
        }
    }

    /**
     * Launches interaction attached to the non-player actor
     * @param interaction interaction of non-player actor
     */
    private void launchInteraction(Interaction interaction){
        if(interaction instanceof CombatInteraction) {
            launchCombatInteraction((CombatInteraction) ServiceLocator.getActorManager().getPlayerActor().getInteraction(), (CombatInteraction) interaction);
        }
        if(interaction instanceof DialogueInteraction) {
            launchDialogueInteraction((DialogueInteraction) interaction);
        }
        if(interaction instanceof  BackgroundInteraction){
            ServiceLocator.getBroadcaster().broadcast((interaction.messageMap.get("ontouch")));
        }
    }

    /**
     * Launches a combat interaction. For now we only handle 2 player combats
     * @param playerInteraction The player's combatInteraction
     * @param enemyInteraction the enemy's CombatInteraction
     */
    private void launchCombatInteraction(CombatInteraction playerInteraction, CombatInteraction enemyInteraction){
        myGameState = GameState.Combat;
        var alliesList = new ArrayList<CombatInteraction>();
        alliesList.add(playerInteraction);
        var enemyList = new ArrayList<CombatInteraction>();
        enemyList.add(enemyInteraction);
        var combatMan = new CombatManager(alliesList, enemyList, new LowestHealthFirstInitiative());
        ServiceLocator.provideCombatManager(combatMan);
        ServiceLocator.getController().setBattleView();
        combatMan.nextTurn();
        //combatMan.nextTurn();
    }


    /**
     * Launches a new dialogue interaction. Currently unused as authoring cannot create dialogues.
     * @param dialogueInteraction the dialogueInteraction
     */
    private void launchDialogueInteraction(DialogueInteraction dialogueInteraction) {
        dialogueInteraction.setMenu();
    }

    /**
     * An event bus that handles all GameEvents that are thrown
     * @param e The event
     */
    public void handleInput(GameEvent e){
        if(e instanceof GameKeyEvent){
            handleKeyEvent((GameKeyEvent) e);
        }
        if(e instanceof GameMenuEvent){
            handleMenuEvent((GameMenuEvent) e);
        }
        if(e instanceof AnimationFinishedEvent && myGameState == GameState.Combat){
            ServiceLocator.getCombatManager().releaseAnimationLock();
        }
    }

    /**
     * Directs menu events to the proper receiver
     * @param e the event
     */
    private void handleMenuEvent(GameMenuEvent e){
        if(myGameState == GameState.Combat){
            ServiceLocator.getCombatManager().receiveInput(e);
        }
        if(myGameState == GameState.Overworld){
            e.getOption().execute(null);
        }

    }

    /**
     * Directs key events to the proper receiver
     * @param e the event
     */
    private void handleKeyEvent(GameKeyEvent e){
        final int DEFAULT_MOVE_AMOUNT = 20;
        var defaultParams = new ArrayList<>();
        defaultParams.add(DEFAULT_MOVE_AMOUNT);
        if(keyMap.containsKey(((KeyEvent) e.getEvent()).getCode())){
            keyMap.get(((KeyEvent) e.getEvent()).getCode()) .execute(defaultParams);
        }
    }


    /**
     * Switches the game state to overworld mode
     */
    public void activateOverWorld(){
        myGameState = GameState.Overworld;
        //ServiceLocator.getController().setWorldView();
    }

    /**
     * Gets the current gamestate
     * @return GameState attached to this GameWorld
     */
    public GameState getGameState(){
        return myGameState;
    }

    /**
     * Checks if two actors have overlapping bounds
     * @param a1 first actor
     * @param a2 second actor
     * @return whether or not they are overlapping
     */
    private boolean overlaps(Actor a1, Actor a2){
        var a1Coordinate = a1.getCoordinate();
        var a1Bounds = a1.getBounds();
        int a1MaxX = a1Coordinate.getX()+a1Bounds.getRelX()+a1Bounds.getWidth();
        int a1MinX = a1Coordinate.getX()+a1Bounds.getRelX();
        int a1MaxY = a1Coordinate.getY()+a1Bounds.getRelY()+a1Bounds.getHeight();
        int a1MinY = a1Coordinate.getY()+a1Bounds.getRelY();
        var a2Coordinate = a2.getCoordinate();
        var a2Bounds = a2.getBounds();
        int a2MaxX = a2Coordinate.getX()+a2Bounds.getRelX()+a2Bounds.getWidth();
        int a2MinX = a2Coordinate.getX()+a2Bounds.getRelX();
        int a2MaxY = a2Coordinate.getY()+a2Bounds.getRelY()+a2Bounds.getHeight();
        int a2MinY = a2Coordinate.getY()+a2Bounds.getRelY();

        boolean xIntersects = (a1MaxX > a2MinX && a1MaxX < a2MaxX) || (a2MaxX > a1MinX && a2MaxX < a1MaxX);
        boolean yIntersects = (a1MaxY > a2MinY && a1MaxY < a2MaxY) || (a2MaxY > a1MinY && a2MaxY < a1MaxY);
        // check for hit on the upper edge

        return(xIntersects && yIntersects);
    }




}
