package authoring.authoring_frontend;

public class ActiveLayer {
    /**
     * Service locator for the layers. Currently not in use.
     *
     * @author Allen Qiu
     */
    private static Layer activeLayer = null;

    /**
     * Sets the currently active layer.
     * @param newActiveLayer The new layer to set as active.
     */
    public static void setActiveLayer(Layer newActiveLayer){
        activeLayer = newActiveLayer;
    }

    /**
     * Gets the currently active layer.
     * @return The active layer.
     */
    public static Layer getActiveLayer(){
        return activeLayer;
    }
}
