package cellular;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Cell {
    protected Cell replacementCell;
    protected Color myColor;
    protected Shape mySquare;
    protected boolean chosen;
    protected HashMap<Cell, Cell> myTargets;

    public Cell(Shape shape, Color color){
        mySquare = shape;
        myColor = color;
        mySquare.setFill(color);
    }

    public abstract void updateCell(ArrayList<Cell> neighborCells);

    public Cell getReplacementCell(){
        return replacementCell;
    }

    public Shape getMySquare(){
        return mySquare;
    }

    public void replaceMySquare(Shape nwRec){
        mySquare = nwRec;
        mySquare.setFill(myColor);
    }

    public HashMap<Cell, Cell> getTargets(){
        return myTargets;
    }

    public void resetTarget(){
        myTargets = null;
    }

    public void choose(){
        chosen = true;
    }

    public boolean wasChosen(){
        return chosen;
    }

    public void resetChosen(){
        chosen = false;
    }

}
