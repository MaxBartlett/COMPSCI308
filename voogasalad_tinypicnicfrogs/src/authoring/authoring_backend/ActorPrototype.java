package authoring.authoring_backend;

import engine.backend.*;
import engine.backend.CombatInteraction;
import engine.backend.Interaction;
import javafx.scene.image.Image;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Glushakov
 * @author brookekeene 
 * Purpose: contains actor data without world coordinates
 * Dependencies: Interaction
 */
public class ActorPrototype {

	private Map<String, String> animationMap;
	private Map<String, int[]> spriteDimensionsMap;
	private Map<String, Interaction> interactionMap;
	private List<Message> activateMessagesList;
	private List<Message> deactivateMessagesList;
	private Map<String, Integer> myStats;
	private String name;
	private Bounds myBound;
	private boolean isPlayer;
	private DialogueTreeNode myDialogue;

	/**
	 * @param data              JSON representation of the prototype
	 * @param prototypeMessages List that maps interactions to messages it sends
	 */

	//NOTE: dialogtreeNode is null for parse interactions, which means
	protected ActorPrototype(JSONObject data, List<Map<String, Message>> prototypeMessages, List<Message> activateMessages, List<Message> deactivateMessages,
							 DialogueTreeNode dialogueRoot) {
		name = (String) data.get("name");
		spriteDimensionsMap = new HashMap<>();
		animationMap = parseAnimations(data);
		myStats = parseStats((JSONArray) data.get("stats"));
		interactionMap = new HashMap<>();
		myBound = parseBounds((JSONObject) data.get("bounds"));
		activateMessagesList= new ArrayList<>(activateMessages);
		deactivateMessagesList = new ArrayList<>(deactivateMessages);
		myDialogue = dialogueRoot;
		parseInteractions((JSONArray) data.get("interactions"), prototypeMessages);
		isPlayer = (boolean) data.get("isPlayer");
	}

	/**
	 * Constructor used for cloning prototype
	 *
	 * @param animationMapP
	 * @param interactionMapP
	 * @param statsMap
	 * @param nameP
	 */
	protected ActorPrototype(Map<String, String> animationMapP, Map<String, Interaction> interactionMapP,
							 Map<String, Integer> statsMap, String nameP, boolean player, Bounds bounds, Map<String, int[]> dimensionMap, List<Message> activateMessages,
							 List<Message> deactivateMessages, DialogueTreeNode dialogueRoot) {
		animationMap = new HashMap<>(animationMapP);
		interactionMap = new HashMap<>(interactionMapP);
		myStats = statsMap;
		name = nameP;
		isPlayer = player;
		myBound = bounds;
		spriteDimensionsMap = new HashMap<>(dimensionMap);
		activateMessagesList =new ArrayList<>(activateMessages) ;
		deactivateMessagesList =new ArrayList<>(deactivateMessages) ;
		myDialogue = dialogueRoot;

	}

	public String getName() {
		return name;
	}

	/**
	 * @param data: the original JSON of the entire prototype
	 * @return the List of Animations for this ActorPrototype in overworld
	 */
	private Map<String, String> parseAnimations(JSONObject data) {
		JSONArray animations = (JSONArray) data.get("animations");
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < animations.size(); i += 1) {
			JSONObject animation = (JSONObject) animations.get(i);
			map.put((String) animation.get("key"), (String) animation.get("path"));
			int[] dimensions = new int[2];
			System.out.println(animation.toJSONString());
			dimensions[0] = Integer.parseInt(String.valueOf(animation.get("spriteRows")));
			dimensions[1] = Integer.parseInt(String.valueOf(animation.get("spriteCols")));
			spriteDimensionsMap.put((String) animation.get("key"), dimensions);
		}
		return map;
	}

	private Map<String, Integer> parseStats(JSONArray stats) {
		Map<String, Integer> statsMap = new HashMap<>();
		for (int i = 0; i < stats.size(); i += 1) {
			JSONObject stat = (JSONObject) stats.get(i);
			statsMap.put((String) stat.get("key"), Integer.parseInt(String.valueOf(stat.get("value"))));
		}
		return statsMap;

	}

	private void parseInteractions(JSONArray data, List<Map<String, Message>> prototypeMessages) {
		for (int i = 0; i < data.size(); i += 1) {
			parseInteraction((JSONObject) data.get(i), prototypeMessages.get(i));
		}
	}

	/**
	 * Constructs the interraction object
	 *
	 * @param interactionJSON:      JSON or interraction related data
	 * @param interactionMessages: messages that this interraction fires
	 */
	private void parseInteraction(JSONObject interactionJSON, Map<String, Message> interactionMessages) {
		Interaction myInteraction;
		if (((String) interactionJSON.get("type")).equalsIgnoreCase("Combat")) {
			myInteraction = new CombatInteraction(interactionJSON, interactionMessages);
			interactionMap.put(myInteraction.getName(), myInteraction);
		} else if (((String) interactionJSON.get("type")).equalsIgnoreCase("Background")) {
			myInteraction = new BackgroundInteraction(interactionJSON, interactionMessages);
			interactionMap.put(myInteraction.getName(), myInteraction);
		}
		else if(((String)interactionJSON.get("type")).equalsIgnoreCase("Dialogue")){
			myInteraction = new DialogueInteraction(myDialogue);
			interactionMap.put(myInteraction.getName(), myInteraction);
		}

	}

	/**
	 * @param boundsJSON JSON of bounds
	 * @return Bounds object
	 */
	private Bounds parseBounds(JSONObject boundsJSON) {
		int relX = Integer.parseInt(String.valueOf(boundsJSON.get("relX")));
		int relY = Integer.parseInt(String.valueOf(boundsJSON.get("relY")));
		int width = Integer.parseInt(String.valueOf(boundsJSON.get("width")));
		int height = Integer.parseInt(String.valueOf(boundsJSON.get("height")));

		return new Bounds(width, height, relX, relY);
	}

	/**
	 * For testing purposes
	 */
	public void serialize() {
		System.out.println(name);
		for (String s : animationMap.keySet()) {
			System.out.print(s + ":" + animationMap.get(s) + " | ");
		}
		System.out.println(" ");
		for (String s : myStats.keySet()) {
			System.out.println(s + ": " + myStats.get(s));
		}
		for (String s : interactionMap.keySet()) {
			System.out.println(s);
			interactionMap.get(s).serialize();
		}
	}

	/**
	 * @return the new instance of the actor prototype
	 */
	protected ActorPrototype clone() {
		return new ActorPrototype(animationMap, interactionMap, myStats, name, isPlayer, myBound, spriteDimensionsMap,
				activateMessagesList, deactivateMessagesList, myDialogue);
	}

	/**
	 * Used by Actor when instantiating from prototype
	 *
	 * @return animation map
	 */
	public Map<String, String> getAnimationMap() {
		return animationMap;
	}

	/**
	 * Used by Actor when instantiating from prototype
	 *
	 * @return interaction map
	 */
	public Map<String, Interaction> getInteractionMap() {
		return interactionMap;
	}

	/**
	 * Used by Actor when instantiating from prototype
	 *
	 * @return stats map
	 */
	public Map<String, Integer> getMyStats() {
		return myStats;
	}

	/**
	 * @return isPlayer
	 */
	public boolean getIsPlayer() {
		return isPlayer;
	}

	/**
	 * @return Bounds
	 */
	public Bounds getBounds() {
		return myBound;
	}

	/**
	 * @return ObservablePrototype
	 */

	public ObservablePrototype getObservablePrototype() {
//		System.out.println(interactionMap.get("idle").getName());
		Image img = new Image((this.getClass().getClassLoader().getResourceAsStream(animationMap.get("idle"))));
		if(interactionMap.get("idle") == null){
			System.out.println("nul");
		}
		return new ObservablePrototype(getName(), img, interactionMap.get("idle").getClass().isInstance(BackgroundInteraction.class));

	}

	/** 
	 * @return spriteDimensionsMap
	 */
	public Map<String, int[]> getSpriteDimensionsMap() {
		return spriteDimensionsMap;
	}

	public List<Message>getActivateMessagesList(){return activateMessagesList;}
	public List <Message>getDeactivateMessagesList(){return deactivateMessagesList;}

}
