package game;

import cellular.segregation.EmptyCell;
import nodes.Node;
import nodes.AlgoNode;

import java.util.*;
import java.util.function.Function;

public class SegGame extends Game{
    private int numMoving = 1;

    public SegGame(HashMap<Node, ArrayList<Node>> map, ArrayList<AlgoNode> nodes){
        super(map, nodes);

        Function<Node, Node> breadthSearch = createFunc();

        for(AlgoNode node: nodes){
            node.setFunc(breadthSearch);
        }
    }

    public boolean endState(){
        if(numMoving == 0){
            return true;
        }
        return false;
    }

    public Function<Node, Node> createFunc(){
        Function<Node, Node> breadthSearch = (Node fstNode) -> {
            if(fstNode.getMyCell() instanceof EmptyCell){
                return null;
            }

            Queue<Node> que = new LinkedList<Node>();
            HashSet<Node> visited = new HashSet<Node>();

            ArrayList<Node> firstAdj = fstNode.getAdjacentNodes();
            Collections.shuffle(firstAdj);
            for(Node curNode: firstAdj){
                que.add(curNode);
                visited.add(curNode);
            }

            while(que.size() != 0){
                Node curNode = que.poll();
                if(curNode.getMyCell() instanceof EmptyCell && !curNode.getMyCell().wasChosen()){
                    return curNode;
                }
                for(Node nwNode: curNode.getAdjacentNodes()){
                    if(!visited.contains(nwNode)){
                        visited.add(nwNode);
                        que.add(nwNode);
                    }
                }
            }

            return null;
        };
        return breadthSearch;
    }


}
