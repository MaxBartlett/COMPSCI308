package cellular.wator;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class KelpCell extends Cell {
    public static final Color CELL_COLOR = Color.GREEN;

    public KelpCell(Shape shape){
        super(shape, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells) {
    }


}
