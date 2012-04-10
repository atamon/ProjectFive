/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import unit.Unit;
import util.Direction;
import util.Vector;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import util.Util;
import static org.junit.Assert.*;

/**
 *
 * @author atamon
 */
public class UnitTest {

    private Unit unit = new Unit(new Vector(0, 0), new Vector(1, 0));

    /**
     * Test of updatePosition method, of class Unit.
     */
    @Test
    public void testUpdateUnit() {
        // With a constant speed > 0 && unchanged steerAngle I should
        // be able to make a full circle.
        // I.E start and end up at the same position eventually in a loop.
        unit.setSteerAngle(25f);
        unit.setSpeed(25);
        unit.setDirection(new Vector(1, 0));

        Vector startingPos = unit.getPosition();
        Vector startingDir = unit.getDirection();
        Direction steerDir = Direction.CLOCKWISE;
        float acceptedPosDiff = 0.1f;
        float acceptedDirDiff = 1f;

        for (int i = 0; i < 2; i++) {
            while (true) {
                // Simulate varying update-frequencies because it should make a circle anyways
                float simulatedTpf = test.Utils.simulateTpf();
                unit.steer(steerDir, simulatedTpf);
                unit.updateUnit(simulatedTpf);

                Vector diffVector = Util.getDiffVector(startingDir, unit.getDirection());
                if (diffVector.getLength() <= acceptedPosDiff) {
                    assertTrue(diffVector.getLength() <= acceptedPosDiff);
                    break;
                }
            }
            steerDir = Direction.ANTICLOCKWISE;
        }
        // TODO ? Add forever growing circle if increasing steerAngle
        // TODO ? Add decreaseing circle crashing into itself (lastpos.equals(thispos)
        //      if decreaseing steerAngle
    }

    /**
     * Test of steerClockwise method, of class Unit.
     */
    @Test
    public void testSteer() {
        // With speed != 0 a unit's direction should rotate 2PI radians eventually
        // Since we're updating with a "random" steeringgrade we might skip
        // the actual .equals so we check if we've passed the startingDir instead
        unit.setSpeed(1);
        unit.setSteerAngle(25f);
        unit.setDirection(new Vector(1, 0));
        Vector startDir = unit.getDirection();
        Vector lastDir = new Vector(startDir);
        Vector diffVector = new Vector(0, 0);
        float acceptedDiff = 0.000005f;
        List<Direction> directions = new LinkedList<Direction>();


        int iterations = Integer.MAX_VALUE;
        for (Direction direction : directions) {
            for (int i = 0; i < iterations; i++) {
                unit.steer(direction, test.Utils.simulateTpf());
                if (lastDir.equals(unit.getDirection())) {
                    fail("Fail: Unit.steer() did not rotate Unit's direction!");
                }
                diffVector = Util.getDiffVector(startDir, unit.getDirection());
                if (diffVector.getLength() <= acceptedDiff) {
                    break;
                }
            }
            assertTrue(diffVector.getLength() <= acceptedDiff);
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
