/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.visual.Unit;
import model.tools.Direction;
import model.tools.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anton Lindgren
 */
public class UnitTest {

    private Unit unit = new Unit(new Vector(0, 0), new Vector(1, 0));

    /**
     * Test of updatePosition method, of class Unit.
     */
    @Test
    public void testUpdateUnit() {
        int iterations = 10;
        Vector zeroVector = new Vector(0, 0);
        // What should be checked is that the unit is updated towards the right
        // direction. IE that the position vector updates according to the direction vector

        //Init stuff
        unit.setPosition(zeroVector);
        unit.setSteerAngle(25f);
        unit.setSpeed(unit.getMaxSpeed());

        // Move west
        moveInDirection(new Vector(-1, 0), iterations);
        // Unit should have moved only in x
        Vector pos = unit.getPosition();
        assertTrue(pos.getX() < 0 && pos.getY() == 0);
        unit.setPosition(zeroVector);

        // Move east
        moveInDirection(new Vector(1, 0), iterations);
        // Should only have moved in x, but +
        pos = unit.getPosition();
        assertTrue(pos.getX() > 0 && pos.getY() == 0);
        unit.setPosition(zeroVector);

        // Move north
        moveInDirection(new Vector(0, 1), iterations);
        // Should only have moved in x, but +
        pos = unit.getPosition();
        assertTrue(pos.getX() == 0 && pos.getY() > 0);
        unit.setPosition(zeroVector);

        // Move south
        moveInDirection(new Vector(0, -1), iterations);
        // Should only have moved in x, but +
        pos = unit.getPosition();
        assertTrue(pos.getX() == 0 && pos.getY() < 0);
        unit.setPosition(zeroVector);
        
        // With speed set to 0 the boat should not move, no matter the direction
        unit.setSpeed(0);
        moveInDirection(new Vector(0, 1), 1000);
        assertTrue(unit.getPosition().equals(zeroVector));
    }

    /**
     * Helper for TestUpdateUnit
     * @param dir
     * @param iterations 
     */
    private void moveInDirection(Vector dir, int iterations) {
        unit.setDirection(dir);
        for (int i = 0; i < iterations; i++) {
            unit.updateUnit(test.Utils.simulateTpf());
        }
    }

    /**
     * Test of steerClockwise method, of class Unit.
     */
    @Test
    public void testSteer() {
        int iterations = 10;
        // We should be able to steer round and round and round in both directions
        // IE From startingdir dir=(1,0) clockwise steering should increase Math.sin for direction vector
        // and anti-clockwise should decrease it. At least for a few iterations
        unit.setSpeed(1);
        unit.setSteerAngle(25f);
        unit.setDirection(new Vector(1, 0));

        // Clockwise
        steerInDirection(Direction.CLOCKWISE, iterations);
        double sin = Math.sin(unit.getDirection().getY()/unit.getDirection().getLength());
        assertTrue(sin > 0);
        unit.setDirection(new Vector(1, 0));
        
        // Anti-clockwise
        steerInDirection(Direction.ANTICLOCKWISE, iterations);
        sin = Math.sin(unit.getDirection().getY()/unit.getDirection().getLength());
        assertTrue(sin < 0);
    }

    /**
     * Helper for TestSteer
     * @param direction
     * @param iterations 
     */
    private void steerInDirection(Direction direction, int iterations) {
        for (int i = 0; i < iterations; i++) {
            unit.steer(direction, test.Utils.simulateTpf());
        }
    }

    /**
     * Test of setSteerAngle method, of class Unit.
     */
    @Test
    public void testSetSteerAngle() {
        // It must change the value of steerAngle
        float angle = unit.getSteerAngle();
        unit.setSteerAngle(angle + 25f);
        assertTrue(unit.getSteerAngle() - 25f == angle);
    }

    /**
     * Test of setDirection method, of class Unit.
     */
    @Test
    public void testSetDirection() {
        // The two overloaded methods should set the same direction
        unit.setDirection(new Vector(25f, 25f));
        Vector direction = unit.getDirection();
        unit.setDirection(25f, 25f);
        assertTrue(direction.equals(unit.getDirection()));
    }

    /**
     * Test of setPosition method, of class Unit.
     */
    @Test
    public void testSetPosition() {
        // The two overloaded methods should set the same direction
        unit.setPosition(new Vector(25f, 25f));
        Vector position = unit.getPosition();
        unit.setPosition(25f, 25f);
        assertTrue(position.equals(unit.getPosition()));
    }

    /**
     * Test of setSpeed method, of class Unit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetSpeed() {
        // Reset
        unit.setSpeed(0);
        assertTrue(unit.getSpeed() == 0);
        // Should cast exception
        unit.setSpeed(Float.MIN_VALUE);
        unit.setSpeed(Float.POSITIVE_INFINITY);
    }

    /**
     * Test of setAcceleration method, of class Unit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetAcceleration() {
        unit.setAcceleration(Integer.MAX_VALUE);
        assertTrue(unit.getAcceleration() == Integer.MAX_VALUE);
        unit.setAcceleration(Integer.MIN_VALUE);
    }

    /**
     * Test of setHitPoints method, of class Unit.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSetHitPoints() {
        int HPMax = unit.getHitPointsMax();
        unit.setHitPoints(HPMax);
        assertTrue(unit.getHitPoints() == HPMax);
        unit.setHitPoints(Integer.MIN_VALUE);
    }

    /**
     * Test of getPosition method, of class Unit.
     */
    @Test
    public void testGetPosition() {
        Vector vector = unit.getPosition();
        assertTrue(vector.equals(unit.getPosition()) && vector != unit.getPosition());
    }

    /**
     * Test of getDirection method, of class Unit.
     */
    @Test
    public void testGetDirection() {
        Vector vector = unit.getDirection();
        assertTrue(vector.equals(unit.getDirection()) && vector != unit.getDirection());
        assertTrue(unit.getDirection().getLength() == 1);
    }

    /**
     * Test of toString method, of class Unit.
     */
    @Test
    public void testToString() {
        assertTrue(unit.toString() instanceof String && unit.toString().length() > 0);
    }

    /**
     * Test of equals method, of class Unit.
     */
    @Test
    public void testEquals() {
        // These two should be equal but not ==
        Unit unit1 = new Unit(new Vector(0, 0), new Vector(1, 0));
        Unit unit2 = new Unit(new Vector(0, 0), new Vector(1, 0));
        assertTrue(unit1.equals(unit2) && unit1 != unit2);
    }
}
