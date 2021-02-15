package cellular.segregation;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;

public class EmptyCell extends Cell {
    public static final Color CELL_COLOR = Color.WHITE;

    public EmptyCell(Shape shape) {
        super(shape, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){}
}
