package engine.backend;

import engine.backend.Commands.CombatMove;
import engine.backend.Commands.Command;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author Michael Glushakov
 * Purpose: Subclass of interraction managing combat
 * Dependencies: Interaction
 */
public class CombatInteraction extends Interaction{
    AnimationObject myCombatIdleAnimation;
    List<Command>commandList;
    int myHealth;


    public CombatInteraction(){
        super();
    }

    /**
     * @return idle animation object
     */
    public AnimationObject getCombatIdleAnimation(){
        return myCombatIdleAnimation;
    }

    public List<Command> getCommandList(){
        return commandList;
    }

    /**
     *  Creates combatinterraction from a JSON
     * @param data JSON representation of interaction info
     * @param messages messages pretaining to interraction
     */
    public CombatInteraction(JSONObject data, Map<String, Message> messages){
        super(data,messages);
        commandList= new ArrayList<>();
        parseMoves((JSONArray)data.get("moves"));
        myCombatIdleAnimation=animationMap.get("default");
        System.out.println(myCombatIdleAnimation);
        myHealth = 1;
        //TODO: Test value
    }

    /**
     *  Sets health by amount specified
     * @param amt amount specified
     */
    public void setHealth(int amt){
        myHealth = amt;
    }

    /**
     *
     * @return health
     */
    public int getHealth(){
        return myHealth;
    }

    /**
     * Logic to parse all moves
     * @param movesArr
     */
    private void parseMoves(JSONArray movesArr){
        for(int i=0;i<movesArr.size();i+=1){
            CombatMove commandMove = new CombatMove((JSONObject)movesArr.get(i));
            commandList.add(commandMove);
        }
    }

    /**
     * @deprecated used for testing purposes
     */
    @Override
    public void serialize(){
        super.serialize();
        for(Command c:commandList){c.serialize();}
    }

    /**
     *  Sets imageviews of combat animation
     * @param cellWidth
     * @param cellHeight
     */
    @Override
    public void setImages(int cellWidth,int cellHeight){
        super.setImages(cellWidth,cellHeight);
        for(Command c:commandList){
            if(CombatMove.class.isInstance(c)){
                CombatMove combatMove=(CombatMove)c;
                combatMove.setImages(cellWidth,cellHeight);
            }}
        myCombatIdleAnimation=super.animationMap.get("idle");
    }
}
