/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tools;

/**
 *
 * @author Victor Lindhé
 */
public class Direction {

    private static final int CLOCKWISE = 1;
    private static final int ANTICLOCKWISE = -1;
    private static final int NONE = 0;
    
    private boolean steeringClockWise;
    private boolean steeringAntiClockWise;
    
    public void steerClockWise(boolean bool) {
        this.steeringClockWise = bool;
    }
    
    public void steerAntiClockWise(boolean bool) {
        this.steeringAntiClockWise = bool;
    }

    public int getValue() {
        int value = 0;
        value = steeringClockWise ? value+CLOCKWISE : value;
        value = steeringAntiClockWise ? value+ANTICLOCKWISE : value;
        
        return value;
    }
}
