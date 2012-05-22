package model.round;

/**
 *  Exception to be thrown if round already started.
 */
public class RoundAlreadyStartedException extends Exception {
    
    public RoundAlreadyStartedException() {
        super("A round can only be started once!");
    }
    public RoundAlreadyStartedException(String s) {
        super(s);
    }
}
