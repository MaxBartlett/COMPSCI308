package authoring.authoring_frontend;

import java.util.HashMap;

/**
 * Active Item
 *
 * Service locator class to get the active item between the menu and the map.
 *
 * @author Allen Qiu
 */
public class ActiveItem {
    private static HashMap<String, Actor> actorHashMap = new HashMap<>();

    /**
     * Sets the active item for this service locator.
     * @param projectName The name of the project.
     * @param newActiveItem The new item to set as active.
     */
    public static void setActiveItem(String projectName, Actor newActiveItem){
        actorHashMap.put(projectName, newActiveItem);
    }

    /**
     * Gets the currently active item.
     * @param projectName The name of the project.
     * @return The currently active item.
     */
    public static Actor getActiveItem(String projectName){
        return actorHashMap.get(projectName);
    }

    /**
     * Removes an item as active.
     * @param projectName The name of the project.
     */
    public static void removeActiveItem(String projectName){
        actorHashMap.remove(projectName);
    }
}
