package authoring.authoring_backend;

import engine.backend.Commands.DialogueSelectCommand;
import engine.backend.DialogueTreeNode;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Revamped/simplified version of DialogueManager
 *
 * @author cl349
 */
public class DialogueManagerRewrite {
    Map<String, DialogueTreeNode> nodeMap;
    List<DialogueTreeNode> rootList;
    List<String> rootNameList;

    public DialogueManagerRewrite(){
        nodeMap = new HashMap<>();
        rootList = new ArrayList<>();
        rootNameList = new ArrayList<>();

    }

    /**
     * adds a new node
     * @param nodeJSON JSON representation of the node to be added
     */
    public void addNode(JSONObject nodeJSON){
        nodeMap.put((String) nodeJSON.get("name"),new DialogueTreeNode((String) nodeJSON.get("text")));
    }

    /**
     * Adds a new root node
     * @param nodeJSON JSON for root node to add
     */
    public void addRoot(JSONObject nodeJSON){
        var root = new DialogueTreeNode((String) nodeJSON.get("text"));
        nodeMap.put((String) nodeJSON.get("name"), root);
        rootList.add(root);
        rootNameList.add((String) nodeJSON.get("name"));
    }


    /**
     * Adds a new connection between two nodes
     * @param connectionJSON JSON representation of the connection
     */
    public void addConnection(JSONObject connectionJSON){
        var parentNode = nodeMap.get((String) connectionJSON.get("parent"));
        var childrenNode = nodeMap.get((String) connectionJSON.get("child"));
        String text = (String) connectionJSON.get("text");
        parentNode.putChild(text, childrenNode);
    }

    /**
     *
     * @return a list of all possible nodes
     */
    public List<String> getAllNodes(){
        var nameList = new ArrayList<String>();
        nameList.addAll(nodeMap.keySet());
        return nameList;
    }

    /**
     *
     * @return al list of all root nodes
     */
    public List<String> getAllRoots(){
        return rootNameList;
    }


    /**
     * Gets a particular root node
     *
     * @param name of the root node
     * @return the root node
     */
    public DialogueTreeNode getRoot(String name){
        return(nodeMap.get(name));
    }

}
