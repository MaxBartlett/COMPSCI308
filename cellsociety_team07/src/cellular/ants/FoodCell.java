package cellular.ants;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class FoodCell extends Cell {
    public static final Color CELL_COLOR = Color.RED;

    public FoodCell(Rectangle shape){
        super(shape, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){

    }
}
