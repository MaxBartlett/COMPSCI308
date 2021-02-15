package authoring.backend;
import authoring.authoring_backend.ActorPrototypeManager;
import authoring.authoring_backend.GameManager;
import engine.backend.Actor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class GameManagetTester {
    @Test
    public void testMessageCreated() {
        GameManager manager = new GameManager();
        manager.createMessage("testMessage", "This is a test");
        assertEquals("This is a test", manager.getMessage("testMessage").getMessageString());
        assertEquals(null, manager.getMessage("test"));
    }

    @Test
    public void testMessageReading() {
        GameManager manager = new GameManager();
        setUp(manager);
        manager.loadMessage("victory","./resources/message-1.xml");
        manager.loadMessage("defeat","./resources/message-2.xml");
        assertEquals("Player won",manager.getMessage("victory").getMessageString());
        assertEquals("Game over",manager.getMessage("defeat").getMessageString());
    }
    @Test
    public void testReadActorNotNull(){
        GameManager manager = new GameManager();
        setUp(manager);
        manager.loadActors("./resources/actors.xml");
        assertNotEquals(null,manager.getActor("player100-100-0"));
    }
    @Test
    public void testReadPrototypeNotNull(){
        GameManager manager = new GameManager();
        setUp(manager);
        manager.loadPrototype("boss","./resources/authoring/prototype-1.xml");
        assertNotEquals(null,manager.getPrototype("boss"));
    }

    private void setUp(GameManager manager){
        manager.createMessage("onVictory","Player won");
        manager.createMessage("onDefeat","Game over");
        manager.saveGame("./resources/","./resources/authoring/");
        JSONObject data = loadJSON("./resources/DemoPrototype1.json");
        JSONObject data2 =loadJSON("./resources/DemoPrototype2.json");
        JSONObject backgroundData=loadJSON("./resources/DemoBackground.json");
        if(data!=null){
            manager.createActorPrototype(data);
            manager.createActorPrototype(data2);
            manager.createActorPrototype(backgroundData);
            manager.createActor("player",0,0,0,0,0);
            manager.createActor("enemy",350,350,0,0,0);
            manager.createActor("background",0,0,0,0,0);
            manager.saveGame("./resources/demo/","./resources/authoring/");
        }
    }
    private  JSONObject loadJSON(String path){
        JSONParser parser = new JSONParser();
        try{

            JSONObject obj=(JSONObject) parser.parse(new FileReader(path));
            return obj;
        }catch (ParseException e){
            e.printStackTrace();
        }catch (IOException e){e.printStackTrace();}


        return null;
    }
}