package game;

import cellular.fire.BurningCell;
import nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class FireGame extends Game {

    public FireGame(HashMap<Node, ArrayList<Node>> map, ArrayList<Node> nodes){
        super(map, nodes);

    }

    public boolean endState(){
        int numBurning = 0;
        for(Node curNode: myNodes){
            if(curNode.getMyCell() instanceof BurningCell){
                numBurning++;
            }
        }
        if(numBurning == 0){
            return true;
        }
        return false;
    }
}