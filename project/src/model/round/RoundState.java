/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.round;

/**
 *
 * @author atamon
 */
public enum RoundState {
    NONE_EXISTANT (-1),
    PLAYING (0),
    PAUSED (1),
    POST (2);
    
    private final int state;
    
    private RoundState(int state) {
        this.state = state;
    }
}
