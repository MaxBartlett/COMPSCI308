package grids;
import nodes.Node;
import java.util.ArrayList;
import javafx.scene.shape.Shape;
import shapes.HexagonCreator;

/** @author Ryan Piersma
 *  @date 10.5.18
 *  Purpose: This class implements a hexagonal grid structure over a set of Cell Society nodes
 *  Assumptions: There is a Node class whose function is to hold cells and their states, and a grid with some
 *  specific dimensions
 *  Dependencies: Shape, Polygon, nodes.Node, ArrayList, shapes.HexagonCreator
 *  Grid x = new HexGrid(grid, gridX, gridY, wraparound);
 */
public class HexGrid extends Grid{

    public static final double HEIGHT_OFFSET_RATIO = Math.sqrt(3)/2.0;
    public static final double HEX_WIDTH_CONSTANT = 2.0;

    /**
     * Constructor for hexagonal grid object
     * @param grid 2d array of nodes being used for the grid
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param wraparound indicate whether simulation is on a torus or not
     */
    public HexGrid(Node[][] grid, int gridX, int gridY, boolean wraparound) {
        this.grid = grid;
        this.gridX = gridX;
        this.gridY = gridY;
        this.wraparound = wraparound; //right now hex grids cannot do wraparound
    }

    /**
     * Find the Moore (for squares, all 8 neighbors) neighborhood of a Cell Society node as defined for a hexagonal grid
     * @param xPos x Coordinate of Node for which Moore neighborhood is found
     * @param yPos y Coordinate of Node for which Moore neighborhood is found
     * @return ArrayList of Nodes in the Moore neighborhood for a given Node represented in a hexagonal grid
     */
    public ArrayList<Node> getAdjacentNodesMooreNeighborhood(int xPos, int yPos)
    {
        ArrayList<Node> adjacentNodes = new ArrayList<>();

        if (yPos > 0) {adjacentNodes.add(grid[xPos][yPos - 1]);}
        if (yPos < gridY - 1) {adjacentNodes.add(grid[xPos][yPos + 1]);}

        if (xPos < gridX-1){
            adjacentNodes.add(grid[xPos + 1][yPos]);
            if (yPos < gridY - 1) {adjacentNodes.add(grid[xPos + 1][yPos + 1]);
        }}
        if (xPos > 0) {
            adjacentNodes.add(grid[xPos - 1][yPos]);
            if (yPos > 0) {adjacentNodes.add(grid[xPos - 1][yPos - 1]);
        }}

        return adjacentNodes;
    }

    /**
     * Find the Von Neumann neighborhood (for squares, N,S,E,W neighbors) of a Cell Society node as defined for a hexagonal grid
     * Note that Von Neumann neighborhoods and Moore Neighborhoods happen to be defined identically for hexagons
     * @param xPos x Coordinate of Node for which Von Neumann neighborhood is found
     * @param yPos y Coordinate of Node for which Von Neumann neighborhood is found
     * @return ArrayList of Nodes in the Von Neumann neighborhood for a given node in hexagonal grid
     */
    public ArrayList<Node> getAdjacentNodesVonNeumannNeighborhood(int xPos, int yPos)
    {
        ArrayList<Node> adjacentNodes = new ArrayList<>();

        if (yPos > 0) {adjacentNodes.add(grid[xPos][yPos - 1]);}
        if (yPos < gridY - 1) {adjacentNodes.add(grid[xPos][yPos + 1]);}

        if (xPos < gridX-1){
            adjacentNodes.add(grid[xPos + 1][yPos]);
            if (yPos < gridY - 1) {adjacentNodes.add(grid[xPos + 1][yPos + 1]);
            }}
        if (xPos > 0) {
            adjacentNodes.add(grid[xPos - 1][yPos]);
            if (yPos > 0) {adjacentNodes.add(grid[xPos - 1][yPos - 1]);
            }}

        return adjacentNodes;
    }

    /**
     * Create a graphical object representing a Node for some grid
     * @param gridX X dimension of grid
     * @param gridY Y dimension of grid
     * @param xPos x position of Node in grid
     * @param yPos y position of Node in grid
     * @return Graphical object (hexagon) that represents a node in the grid
     */
    public Shape createShape(int gridX, int gridY, int xPos, int yPos)
    {
        double cellWidth = computeCellWidth(gridX, gridY);
        if (gridX % 2 == 1)
        {
            yPos += HEIGHT_OFFSET_RATIO*cellWidth;
        }
        HexagonCreator hexCreate = new HexagonCreator(xPos, yPos, cellWidth);
        return hexCreate.getMyHex();
    }

    /**
     * Calculate cell width to be used for displaying Cells graphically
     * @param gridX  X dimension of grid being initialized
     * @param gridY  Y dimension of grid being initialized
     * @return Cell width to be used for displaying Cells graphically
     */
    public double computeCellWidth(int gridX, int gridY)
    {
        double cellWidth = Math.floor(WINDOW_WIDTH/(gridY*HEX_WIDTH_CONSTANT));
        return cellWidth;
    }



}
