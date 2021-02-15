package engine.backend;

import engine.backend.AnimationObject;
import engine.backend.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Interaction
 *
 * @author Michael Glushakov (mg367)
 * @author Max Bartlett (mmb70)
 */
public abstract class Interaction {
	Map<String, AnimationObject> animationMap;
	protected Map<String, Message> messageMap;
	String myName;

	//TODO: fill out defaults

	/**
	 * default constructor
	 */
	public Interaction() {

	}

	/**
	 * @param data:     JSON representation of data relevant to interaction
	 * @param messages: Map of messages called by interaction
	 */
	public Interaction(JSONObject data, Map<String, Message> messages) {
		animationMap = new HashMap<>();
		messageMap = messages;
		myName = (String) data.get("name");
		loadAnimationMap((JSONArray) data.get("animations"));
	}

	/**
	 * @return myName
	 */
	public String getName() {
		return myName;
	}

	/**
	 * sets images in animationMap
	 */
	public void setImages(int cellWidth,int cellHeight) {
		for (AnimationObject a : animationMap.values()) {
			a.setImage(cellWidth,cellHeight);
		}
	}

	/**
	 * Loads animations into animationMap
	 *
	 * @param data Assume animations look like this:
	 *             animations:[key:default, path:"/resource/charizard3.png"]
	 */
	private void loadAnimationMap(JSONArray data) {
		final String KEY = "key";
		final String PATH = "path";
		final String SPRITE_ROWS = "spriteRows";
		final String SPRITE_COLS = "spriteCols";
		for (int i = 0; i < data.size(); i += 1) {
			JSONObject animation = (JSONObject) data.get(i);
			animationMap.put((String) animation.get(KEY), new AnimationObject((String) animation.get(KEY), (String) animation.get(PATH), Integer.parseInt(String.valueOf(animation.get(SPRITE_ROWS))), Integer.parseInt(String.valueOf(animation.get(SPRITE_COLS)))));
		}
	}

	/**
	 * placeholder
	 */
	public void serialize() {

	}
}
