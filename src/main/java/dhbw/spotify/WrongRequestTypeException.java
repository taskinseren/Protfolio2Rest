package dhbw.spotify;

public class WrongRequestTypeException extends Exception {
    public WrongRequestTypeException(String error){
        super(error);
    }
}
