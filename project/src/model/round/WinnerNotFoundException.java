/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.round;

/**
 *
 * @author atamon
 */
public class WinnerNotFoundException extends RuntimeException {
    
    public WinnerNotFoundException(String message) {
        super(message);
    }
}
