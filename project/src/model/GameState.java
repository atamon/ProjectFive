/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author atamon
 */
public enum GameState {
    ACTIVE (1),
    INACTIVE (0),
    STATS (2);
    
    private final int state;
    
    private GameState(int state) {
        this.state = state;
    }
    
    private int getValue() {
        return state;
    }
}
