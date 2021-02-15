package cellular.fire;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class BurningCell extends Cell {
    public static final Color CELL_COLOR = Color.RED;

    public BurningCell(Shape shape){
        super(shape, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){
        replacementCell = new BurntCell(mySquare);
    }
}
