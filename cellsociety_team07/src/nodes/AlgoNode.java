package nodes;

import cellular.Cell;
import cellular.segregation.EmptyCell;
import java.util.function.Function;

public class AlgoNode extends Node{
    private Function<Node, Node> myFunc;


    public AlgoNode(Cell inpt){
        super(inpt);
    }

    public void updateNode(){
        nodeSquare = myCell.getMySquare();
        myCell.updateCell(getNeighborCells());
        if(myCell.getReplacementCell() instanceof EmptyCell){
            myReplacementCell = myCell.getReplacementCell();
            Node target = myFunc.apply(this);
            if(target != null){
                target.getMyCell().choose();
                target.setReplacementCell(myCell);
            }
            else if(target == null && !(myCell instanceof EmptyCell)) {
                myReplacementCell = null;
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


    public void setFunc(Function<Node, Node> func){
        myFunc = func;
    }
}
