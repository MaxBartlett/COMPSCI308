/**
 * This is a custom exception that handles syntax errors
 * Assumptions: the syntax is thrown by a Command
 * Dependencies: Syntax.properties
 *
 * Usage: throw new InvalidSyntaxException(key)
 *
 *
 * @author Christopher Lin
 */

package Backend.Exceptions;


import Backend.CommandManager;

import java.util.ResourceBundle;

public class InvalidSyntaxException extends CustomException {
    public static final String MY_KEY = "InvalidSyntax";

    public InvalidSyntaxException(String cmdKey){
        super(MY_KEY, ResourceBundle.getBundle(CommandManager.SYNTAX_PATH).getString(cmdKey));
    }
}
