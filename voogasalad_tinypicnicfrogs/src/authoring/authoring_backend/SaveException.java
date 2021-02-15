package authoring.authoring_backend;

public class SaveException extends RuntimeException {
    public SaveException(){
        super("Error Saving Game");
    }
}
