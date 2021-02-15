package game;

import nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class SugarGame extends Game {

    public SugarGame(HashMap<Node, ArrayList<Node>> map, ArrayList<Node> nodes){
        super(map, nodes);

    }

    public boolean endState(){
        return false;
    }

}
