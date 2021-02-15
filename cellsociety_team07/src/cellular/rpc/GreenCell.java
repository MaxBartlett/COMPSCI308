package cellular.rpc;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class GreenCell extends ColorCell {
    public static final Color CELL_COLOR = Color.rgb(0, 255, 0);

    public GreenCell (Shape shape){
        super(shape, CELL_COLOR);
    }

    public ColorCell createChild(Shape rec){
        return new GreenCell(rec);
    }

    public boolean isPrey(Cell target){
        return target instanceof WhiteCell || target instanceof RedCell;
    }
}
