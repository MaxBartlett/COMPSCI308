package Backend.Exceptions;
/**
 * @author Michael Glushakov
 */
public class InvalidVariableCallException extends CustomException {
    public static String MY_KEY="InvalidVariableCallException";
    public InvalidVariableCallException(){
        super(MY_KEY);
    }
}
