package engine.backend;


/**
 * Used to pass global messages to actors
 * @author Christopher Lin cl349
 */

public class Message {
    private String myMessageString;
    public Message(String messageString){
        myMessageString = messageString;
    }
    public String getMessageString(){
        return myMessageString;
    }
}
