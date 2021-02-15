package cellular.life;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class LiveCell extends Cell {
    public static final Color CELL_COLOR = Color.BLACK;

    public LiveCell(Shape shape){
        super(shape, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){
        int numAlive = 0;
        for(Cell curCell: myNeighborCells){
            if(curCell instanceof LiveCell){
                numAlive++;
            }
        }
        if(numAlive < 2 || numAlive > 3){
            replacementCell = new DeadCell(mySquare);
        }

    }

}
