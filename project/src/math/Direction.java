/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Direction other = (Direction) obj;
        if (this.steeringClockWise != other.steeringClockWise) {
            return false;
        }
        if (this.steeringAntiClockWise != other.steeringAntiClockWise) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.steeringClockWise ? 1 : 0);
        hash = 53 * hash + (this.steeringAntiClockWise ? 1 : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Direction{" + "steeringClockWise=" + steeringClockWise + ", steeringAntiClockWise=" + steeringAntiClockWise + '}';
    }
    

}
