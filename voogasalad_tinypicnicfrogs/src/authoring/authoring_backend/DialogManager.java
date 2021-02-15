package authoring.authoring_backend;

import engine.backend.DialogueTreeNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * the dialogMap maps the name of the DialogInteraction (such as Greeting) to the DialogRootNode which is the start of the
 * dialog graph. The responses is what is available when the author wants to map a prompt to a response. And the dialogNodes
 * are filled out by the author, as they fill out the DialogueNodeWindow (node key, to dialog body)
 */

public class DialogManager {
    private Map<String, DialogueTreeNode>dialogMap;
    private List<String> responses;
    private JSONArray dialogNodes;

    public DialogManager(){
        dialogMap=new HashMap<>();
        responses = new ArrayList<>();
        dialogNodes = new JSONArray();
    }

    public DialogueTreeNode createDialog(String dialogName,JSONArray dialogNodes, JSONObject dialogTree){
        Map<String,DialogueTreeNode>nodeMap= new HashMap<>();
        for(int i=0;i<dialogNodes.size();i+=1){
            JSONObject obj= (JSONObject)dialogNodes.get(i);
            nodeMap.put((String)obj.get("key"),new DialogueTreeNode((String)obj.get("value")));
        }

        dfs(nodeMap,nodeMap.get("root"),new HashSet<>(),dialogTree,"root");
        dialogMap.put(dialogName,nodeMap.get("root"));
        return nodeMap.get("root");
    }
    private void dfs(Map<String,DialogueTreeNode>nodes,DialogueTreeNode current, Set<DialogueTreeNode>visited, JSONObject dialogTree,String leaf){
        JSONArray arr=(JSONArray) dialogTree.get(leaf);
        if(arr.size()==0||arr==null){return;}
        for(int i=0;i<arr.size();i+=1){
            JSONObject entry= (JSONObject)arr.get(i);
            current.putChild((String)entry.get("prompt"),nodes.get((String)entry.get("node")));
            if(!visited.contains(nodes.get((String)entry.get("node")))){
                visited.add(nodes.get((String)entry.get("node")));
                dfs(nodes,nodes.get((String)entry.get("node")),visited,dialogTree,(String)entry.get("node"));}
        }
    }

    /**
     * These methods are used in the gameManager
     * @param key
     * @return
     */

    public DialogueTreeNode getDialog(String key){
        return dialogMap.get(key);
    }

    public void addDialogNode(JSONObject dialogJSON){
        dialogNodes.add(dialogJSON);
    }
    public void addResponse(String newResponse){
        responses.add(newResponse);
    }
    public List<String> getResponses(){
        return responses;
    }
    public JSONArray getDialogNodes() {
        return dialogNodes;
    }
}
