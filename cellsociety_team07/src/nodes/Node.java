package nodes;

import cellular.Cell;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Node {

    protected HashMap<Node, ArrayList<Node>> myMap;
    protected Cell myCell;
    protected Cell myReplacementCell;
    protected Shape nodeSquare;

    public Node(Cell inpt){
        myCell = inpt;
    }

    public abstract void updateNode();

    public void setMap(HashMap<Node, ArrayList<Node>> map){
        myMap = map;
    }

    public void setReplacementCell(Cell nwCell){
        myReplacementCell = nwCell;
    }

    public Cell getMyCell(){
        return myCell;
    }

    public ArrayList<Node> getAdjacentNodes(){
        return myMap.get(this);
    }

    public ArrayList<Cell> getNeighborCells(){
        ArrayList<Node> neighborNodes = getAdjacentNodes();
        ArrayList<Cell> neighborCells = new ArrayList<Cell>();
        for(Node curNode: neighborNodes){
            neighborCells.add(curNode.getMyCell());
        }
        return neighborCells;
    }

    public abstract void finishUpdate();

}
