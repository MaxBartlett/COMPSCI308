package engine.frontend.game_engine_UI.AnimationProcesser;

import engine.backend.AnimationObject;

import java.util.Collection;
import java.util.HashMap;

/**
 * AnimationManager class that takes in a list of AnimationObject and
 * process the sprites for each of them
 *
 * @author Duy Trieu (dvt5)
 */
public class AnimationManager {
    private Collection<AnimationObject> animationObjects;
    private HashMap<AnimationObject, Sprite[]> spriteMap;

    /**
     * @param objects
     */
    public AnimationManager(Collection<AnimationObject> objects) {
        animationObjects = objects;
        spriteMap = new HashMap<>();
        for (AnimationObject object : objects) {
            SpriteProcessor processor = new SpriteProcessor(object.getAnimationView().getImage(), object.getSpriteRows(), object.getSpiteCols());
            spriteMap.put(object, processor.getViewList());
        }

    }
    /**
     * @return spriteMap
     */
    public HashMap<AnimationObject, Sprite[]> getSpriteMap () {
        return spriteMap;
    }
}
