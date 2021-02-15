package engine.backend;

import java.util.ArrayList;
import java.util.List;

public class ActorManager {
	List<Actor> allActors;
	List<Actor> activeActors;
	List<Actor> inactiveActors;
	Actor myPlayerActor;

	/**
	 * @param actorList
	 */
	public ActorManager(List<Actor> actorList) {
		allActors = actorList;
		inactiveActors = new ArrayList<>();
		activeActors = allActors;
		setPlayerActor();
	}

	/**
	 * @return myPlayerActor
	 */
	public Actor getPlayerActor() {
		return myPlayerActor;
	}
    public List<Actor> getAllActors(){return allActors;}

	/**
	 * sets myPlayerActor
	 */
	private void setPlayerActor() {
		for (Actor actor : allActors) {
			if (actor.getIsPlayerActor()) {
				myPlayerActor = actor;
				break;
			}
		}
	}

	/**
	 * @return activeActors
	 */
	public List<Actor> getActiveActors() {
		return activeActors;
	}

	/**
	 * @return inactiveActors
	 */
	public List<Actor> getInactiveActors() {
		return inactiveActors;
	}

	/**
	 * @return list of an actor's AnimationObjects
	 */
	public List<AnimationObject> getAnimationObjects() {
		List<AnimationObject> activeAnimationObjects = new ArrayList<>();
		//System.out.println("ACTIVE ACTORS:"+activeActors.size());
		for (Actor actor : activeActors) {
			activeAnimationObjects.add(actor.getActiveAnimation());
		}
		return activeAnimationObjects;
	}

	/**
	 * changes an actor's state
	 *
	 * @param removeList list from which to remove the actor
	 * @param addList    list from which to add the actor
	 * @param actor      actor
	 */
	private void changeActorState(List<Actor> removeList, List<Actor> addList, Actor actor) {
		removeList.remove(actor);
		addList.add(actor);
	}

	/**
	 * @param actor actor to activate
	 */
	public void activate(Actor actor) {
		changeActorState(inactiveActors, activeActors, actor);
	}

	/**
	 * inactivates a given actor
	 *
	 * @param actor
	 */
	public void inactivate(Actor actor) {
		changeActorState(activeActors, inactiveActors, actor);
	}
}
