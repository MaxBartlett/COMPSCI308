package engine.backend.Commands;

import engine.backend.AnimationObject;
import engine.backend.CombatInteraction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Michael Glushakov
 * Purpose: CombatMove is what is called in Combat to modify health/other statistics
 * Dependencies: Interaction
 */
public class CombatMove extends Command {
    private String stat;
    private int targetActorNum;
    private int targetValue;
    private enum targetActor {ENEMY, FRIEND};
    private enum targetType {CONSTANT,PERCENTAGE};
    private targetType myTargetType;
    private targetActor myTargetActor;

    private Map<String, AnimationObject> myAnimationMap;


    /**
     *
     * @param params JSON representation of parameters needed to create combat move
     */
    public CombatMove(JSONObject params){
        super((String)params.get("name"));
        stat=(String)params.get("targetStat");
        targetActorNum=Integer.parseInt(String.valueOf(params.get("targetActorNumber")));
        targetValue=Integer.parseInt(String.valueOf(params.get("targetValue")));
        myTargetType=parseTargetType((String)params.get("targetType"));
        myTargetActor=parseTargetActor((String)params.get("targetActorType"));
        myAnimationMap=parseAnimationMap((JSONArray)params.get("animations"));


    }

    /**
     *
     * @param key animation key
     * @return animationObject associated with the key
     */
    public AnimationObject getAnimation(String key){
        return myAnimationMap.get(key);
    }

    /**
     * @param params A list of parameters
     * executes the move
     */
    @Override
    public void execute(List<Object> params) {
        if(myTarget.getClass() == CombatInteraction.class){
            int currentHealth = ((CombatInteraction) myTarget).getHealth();
            ((CombatInteraction) myTarget).setHealth(calculateHealth(currentHealth));
        }
    }

    /**
     * Ideally would have been replaced by a more abstract algorithm, but because of other issues decided to only focuse om modifying health
     * @param health
     * @return new health value
     */
    private int calculateHealth(int health){
        if(myTargetType==targetType.CONSTANT){
            health-=targetValue;
        }
        else if (myTargetType==targetType.PERCENTAGE){
            health=(int)((100-targetValue)*health/100);
        }
        return health;
    }
    private targetType parseTargetType(String value){
        if(value.equals("constant"))return targetType.CONSTANT;
        else if(value.equals("percent"))return targetType.PERCENTAGE;
        else throw new IllegalArgumentException("invalid target type");
    }

    private targetActor parseTargetActor(String value){
        if(value.equals("friend"))return targetActor.FRIEND;
        else if(value.equals("enemy"))return targetActor.ENEMY;
        else throw new IllegalArgumentException("invalid target type");
    }

    public Map<String,AnimationObject>parseAnimationMap(JSONArray arr){
        Map<String,AnimationObject>animationMap=new HashMap<>();
        for(int i=0;i<arr.size();i+=1){
            JSONObject animation=(JSONObject)arr.get(i);
            animationMap.put((String)animation.get("key"),new AnimationObject((String)animation.get("key"),(String)animation.get("path"),Integer.parseInt(String.valueOf(animation.get("spriteRows"))),Integer.parseInt(String.valueOf(animation.get("spriteCols")))));
        }
        return animationMap;
    }

    /**
     * for testing purposes
     */
    @Override
    public void serialize(){
        //System.out.println(super.myName+": "+"stat:"+stat+", value: "+targetValue+","+", number:"+targetActorNum+ ", type: "+myTargetType.toString()+", actor: "+myTargetActor.toString());
    }

    /**
     * Sets Move's images
     * @param cellWidth
     * @param cellHeight
     */
    public void setImages(int cellWidth, int cellHeight){
        for(AnimationObject a:myAnimationMap.values()){
            a.setImage(cellWidth,cellHeight);
        }
    }
}
