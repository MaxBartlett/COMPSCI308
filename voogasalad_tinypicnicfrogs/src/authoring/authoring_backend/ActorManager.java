package authoring.authoring_backend;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.backend.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Glushakov
 * Purpose: Contains a Map of all Actors in the game, all actor management done through this class
 */

public class ActorManager {
    Map<String, Actor> actorMap;
    List <ObservableActor>observableActors;
    protected ActorManager(){

        actorMap = new HashMap<>();
        observableActors=new ArrayList<>();
    }

    /**
     *
     * @param actor already existing actor
     * @param id id under which to add the actor
     * @return actor id
     */
    private String addActor(Actor actor, String id){
        actorMap.put(id,actor);
        observableActors.add(actor.getObservableActor());
        return id;
    }

    /**
     *
     * @param id id of actor in the map
     * @return Actor associated with a
     */
    protected Actor getActor(String id){
        return actorMap.get(id);
    }

    /**
     * Saves all actors
     * @param path: path of the folder where to store actors
     */
    protected void serializeAllActors(String path)throws SaveException{
        XStream serializer = new XStream(new DomDriver());
        serializer.omitField(AnimationObject.class,"animationView");
        System.out.println(actorMap);
        String serialized= serializer.toXML(actorMap);

        try{
            Files.write(Paths.get(path+"actors.xml"),serialized.getBytes());}catch (IOException e){throw new SaveException();}

    }

    /**
     * Creates actor from prototype
     * @param actorPrototype
     * @param x
     * @param y
     * @param z
     */
    protected void createActor(ActorPrototype actorPrototype, int x, int y, int z){
        Actor actor= new Actor(actorPrototype,x,y,z);
        addActor(actor,actorPrototype.getName()+x+"-"+y+"-"+z);

    }

    /**
     * @param id Serializes an actor with a particular id
     */
    protected void serializeActor(String id){
        actorMap.get(id).serialize();
    }

    /**
     * Loads ActorMap from an XML file
     * @param path
     */
    protected void loadActors(String path){


        XStream serializer = new XStream(new DomDriver());
        Map<String,Actor>loadedMap=(Map<String, Actor>) serializer.fromXML(Paths.get(path).toFile());
        actorMap.putAll(loadedMap);
        for(Actor a:loadedMap.values()){
            a.setImageAuthoring();
            observableActors.add(a.getObservableActor());}

    }

    /**
     * removes actor with a particular id
     * @param uniqueID id of the actor in the map
     */
    protected void deleteActor(String uniqueID){
        actorMap.remove(uniqueID);
    }

    protected List<ObservableActor> getObservableList(){
        return observableActors;
    }


}
