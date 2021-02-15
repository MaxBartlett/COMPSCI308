

package grids;

import javafx.scene.shape.Shape;
import nodes.Node;

import java.util.ArrayList;

/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class implements a grid structure over a set of Cell Society nodes
 *  Assumptions: There is a Node class whose function is to hold cells and their states, and a grid with some
 *  specific dimensions
 *  Dependencies: Shape, nodes.Node
 *  Abstract class - cannot be instantiated
 */

public abstract class Grid {

    public static final double WINDOW_WIDTH = 800.0;

    //Grid state variables
    protected Node[][] grid;
    protected int gridX;
    protected int gridY;
    protected boolean wraparound;

    /**
     * Calculate cell width to be used for displaying Cells graphically
     * @param gridX  X dimension of grid being initialized
     * @param gridY  Y dimension of grid being initialized
     * @return Cell width to be used for displaying Cells graphically
     */
    public abstract double computeCellWidth(int gridX, int gridY);

    /**
     * Find the Moore (for squares, all 8 neighbors) neighborhood of a Cell Society node as defined for some instance of Grid
     * @param xPos x Coordinate of Node for which Moore neighborhood is found
     * @param yPos y Coordinate of Node for which Moore neighborhood is found
     * @return ArrayList of Nodes in the Moore neighborhood for a given Node
     */
    public abstract ArrayList<Node> getAdjacentNodesMooreNeighborhood(int xPos, int yPos);

    /**
     * Find the Von Neumann neighborhood (for squares, N,S,E,W neighbors) of a Cell Society node as defined for some instance of Grid
     * @param xPos x Coordinate of Node for which Von Neumann neighborhood is found
     * @param yPos y Coordinate of Node for which Von Neumann neighborhood is found
     * @return ArrayList of Nodes in the Von Neumann neighborhood for a given Node
     */
    public abstract ArrayList<Node> getAdjacentNodesVonNeumannNeighborhood(int xPos, int yPos);

    /**
     * Create a graphical object representing a Node for some grid
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param xPos x position of Node in grid
     * @param yPos y position of Node in grid
     * @return Graphical object that represents a node in the grid
     */
    public abstract Shape createShape(int gridX, int gridY, int xPos, int yPos);
}
