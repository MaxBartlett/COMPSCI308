package cellular.fire;

import cellular.Cell;
import cellular.fire.BurningCell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class TreeCell extends Cell {
    public static final Color CELL_COLOR = Color.GREEN;

    private double myChance;

    public TreeCell(Shape shape, double prob){
        super(shape, CELL_COLOR);
        myChance = prob;
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){
        for(Cell curCell: myNeighborCells){
            if(curCell instanceof BurningCell && burns()){
                replacementCell = new BurningCell(mySquare);
            }
        }
    }

    public boolean burns(){
        return Math.random() <= myChance;
    }
}
