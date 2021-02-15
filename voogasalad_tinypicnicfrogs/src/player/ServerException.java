package player;

/**
 * @author Christopher Lin
 */
public class ServerException extends Exception {
    Exception myException;
    ServerException(Exception e){
        myException = e;
    }

    public Exception getException(){
        return myException;
    }
}
