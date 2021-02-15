package game;

import nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game {
    protected HashMap<Node, ArrayList<Node>> myMap;
    protected ArrayList<? extends Node> myNodes;

    Game(HashMap<Node, ArrayList<Node>> map, ArrayList<? extends Node> nodes){
        myMap = map;
        myNodes = nodes;
    }

    public void step(){
        if(!endState()){
            for(Node curNode: myNodes){
                curNode.updateNode();
            }
            for(Node curNode: myNodes){
                curNode.finishUpdate();
            }
        }
    }

    public abstract boolean endState();

}
