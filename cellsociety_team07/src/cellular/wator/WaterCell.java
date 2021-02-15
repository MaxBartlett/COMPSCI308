package cellular.wator;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class WaterCell extends Cell {
    public static final Color CELL_COLOR = Color.BLUE;
    public static final double KELP_SPAWN_CHANCE = .1;

    public WaterCell(Shape shape){
        super(shape, CELL_COLOR);
    }

    public KelpCell makeReplacement(){
        return new KelpCell(mySquare);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells) {
        if(spawns() && !wasChosen()) {
            replacementCell = makeReplacement();
            choose();
        }
    }

    public boolean spawns(){
        return Math.random() <= KELP_SPAWN_CHANCE;
    }
}
