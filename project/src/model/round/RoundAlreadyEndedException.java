/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.round;

/**
 *
 * @author atamon
 */
public class RoundAlreadyEndedException extends Exception {
    
    public RoundAlreadyEndedException() {
        super("Round can only end once!");
    }
    
    public RoundAlreadyEndedException(String s) {
        super(s);
    }
}