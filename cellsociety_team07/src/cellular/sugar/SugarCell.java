package cellular.sugar;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class SugarCell extends Cell {
    public static final Color CELL_COLOR = Color.rgb(255,165,0);
    public static final int MAX_CAPACITY = 20;
    private double sugarCount;
    private int growthRate;

    public SugarCell(Shape shape, int grwthRate, int initSug){
        super(shape, CELL_COLOR);
        growthRate = grwthRate;
        sugarCount = initSug;
    }

    @Override
    public void updateCell(ArrayList<Cell> neighborCells) {
        Color gradColor = Color.rgb(255,165,0, sugarCount/MAX_CAPACITY);
        mySquare.setFill(gradColor);
        sugarCount += growthRate;
        if(sugarCount > MAX_CAPACITY){
            sugarCount = MAX_CAPACITY;
        }
    }

    public double getSugarCount(){
        return sugarCount;
    }

    public int getGrowthRate(){
        return growthRate;
    }
}