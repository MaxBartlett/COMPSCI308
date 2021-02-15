package cellular.rpc;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class BlueCell extends ColorCell {
    public static final Color CELL_COLOR = Color.rgb(0, 0, 255);

    public BlueCell(Shape shape){
        super(shape, CELL_COLOR);
    }

    public ColorCell createChild(Shape rec){
        return new BlueCell(rec);
    }

    public boolean isPrey(Cell target){
        return target instanceof WhiteCell || target instanceof GreenCell;
    }
}