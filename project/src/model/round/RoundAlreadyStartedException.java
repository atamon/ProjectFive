/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.round;

/**
 *
 * @author atamon
 */
public class RoundAlreadyStartedException extends Exception {
    
    public RoundAlreadyStartedException() {
        super("A round can only be started once!");
    }
    public RoundAlreadyStartedException(String s) {
        super(s);
    }
}
