package grids;//build cell maps for simulations with grids by using a grid
import javafx.scene.shape.Rectangle;
import nodes.Node;
import javafx.scene.shape.*;
import java.util.ArrayList;

/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class implements a square grid structure over a set of Cell Society nodes
 *  Assumptions: There is a Node class whose function is to hold cells and their states, and a grid with some
 *  specific dimensions
 *  Dependencies: Shape, Rectangle, nodes.Node, ArrayList
 *  Grid x = new SquareGrid(grid, gridX, gridY, wraparound);
 */
public class SquareGrid extends Grid {

    /**
     * Constructor for square grid object
     * @param grid 2d array of nodes being used for the grid
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param wraparound indicate whether simulation is on a torus or not
     */
    public SquareGrid(Node[][] grid, int gridX, int gridY, boolean wraparound) {
        this.grid = grid;
        this.gridX = gridX;
        this.gridY = gridY;
        this.wraparound = wraparound;
    }

    /**
     * Find the Moore (for squares, all 8 neighbors) neighborhood of a Cell Society node as defined for a square grid
     * @param xPos x Coordinate of Node for which Moore neighborhood is found
     * @param yPos y Coordinate of Node for which Moore neighborhood is found
     * @return ArrayList of Nodes in the Moore neighborhood for a given Node represented in a square grid
     */
    public ArrayList<Node> getAdjacentNodesMooreNeighborhood(int xPos, int yPos) {
        ArrayList<Node> adjacentNodes = new ArrayList<Node>();

        int upperX = (xPos == (gridX - 1)) ? (gridX - 1) : xPos + 1;
        int lowerX = (xPos == 0) ? 0 : xPos - 1;
        int upperY = (yPos == (gridY - 1)) ? (gridY - 1) : yPos + 1;
        int lowerY = (yPos == 0) ? 0 : yPos - 1;

        for (int i = lowerX; i <= upperX; i++) {
            for (int j = lowerY; j <= upperY; j++) {
                if (!((i == xPos) && (j == yPos))) {
                    adjacentNodes.add(grid[i][j]);
                }
            }
        }
        return adjacentNodes;
    }

    /**
     * Find the Von Neumann neighborhood (for squares, N,S,E,W neighbors) of a Cell Society node as defined for a hexagonal grid
     * Note that Von Neumann neighborhoods and Moore Neighborhoods happen to be defined identically for hexagons
     * @param xPos x Coordinate of Node for which Von Neumann neighborhood is found
     * @param yPos y Coordinate of Node for which Von Neumann neighborhood is found
     * @return ArrayList of Nodes in the Von Neumann neighborhood for a given node in hexagonal grid
     */
    public ArrayList<Node> getAdjacentNodesVonNeumannNeighborhood(int xPos, int yPos) {
        ArrayList<Node> adjacentNodes = new ArrayList<Node>();

        if (xPos > 0) {
            adjacentNodes.add(grid[xPos - 1][yPos]);
        }
        if (xPos < (gridX - 1)) {
            adjacentNodes.add(grid[xPos + 1][yPos]);
        }
        if (yPos > 0) {
            adjacentNodes.add(grid[xPos][yPos - 1]);
        }
        if (yPos < (gridY - 1)) {
            adjacentNodes.add(grid[xPos][yPos + 1]);
        }

        if (wraparound) {
            if (xPos == 0) {
                adjacentNodes.add(grid[gridX - 1][yPos]);
            }
            if (xPos == (gridX - 1)) {
                adjacentNodes.add(grid[0][yPos]);
            }
            if (yPos == 0) {
                adjacentNodes.add(grid[xPos][gridY - 1]);
            }
            if (yPos == (gridY - 1)) {
                adjacentNodes.add(grid[xPos][0]);
            }
        }

        return adjacentNodes;
    }

    /**
     * @author Jonathan Nakagawa
     * Find the adjacent cross neighborhood of a Cell Society node in a square grid (sugar sim)
     * @param xPos x Coordinate of Node for which adjacent cross neighborhood is found
     * @param yPos y Coordinate of Node for which adjacent cross neighborhood is found
     * @return ArrayList of Nodes in the adjacent cross neighborhood for a given node in square grid
     */
    public ArrayList<Node> getAdjacentCrossNeighborhood(int xPos, int yPos, int range) {
        ArrayList<Node> adjacentNodes = new ArrayList<Node>();

        int traceX = xPos;
        while (traceX < gridX && traceX - xPos < range) {
            adjacentNodes.add(grid[traceX][yPos]);
            traceX++;
        }
        traceX = xPos;
        while (traceX > 0 && xPos - traceX < range) {
            adjacentNodes.add(grid[traceX][yPos]);
            traceX--;
        }

        int traceY = yPos;
        while (traceY < gridY && traceY - yPos < range) {
            adjacentNodes.add(grid[xPos][traceY]);
            traceY++;
        }
        traceY = yPos;
        while (traceY > 0 && yPos - traceY < range) {
            adjacentNodes.add(grid[xPos][traceY]);
            traceY--;
        }

        return adjacentNodes;
    }

    /**
     * Create a graphical object representing a Node for some grid
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param xPos x position of Node in grid
     * @param yPos y position of Node in grid
     * @return Graphical object (square) that represents a node in the grid
     */
    public double computeCellWidth(int gridX, int gridY) {
        if (gridX > gridY) {
            return WINDOW_WIDTH / (double) gridX;
        } else {
            return Math.floor(WINDOW_WIDTH / (double) gridY);
        }

    }

    /**
     * Calculate cell width to be used for displaying Cells graphically
     * @param gridX  X dimension of grid being initialized
     * @param gridY  Y dimension of grid being initialized
     * @return Cell width to be used for displaying Cells graphically
     */
    public Shape createShape(int gridX, int gridY, int xPos, int yPos) {
        double cellWidth = computeCellWidth(gridX, gridY);
        return new Rectangle(xPos * cellWidth, yPos * cellWidth, cellWidth, cellWidth);
    }
}



