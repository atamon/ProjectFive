/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tools;

/**
 *
 * @author Victor Lindhé
 */
public enum Direction {
    CLOCKWISE (1), ANTICLOCKWISE (-1), NONE (0);
    
    private final int direction;
    
    private Direction(int direction){
        this.direction = direction;
    }   
    
    public int getValue(){
        return this.direction;
    }
}
