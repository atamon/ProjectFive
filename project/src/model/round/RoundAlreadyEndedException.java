package model.round;

/**
 * Exception to be thrown if round already ended.
 **/
public class RoundAlreadyEndedException extends Exception {
    
    public RoundAlreadyEndedException() {
        super("Round can only end once!");
    }
    
    public RoundAlreadyEndedException(String s) {
        super(s);
    }
}
