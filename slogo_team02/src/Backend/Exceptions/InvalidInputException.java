package Backend.Exceptions;
/**
 * @author Michael Glushakov
 */
public class InvalidInputException extends CustomException {
    private static String MY_KEY="InvalidInputException";
    public InvalidInputException(){
        super(MY_KEY);
    }
    public InvalidInputException(String str){
        super(MY_KEY,str);
    }
}
