package cellular.ants;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GroundCell extends Cell {
    public static final Color CELL_COLOR = Color.BROWN;
    private double pheramoneA = 0;
    private double pheramoneB = 0;

    public GroundCell(Rectangle shape){
        super(shape, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){
    }

    public double getPheramoneA(){
        return pheramoneA;
    }
    public double getPheramoneB(){
        return pheramoneB;
    }

    public void changePheramoneA(double delta){
        pheramoneA += delta;
    }
    public void changePheramoneB(double delta){
        pheramoneB += delta;
    }
}