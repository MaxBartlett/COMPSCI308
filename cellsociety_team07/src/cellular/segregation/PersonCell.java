package cellular.segregation;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public abstract class PersonCell extends Cell {

    protected boolean move;
    protected double mySatisfiedRate;

    PersonCell(Shape shape, double rate, Color CELL_COLOR){
        super(shape, CELL_COLOR);
        mySatisfiedRate = rate;
    }

    public boolean shouldMove(){
        return move;
    }
}
