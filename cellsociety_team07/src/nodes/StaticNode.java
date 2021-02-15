package nodes;

import cellular.Cell;

public class StaticNode extends Node{

    public StaticNode(Cell inpt){
        super(inpt);
    }

    public void updateNode(){
        myCell.updateCell(getNeighborCells());
        myReplacementCell = myCell.getReplacementCell();
    }

    public void finishUpdate(){
        nodeSquare = myCell.getMySquare();
        if(myReplacementCell != null){
            myCell = myReplacementCell;
            myCell.replaceMySquare(nodeSquare);
        }
        myCell.resetChosen();
        myCell.resetTarget();
        myReplacementCell = null;
    }

}
