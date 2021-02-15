package game;

import nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class ColorGame extends Game {

    public ColorGame(HashMap<Node, ArrayList<Node>> map, ArrayList<Node> nodes){
        super(map, nodes);
    }

    public boolean endState(){
        return false;
    }
}
