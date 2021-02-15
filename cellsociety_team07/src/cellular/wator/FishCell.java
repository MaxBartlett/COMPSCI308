package cellular.wator;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class FishCell extends AnimalCell {
    public static final Color CELL_COLOR = Color.ORANGE;

    public FishCell(Shape shape, int trnsAlive, int trnsSpawn){
        super(shape, CELL_COLOR);

        turnsAlive = trnsAlive;
        turnsForSpawn = trnsSpawn;
    }

    public void setUpSpawn(){
        birthCell = new FishCell(mySquare, 0, turnsAlive);
    }


    public void populateLists(ArrayList<Cell> myNeighborCells, ArrayList<Cell> myFoodNodes, ArrayList<Cell> myWaterNodes){
        for(Cell curCell: myNeighborCells){
            if(curCell instanceof KelpCell && !curCell.wasChosen()){
                myFoodNodes.add(curCell);
            }
            else if(curCell instanceof WaterCell && !curCell.wasChosen()){
                myWaterNodes.add(curCell);
            }
        }
    }

}
