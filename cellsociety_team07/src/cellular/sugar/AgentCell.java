package cellular.sugar;
import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AgentCell extends Cell {
    public static final Color CELL_COLOR = Color.GREEN;
    private int hldSugar;
    private int sugarMetabolism;

    public AgentCell(Shape shape, int initSug, int met){
        super(shape, CELL_COLOR);
        hldSugar = initSug;
        sugarMetabolism = met;
    }

    @Override
    public void updateCell(ArrayList<Cell> neighborCells) {
        double curMax = 0;
        myTargets = new HashMap<Cell, Cell>();
        ArrayList<Cell> maxCells = new ArrayList<Cell>();
        for(Cell curCell: neighborCells){
            if(curCell instanceof SugarCell && ((SugarCell) curCell).getSugarCount() >= curMax && !curCell.wasChosen()){
                curMax = ((SugarCell) curCell).getSugarCount();
                maxCells.add(curCell);
            }
        }
        if(maxCells.size() != 0){
            Collections.shuffle(maxCells);
            Cell target = maxCells.get(0);
            target.choose();
            replacementCell = new SugarCell(getMySquare(), ((SugarCell) target).getGrowthRate(),0);
            hldSugar += ((SugarCell)target).getSugarCount();
            hldSugar -= sugarMetabolism;
            if(hldSugar > 0){
                myTargets.put(target, this);
            }
        }
    }
}
