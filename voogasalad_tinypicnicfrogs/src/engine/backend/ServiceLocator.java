package engine.backend;

import engine.controller.Controller;

/**
 * Follows the Service Locator design pattern. Used to locate global objects
 *
 * @Christopher Lin cl349
 */
public class ServiceLocator {


    private static GameWorld myGameWorld;
    private static AI myAI;
    private static ActorManager myActorManager;
    private static CombatManager myCombatManager;
    private static Controller myController;
    private static Broadcaster myBroadcaster;


    /**
     *
     * @return the GameWorld currently in use
     */
    public static GameWorld getGameWorld(){
        if(myGameWorld == null){
            provideGameWorld(new GameWorld(0, 0));
        }
        return myGameWorld;
    }

    /**
     *
     * @return the Broadcaster currently in use
     */
    public static Broadcaster getBroadcaster(){
        return myBroadcaster;
    }

    /**
     * Sets the GameWorld used by the game
     * @param gameWorld the GameWorld object to be returned by getter
     */

    public static void provideGameWorld(GameWorld gameWorld){
        myGameWorld = gameWorld;
    }


    /**
     *
     * @return the AI currently in use
     */
    public static AI getAI(){
        if(myGameWorld == null){
            provideAI(new RandomAI());
        }
        return myAI;
    }

    /**
     *
     * @return the ActorManager currently in use
     */
    public static ActorManager getActorManager(){
        if(myActorManager == null){
//            System.out.println("NULL");
            provideActorManager(new ActorManager(null));
        }
        return myActorManager;
    }

    /**
     *
     * @return the combatManager in use
     */
    public static CombatManager getCombatManager(){


        return myCombatManager;
    }

    /**
     *
     * @return the current controller in use
     */
    public static Controller getController() {
        return myController;
    }

    /**
     * Provides a combatManager to serve out
     * @param combatManager combatManager to serve
     */
    public static void provideCombatManager(CombatManager combatManager){
        System.out.println("providing: " + combatManager);
        myCombatManager = combatManager;
    }

    /**
     * Provides an ActorManager
     * @param actorManager ActorManager to serve
     */
    public static void provideActorManager(ActorManager actorManager){myActorManager = actorManager;}

    /**
     * Provides a Controller to serve out
     * @param controller Controller to serve
     */
    public static void provideController(Controller controller){myController = controller;}

    /**
     * Provides a combatManager to serve out
     * @param br Broadcaster to serve
     */
    public static void provideBroadcaster(Broadcaster br){myBroadcaster = br;};

    /**
     * TODO: set null default object
     * Sets the AI
     * @param ai the AI to be served
     */

    public static void provideAI(AI ai){
        myAI = ai;
    }


}
