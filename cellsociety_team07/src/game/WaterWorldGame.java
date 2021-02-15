package game;

import nodes.Node;
import java.util.ArrayList;
import java.util.HashMap;

public class WaterWorldGame extends Game {

    public WaterWorldGame(HashMap<Node, ArrayList<Node>> map, ArrayList<Node> nodes){
        super(map, nodes);
    }

    public boolean endState(){
        return false;
    }

}
