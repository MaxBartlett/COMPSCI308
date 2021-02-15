package cellular.wator;

import cellular.Cell;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.*;

public abstract class AnimalCell extends Cell {

    protected Cell destinationCell;
    protected Cell spawnCell;
    protected Cell birthCell;
    protected int turnsAlive;
    protected int turnsForSpawn;

    AnimalCell(Shape shape, Color CELL_COLOR){
        super(shape, CELL_COLOR);
    }

    @Override
    public void updateCell(ArrayList<Cell> myNeighborCells){
        turnsAlive++;
        destinationCell = null;
        ArrayList<Cell> myFoodCells = new ArrayList<Cell>();
        ArrayList<Cell> myWaterCells = new ArrayList<Cell>();
        populateLists(myNeighborCells, myFoodCells, myWaterCells);
        if(!wasChosen()){
            findDestinations(myFoodCells, myWaterCells);
            if(destinationCell != null){
                myTargets.put(destinationCell, this);
                choose();
            }
            else{
                replacementCell = this;
            }
            if(spawnCell != null){
                setUpSpawn();
                myTargets.put(spawnCell, birthCell);
            }
        }
    }

    public void findDestinations(ArrayList<Cell> myFoodCells, ArrayList<Cell> myWaterCells) {
        myTargets = new HashMap<Cell, Cell>();
        if(!myFoodCells.isEmpty()){
            destinationCell = chooseCell(myFoodCells);
        }
        if(destinationCell == null && !myWaterCells.isEmpty()){
            destinationCell = chooseCell(myWaterCells);
        }

        if(!myWaterCells.isEmpty()){
            spawnCell = findSpawn(myWaterCells);
        }

        if(destinationCell != null){
            replacementCell = new WaterCell(getMySquare());
        }
    }

    public Cell chooseCell(ArrayList<Cell> cellList){
        Collections.shuffle(cellList);
        for(Cell ret: cellList){
            if(!ret.wasChosen()){
                ret.choose();
                return ret;
            }
        }
        return null;
    }


    //Returns cell that represents spawn point
    public Cell findSpawn(ArrayList<Cell> cellList){
        if(turnsForSpawn == turnsAlive){
            return chooseCell(cellList);
        }
        return null;
    }

    public abstract void setUpSpawn();

    public abstract void populateLists(ArrayList<Cell> myNeighborCells, ArrayList<Cell> myFoodNodes, ArrayList<Cell> myWaterNodes);

}