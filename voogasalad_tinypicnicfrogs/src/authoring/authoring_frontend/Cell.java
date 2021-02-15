package authoring.authoring_frontend;

import java.util.ArrayList;

/**
 * Class for a cell of a map.
 *
 * @author Allen Qiu
 */
public class Cell {
    private int x;
    private int y;
    private ArrayList<Actor> myActors;

    /**
     * Constructor
     * @param myX x-coordinate of the cell.
     * @param myY y-coordinate of the cell.
     */
    Cell(int myX, int myY){
        x = myX;
        y = myY;
        myActors = new ArrayList<>();
    }

    /**
     * Adds an actor to this cell at the top.
     * @param toAdd The actor to add.
     */
    void addActor(Actor toAdd){
        myActors.add(toAdd);
    }

    /**
     * Removes an actor from this cell from the top.
     */
    public void removeActor(){
        myActors.remove(myActors.size()-1);
    }

    /**
     * Returns a list of actors at this cell.
     * @return List of actors at this cell.
     */
    public ArrayList<Actor> getActors(){
        return myActors;
    }

    /**
     * Gets x-coordinate.
     * @return x-coordinate.
     */
    public int getX(){
        return x;
    }

    /**
     * Gets y-coordinate.
     * @return y-coordinate.
     */
    public int getY(){
        return y;
    }
}
