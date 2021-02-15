package authoring.authoring_frontend;

/**
 * Currently unused layer class.
 *
 * @author Allen Qiu
 */
public class Layer {
    private int id;

    /**
     * Default constructor starts with ID 1.
     */
    Layer(){
        this(1);
    }

    /**
     * Constructor.
     * @param layerID ID of the layer
     */
    Layer(int layerID){
        id = layerID;
    }

    /**
     * Gets the layer ID.
     * @return Layer ID
     */
    public int getLayerID(){
        return id;
    }

    /**
     * Prints the layer name and ID.
     * @return String containing the layer and number.
     */
    public String toString(){
        return "Layer " + id;
    }
}
