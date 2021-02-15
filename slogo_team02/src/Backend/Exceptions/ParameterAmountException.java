package Backend.Exceptions;
/**
 * @author Michael Glushakov
 */
public class ParameterAmountException extends CustomException {
    private static String MY_KEY="ParameterAmountException";
    public ParameterAmountException(){
        super(MY_KEY);
    }
}
