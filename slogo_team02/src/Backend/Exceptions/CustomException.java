package Backend.Exceptions;

import Backend.CommandManager;

import java.util.ResourceBundle;

/**
 * @author Michael Glushakov (mg367)
 * @apiNote Purpose: Abstract Class meant to define thew structure for all custom exceptions
 * @apiNote Assumptions: ERROR_PATH is an actual path to the resource bundle
 * @apiNote Dependencies: CommandManager.ERROR_PATH
 * @apiNote Usage: throw new CustomException()
 */
public class CustomException extends IllegalArgumentException {
    public CustomException(String key){
        super(ResourceBundle.getBundle(CommandManager.ERROR_PATH).getString(key));
    }
    public CustomException(String key,String message){
        super(ResourceBundle.getBundle(CommandManager.ERROR_PATH).getString(key)+":"+message);
    }
}
