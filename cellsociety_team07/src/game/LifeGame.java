package game;

import cellular.life.LiveCell;
import nodes.Node;
import java.util.ArrayList;
import java.util.HashMap;

public class LifeGame extends Game {

    public LifeGame(HashMap<Node, ArrayList<Node>> map, ArrayList<Node> nodes){
        super(map, nodes);
    }

    public boolean endState(){
        int numAlive = 0;
        for(Node curNode: myNodes){
            if(curNode.getMyCell() instanceof LiveCell){
                numAlive++;
            }
        }
        if(numAlive == 0){
            return true;
        }
        return false;
    }
}