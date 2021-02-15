package cellular.rpc;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RedCell extends ColorCell {
    public static final Color CELL_COLOR = Color.rgb(255, 0, 0);

    public RedCell(Shape shape){
        super(shape, CELL_COLOR);
    }

    public ColorCell createChild(Shape rec){
        return new RedCell(rec);
    }

    public boolean isPrey(Cell target){
        return target instanceof WhiteCell || target instanceof BlueCell;
    }
}