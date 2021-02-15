package authoring.authoring_backend;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import engine.backend.Actor;
import engine.backend.AnimationObject;
import engine.backend.DialogueTreeNode;
import engine.backend.Message;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael GLushakov
 * Purpose: manages Actor Prototypes created by game developer
 */
public class ActorPrototypeManager {
    private Map<String,ActorPrototype>actorPrototypeMap;
    private List<ObservablePrototype>prototypeList;
    protected ActorPrototypeManager(){
        actorPrototypeMap= new HashMap<>();
        prototypeList= new ArrayList<>();
    }

    /**
     *
     * @param data JSON representation of data entered by autjor
     * @param prototypeMessages: parsed out messages relevant to each interraction: Each spot is the list is a Map in of the Messages pertaining to the interaction
     */
    protected void createActorPrototype(JSONObject data, List<Map<String, Message>> prototypeMessages, List<Message> activateMessages,
                                        List<Message> deactivateMessages, DialogueTreeNode dialogueNode){
      ActorPrototype prototype = new ActorPrototype(data,prototypeMessages, activateMessages, deactivateMessages, dialogueNode);
      
      actorPrototypeMap.put(prototype.getName(),prototype);
      prototypeList.add(prototype.getObservablePrototype());


    }

    /**
     *
     * @param key key of the actor prototype in the map
     * @return ActorPrototype associated with the key
     */
    protected ActorPrototype getPrototype(String key){
        return actorPrototypeMap.get(key);
    }

    /**
     *
     * @param key key of the actor prototype in the map
     * @return new instance of ActorPrototype associated with the key
     */
    protected ActorPrototype getNewPrototypeInstance(String key){
        return actorPrototypeMap.get(key).clone();
    }

    /**
     * @apiNote For Testing Purposes
     * @param prototypeMessages
     */
    private void testMessageParsing( List<Map<String, Message>> prototypeMessages){
        System.out.println("Received:");
        System.out.println(prototypeMessages.get(0).size());
        for(Map<String,Message>map:prototypeMessages){
            for(String s:map.keySet()){

                System.out.println(s+": "+map.get(s).getMessageString());
            }
        }
    }

    /**
     * Serializes all existion prototypes
     * @param path: ath to folder where to save all prototypes
     */
    protected void serializeAllPrototypes(String path)throws SaveException{
        System.out.println("Prototypes:"+actorPrototypeMap.size());
        XStream serializer = new XStream(new DomDriver());
        serializer.omitField(AnimationObject.class,"animationView");
        String serialized= serializer.toXML(actorPrototypeMap);

            try{
                Files.write(Paths.get(path+"prototypes.xml"),serialized.getBytes());
            }
            catch (IOException e){
                throw new SaveException();
            }
        }


    /**
     *  Loads a prototype from an XML File
     * @param key key of the actor prototype in the map
     * @param path path to XML file
     */
    protected void loadPrototype(String key, String path){
        XStream serializer = new XStream(new DomDriver());
        ActorPrototype loadedActorPrototype=(ActorPrototype) serializer.fromXML(Paths.get(path).toFile());
        actorPrototypeMap.put(key,loadedActorPrototype);
       prototypeList.add(loadedActorPrototype.getObservablePrototype());
    }
    protected void loadPrototypes(String path){
        /*File xmlFile = new File(path);

        Reader fileReader = null;
        try {
            fileReader = new FileReader(xmlFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufReader = new BufferedReader(fileReader);

        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            line = bufReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while( line != null){
            sb.append(line).append("\n");
            try {
                line = bufReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String xml2String = sb.toString();*/

        XStream serializer = new XStream(new DomDriver());
        Map<String,ActorPrototype>loadedMap=(Map<String,ActorPrototype>)serializer.fromXML(Paths.get(path).toFile());
        actorPrototypeMap.putAll(loadedMap);
       for(ActorPrototype a:loadedMap.values()){prototypeList.add(a.getObservablePrototype());}
    }

    /**
     * deletes a prototype with a given name/id
     * @param name name of the prototype corresponding to the key in the map
     */
    protected void deletePrototype(String name){
        actorPrototypeMap.remove(name);
    }
    protected List<ObservablePrototype> getObservableList(){
        return prototypeList;
    }



}
