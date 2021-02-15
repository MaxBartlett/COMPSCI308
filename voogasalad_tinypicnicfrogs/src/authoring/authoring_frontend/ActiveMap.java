package authoring.authoring_frontend;

import java.util.HashMap;

/**
 * Service locator class for the currently active map.
 *
 * @author Allen Qiu
 */
public class ActiveMap {
    private static HashMap<String, String> mapHashMap = new HashMap<>();

    /**
     * Sets the currently active map.
     * @param projectName The name of the project.
     * @param newActiveMap The new map to set as active.
     */
    public static void setActiveMap(String projectName, String newActiveMap){
        mapHashMap.put(projectName, newActiveMap);
    }

    /**
     * Returns the currently active map.
     * @param projectName The name of the project.
     * @return The currently active map.
     */
    public static String getActiveMap(String projectName){
        return mapHashMap.get(projectName);
    }

    /**
     * Removes the currently active map.
     * @param projectName The name of the project.
     */
    public static void removeActiveMap(String projectName){
        mapHashMap.remove(projectName);
    }


}
