/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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
    public void testUpdatePosition() {
        fail("Not implemented yet!");
    }

    /**
     * Test of accelerate method, of class Unit.
     */
    @Test
    public void testAccelerate() {
        //Should (somewhat) linearly increase speed
        Unit unit = new Unit(new Vector(0, 0), new Vector(1, 0));
        unit.setSpeed(0);
        // The method should never increase speed above the unit's maxSpeed.
        while (true) {
            float lastSpeed = unit.getSpeed();
            // Some kind of updatefrequency-simulation
            unit.accelerate(test.Utils.simulateTpf());
            assertTrue(lastSpeed <= unit.getSpeed());

            if (lastSpeed == unit.getSpeed()) {
                assertTrue(unit.getSpeed() == unit.getMaxSpeed());
                break;
            }
        }
    }

    /**
     * Test of retardate method, of class Unit.
     */
    @Test
    public void testRetardate() {
        unit.setSpeed(unit.getMaxSpeed());
        boolean reachedZeroSpeed = false;
        while (true) {
            unit.retardate(test.Utils.simulateTpf());
            // Make sure speed >= 0 and when 0 is reached 
            // check once more if we can go below 0
            assertTrue(unit.getSpeed() >= 0);
            if (reachedZeroSpeed) {
                return;
            }
            if (unit.getSpeed() == 0) {
                reachedZeroSpeed = true;
            }
        }
    }

    /**
     * Test of steerClockwise method, of class Unit.
     */
    @Test
    public void testSteerClockwise() {
        // With a constant speed > 0 && unchanged steerAngle I should
        // be able to make a full circle combined with updateposition.
        // I.E start and end up at the same position eventually in a loop.
        unit.setSteerAngle((float) Math.PI);
        unit.setSpeed(unit.getMaxSpeed());
        Vector startingPos = unit.getPosition();
        while (true) {
            float simulatedTpf = test.Utils.simulateTpf();
            unit.updatePosition(simulatedTpf);
            // Simulate varying update-frequencies because it should make a circle anyways
            unit.steerClockwise(simulatedTpf);
            Vector diffVector = new Vector(Math.abs(startingPos.getX()) - unit.getPosition().getX(),
                    Math.abs(startingPos.getY() - unit.getPosition().getY()));
            float diffLength = diffVector.getLength();
            if (diffLength <= Unit.ACCEPTED_STEER_DIFF) {
                assertTrue(diffLength <= Unit.ACCEPTED_STEER_DIFF);
                break;
            }
        }
        // TODO Add forever growing circle if increasing steerAngle
        // TODO Add decreaseing circle crashing into itself (lastpos.equals(thispos)
        //      if decreaseing steerAngle
    }

    /**
     * Test of steerAntiClockwise method, of class Unit.
     */
    @Test
    public void testSteerAntiClockwise() {
        // With a constant speed > 0 && unchanged steerAngle I should
        // be able to make a full circle combined with updateposition.
        // I.E start and end up at the same position eventually in a loop.
        unit.setSteerAngle((float) Math.PI);
        unit.setSpeed(unit.getMaxSpeed());
        Vector startingPos = unit.getPosition();
        while (true) {
            float simulatedTpf = test.Utils.simulateTpf();
            unit.updatePosition(simulatedTpf);
            // Simulate varying update-frequencies because it should make a circle anyways
            unit.steerAntiClockwise(simulatedTpf);
                Vector diffVector = new Vector(Math.abs(startingPos.getX()) - unit.getPosition().getX(),
                    Math.abs(startingPos.getY() - unit.getPosition().getY()));
            float diffLength = diffVector.getLength();
            if (diffLength <= Unit.ACCEPTED_STEER_DIFF) {
                assertTrue(diffLength <= Unit.ACCEPTED_STEER_DIFF);
                break;
            }
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
    @Test (expected = IllegalArgumentException.class)
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
    @Test (expected = IllegalArgumentException.class)
    public void testSetAcceleration() {
        unit.setAcceleration(Integer.MAX_VALUE);
        assertTrue(unit.getAcceleration() == Integer.MAX_VALUE);
        unit.setAcceleration(Integer.MIN_VALUE);
    }

    /**
     * Test of setHitPoints method, of class Unit.
     */
    @Test (expected = IllegalArgumentException.class)
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
