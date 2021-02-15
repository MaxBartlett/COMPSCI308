package authoring.authoring_frontend;

import javafx.util.Pair;

/**
 * Portal class to connect two cells.
 *
 * @author Allen Qiu
 */
public class Portal {
    private Pair<Integer, Integer> fromCoordinate;
    private Pair<Integer, Integer> toCoordinate;
    private boolean reversable;
    private Map mapFrom;
    private Map mapTo;
    private MapManager mapManager;

    /**
     * Constructor
     * @param from Pair of integers from.
     * @param fromMap Map from.
     * @param to Pair of integers to.
     * @param toMap Map to.
     * @param reverse If portal is reversable
     * @param manager MapManager of the game
     */
    Portal(Pair<Integer, Integer> from, Map fromMap, Pair<Integer, Integer> to, Map toMap, boolean reverse, MapManager manager){
        fromCoordinate = from;
        toCoordinate = to;
        reversable = reverse;
        mapFrom = fromMap;
        mapTo = toMap;
        mapManager = manager;
    }

    public boolean withinWidth(int newWidth){
        if(fromCoordinate.getKey() > newWidth || toCoordinate.getKey() > newWidth){
            return true;
        }
        return false;
    }

    public boolean withinHeight(int newHeight){
        if(fromCoordinate.getValue() > newHeight || toCoordinate.getValue() > newHeight){
            return true;
        }
        return false;
    }
}
