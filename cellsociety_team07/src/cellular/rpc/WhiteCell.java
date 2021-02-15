package cellular.rpc;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class WhiteCell extends ColorCell {
    public static final Color CELL_COLOR = Color.BROWN;

    public WhiteCell(Shape shape) {
        super(shape, CELL_COLOR);
    }

    public ColorCell createChild(Shape rec){
        return new WhiteCell(rec);
    }

    public boolean isPrey(Cell target){
        return true;
    }
}