/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.settings.SettingsLoader;
import model.visual.Unit;
import math.Vector;
import model.settings.Settings;
import model.visual.Battlefield;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Anton Lindgren
 */
public class UnitTest {

    private Unit unit;
    private Battlefield bf;

    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
        int uSize = Settings.getInstance().getSetting("unitSize");
        unit = new Unit(Battlefield.getStartingPosition(0, new Vector(100, 1, 100)),
                Battlefield.getStartingDir(0),
                new Vector(uSize, uSize, uSize),
                Settings.getInstance().getSetting("unitMass"));
        bf = new Battlefield();
        bf.addToBattlefield(unit);
    }

    /**
     * Test of update method, of class Unit.
     */
    @Test
    public void testUpdate() {
        Vector pos = unit.getPosition();
        Vector dir = unit.getDirection();
        unit.setIsAccelerating(true);
        unit.steerClockWise(true);
        // Calls unit.update() and updates physical world
        bf.update(0.076f);
        assertFalse(pos.equals(unit.getPosition()) || dir.equals(unit.getDirection()));
    }

    /**
     * Test of setSteerAngle method, of class Unit.
     */
    @Test
    public void testSetSteerAngle() {
        unit.setSteerAngle(5f);
        assertTrue(unit.getSteerAngle() == 5f);
        unit.setSteerAngle(Settings.getInstance().getSetting("steerAngle"));
        assertTrue(unit.getSteerAngle() == Settings.getInstance().getSetting("steerAngle"));
    }

    @Test
    public void testGetSteerAngle() {
        float angle = unit.getSteerAngle();
        unit.setSteerAngle(angle + 5f);
        assertTrue(unit.getSteerAngle() - 5f == angle);
    }

    @Test
    public void testSteerClockWise() {
        unit.steerAntiClockWise(false);
        Vector dir = unit.getDirection();
        unit.steerClockWise(true);
        bf.update(0.016f);
        assertFalse(dir.equals(unit.getDirection()));
        
        //Testing that it stands exactly still is redundant since we don't need it
    }

    @Test
    public void testSteerAntiClockWise() {
        unit.steerClockWise(false);
        Vector dir = unit.getDirection();
        unit.steerAntiClockWise(true);
        bf.update(0.016f);
        assertFalse(dir.equals(unit.getDirection()));

        //Testing that it stands exactly still is redundant since we don't need it
    }

    @Test
    public void testSetIsAccelerating() {
        unit.halt();
        unit.setIsAccelerating(false);
        bf.update(0.016f);
        // Just needs to be small, not zero since we have gravity that applies and all
        assertTrue(Math.floor(unit.getSpeed()) == 0);
        unit.setIsAccelerating(true);
        bf.update(0.016f);
        assertTrue(unit.getSpeed() > 0);
    }

    /**
     * Test of setHitPoints method, of class Unit.
     */
    @Test
    public void testSetHitPoints() {
        int HPMax = unit.getHitPointsMax();
        unit.setHitPoints(HPMax);
        assertTrue(unit.getHitPoints() == HPMax);
        unit.setHitPoints(HPMax+1);
        assertTrue(unit.getHitPoints() == unit.getHitPointsMax());
    }

    @Test
    public void testGetHitPoints() {
        unit.setHitPoints(0);
        assertTrue(unit.getHitPoints() == 0);
    }

    @Test
    public void testGetHitPointsMax() {
        assertTrue(unit.getHitPointsMax() >= unit.getHitPoints());
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
        Unit unit2 = new Unit(unit.getPosition(), unit.getDirection(), unit.getSize(), unit.getPhysicalObject().getMass());
        Unit unit3 = new Unit(unit.getPosition(), unit.getDirection(), unit.getSize(), unit.getPhysicalObject().getMass());
        assertTrue(!unit3.equals(unit2) && !unit2.equals(unit3) && unit2.equals(unit2));
    }
}
