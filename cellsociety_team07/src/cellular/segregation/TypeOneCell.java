package cellular.segregation;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class TypeOneCell extends PersonCell {
    public static final Color CELL_COLOR = Color.RED;

    public TypeOneCell(Shape shape, double rate) {
        super(shape, rate, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){
        double numSame = 0;
        double numDiff = 0;
        replacementCell = null;
        move = false;
        for(Cell curCell: myNeighborCells){
            if(curCell instanceof TypeOneCell){
                numSame++;
            }
            else if(curCell instanceof TypeTwoCell){
                numDiff++;
            }
        }
        if(numSame / (numSame + numDiff) < mySatisfiedRate){
            move = true;
            replacementCell = new EmptyCell(mySquare);
        }
    }

}
