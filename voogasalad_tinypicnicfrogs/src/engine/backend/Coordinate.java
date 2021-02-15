package engine.backend;

/**
 *
 * Represents an abstract Coordinate. Has built in checking for the game bounds.
 *
 * @Christopher Lin cl349
 */
public class Coordinate {

    private int x;
    private int y;
    private int z;

    public Coordinate(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        if(!checkXBounds(x)){
            return;
        }
        this.x = x;
    }

    public int getY() {

        return y;
    }

    public void setY(int y) {
        if(!checkYBounds(y)){
            return;
        }
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    private boolean checkXBounds(int x){
        int mapWidth = ServiceLocator.getGameWorld().getMapWidth();
        return (x >= 0 && x <= mapWidth);
    }

    private boolean checkYBounds(int y){
        int mapHeight = ServiceLocator.getGameWorld().getMapHeight();
        return (y >= 0 && y <= mapHeight);
    }

}
