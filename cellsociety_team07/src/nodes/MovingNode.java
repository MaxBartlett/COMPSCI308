package nodes;

import cellular.Cell;

import java.util.HashMap;

public class MovingNode extends Node {

    public MovingNode(Cell inpt){
        super(inpt);
    }

    public void updateNode(){
        nodeSquare = myCell.getMySquare();
        myCell.updateCell(getNeighborCells());
        if(myReplacementCell == null){
            myReplacementCell = myCell.getReplacementCell();
        }
        HashMap<Cell, Cell> targets = myCell.getTargets();
        for(Node curNode: getAdjacentNodes()){
            if(targets != null && targets.containsKey(curNode.getMyCell())){
                curNode.setReplacementCell(targets.get(curNode.getMyCell()));
            }
        }


    }

    public void finishUpdate(){
        if(myReplacementCell != null){
            myCell = myReplacementCell;
            myCell.replaceMySquare(nodeSquare);
        }
        nodeSquare = myCell.getMySquare();
        myCell.resetChosen();
        myCell.resetTarget();
        myReplacementCell = null;
    }



}
