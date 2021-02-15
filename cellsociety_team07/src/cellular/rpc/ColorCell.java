package cellular.rpc;
import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collections;

public abstract class ColorCell extends Cell {
    public static final double MAX_GRADIENT = 10;
    protected int gradient;

    public ColorCell(Shape shape, Color CELL_COLOR){
        super(shape, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){
        Collections.shuffle(myNeighborCells);
        Cell target = myNeighborCells.get(0);
        if(target instanceof WhiteCell){
            replaceTarget(target);
        }
        else if(isPrey(target)){
            int trgtGrad = ((ColorCell) target).getGradient();
            if(trgtGrad == 9){
                replaceTarget(target);
            }
            else {
                ((ColorCell) target).setGradient(((ColorCell) target).getGradient() + 1);
                ((ColorCell) target).updateColor();
            }
        }
    }

    public void setGradient(int nwGrad){
        gradient = nwGrad;
    }

    public int getGradient(){
        return gradient;
    }

    public void updateColor(){
        Color gradColor = Color.rgb((int) myColor.getRed() ,(int) myColor.getGreen(),(int) myColor.getBlue(), (MAX_GRADIENT - gradient)/ MAX_GRADIENT);
        mySquare.setFill(gradColor);
    }

    public void replaceTarget(Cell target){
        target.choose();
        Shape hldRc = target.getMySquare();
        ColorCell replace = createChild(hldRc);
        replace.setGradient(gradient + 1);
        if (gradient == 9) {
            replace.setGradient(9);
        }
        myTargets.put(target, replace);
    }

    public abstract ColorCell createChild(Shape rec);

    public abstract boolean isPrey(Cell target);

}
