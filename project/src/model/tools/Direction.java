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

    public static final Direction CLOCKWISE = new Direction(1);
    public static final Direction ANTICLOCKWISE = new Direction(-1);
    public static final Direction NONE = new Direction(0);
    private final int direction;

    /**
     * Creates a direction with value -1, 0 or 1
     * @param direction 
     */
    private Direction(int directionValue) {
        if (Math.abs(directionValue) == 1 || directionValue == 0) {
            this.direction = directionValue;
        } else {
            throw new IllegalArgumentException("ERROR: Illegal Direction created with value: " + 
                    directionValue + ":'(");
        }
    }

    public Direction(Direction direction) {
        this(direction.getValue());
    }

    public int getValue() {
        return this.direction;
    }

    public static Direction addDirections(Direction dir1, Direction dir2) {
        return new Direction(dir1.getValue() + dir2.getValue());
    }
}
