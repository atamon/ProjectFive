/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.settings.SettingsLoader;
import model.settings.Settings;
import org.junit.Before;
import model.player.Player;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import model.visual.Battlefield;
import model.visual.Unit;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victorlindhe
 */
public class PlayerTest {

    private Unit unit;
    private Player player;
    private Battlefield bf;
    private boolean fireCreatedCB = false;

    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
        player = new Player(0);
        int uSize = Settings.getInstance().getSetting("unitSize");
        unit = new Unit(Battlefield.getStartingPosition(0, new Vector(100, 1, 100)),
                Battlefield.getStartingDir(0),
                new Vector(uSize, uSize, uSize),
                Settings.getInstance().getSetting("unitMass"));
        bf = new Battlefield();

        PropertyChangeListener pListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                if ("CannonBall Created".equals(pce.getPropertyName())) {
                    fireCreatedCB = true;
                }
            }
        };
        player.setUnit(unit);
        bf.addToBattlefield(unit);
        player.addPropertyChangeListener(pListener);
        
    }

    /**
     * Test of getUnit method, of class Player. Tests if the set Unit is the
     * same as the returned. Tests if not setting a Unit for the Player results
     * in a null return.
     */
    @Test
    public void testGetUnit() {
        player.setUnit(unit);
        assertSame(unit, player.getUnit());

        Player emptyInstance = new Player(1);
        assertNull(emptyInstance.getUnit());
    }

    /**
     * Test of setUnit method, of class Player. Tests if we get a
     * NullPointerException when sending a null object, which we don't want.
     * Tests if we get returned the same Unit that we set.
     */
    public void testSetUnit() {
        Player instance = new Player(0);
        instance.setUnit(null);

        instance.setUnit(unit);
        assertEquals(instance.getUnit(), unit);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncreaseFirePowerLeft() {
        player.increaseFirePowerLeft(10f);
        assertTrue(true);
        player.increaseFirePowerLeft(-0.1f);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIncreaseFirePowerRight() {
        player.increaseFirePowerRight(10f);
        assertTrue(true);
        player.increaseFirePowerRight(-0.1f);
    }
    
    @Test
    public void testCanUnitFire() {
        player.getUnit().update(10);
        assertTrue(player.canUnitFire());
        player.fireLeft();
        assertFalse(player.canUnitFire());
    }

    /**
     * Test fireLeft.
     */
    @Test
    public void testFireLeft() {
        fireCreatedCB = false;
        player.setUnit(unit);
        player.fireLeft();

        assertTrue(fireCreatedCB);
    }

    /**
     * Test fireRight.
     */
    @Test
    public void testFireRight() {
        fireCreatedCB = false;
        player.setUnit(unit);
        player.fireRight();

        assertTrue(fireCreatedCB);
    }

    /**
     * Test of accelerateUnit method, of class Player. Tests if the position
     * changes when accelerating.
     */
    @Test
    public void testAccelerateUnit() {
        // TODO FIX THIS SHIT
        player.setUnit(unit);
        player.getUnit().halt();
        player.accelerateUnit(true);
        // Simulate frame
        bf.update(0.016f);
        assertTrue(unit.getSpeed() > 0);
    }

    @Test
    public void testSteerUnitClockWise() {
        player.steerUnitClockWise(true);
        Unit pUnit = player.getUnit();
        Vector firstDir = pUnit.getDirection();
        player.accelerateUnit(true);
        // Simulate a frame
        pUnit.update(0.016f);
        player.steerUnitClockWise(false);
        assertFalse(firstDir.equals(pUnit.getDirection()));
    }

    @Test
    public void testSteerUnitAntiClockWise() {
        player.steerUnitAntiClockWise(true);
        Unit pUnit = player.getUnit();
        Vector firstDir = pUnit.getDirection();
        player.accelerateUnit(true);
        // Simulate a frame
        pUnit.update(0.016f);
        player.steerUnitAntiClockWise(false);
        assertFalse(firstDir.equals(pUnit.getDirection()));

    }

    /**
     * Test of getId method, of class Player. Tests if the returned id is the
     * same as the one we set.
     */
    @Test
    public void testGetId() {
        int id = 0;
        Player instance = new Player(id);
        int expResult = id;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of addUnitListener method, of class Player. Tests to add a null
     * object as listener. Tests to add a PropertyChangeListener as listener.
     */
    @Test
    public void testAddUnitListener() {
        PropertyChangeListener pl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                assertTrue(pce.getSource() instanceof Unit);
            }
        };
        Player instance = new Player(0);
        instance.setUnit(unit);
        instance.addUnitListener(null);
        instance.addUnitListener(pl);
        unit.announceRemoval();
    }

    /**
     * Test of removeUnitListener method, of class Player. Tests to remove a
     * null object. Tests to remove a listener which isn't added. Tests to
     * remove an added listener.
     */
    @Test
    public void testRemoveUnitListener() {
        PropertyChangeListener pl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                fail("RemoveUnitListener did not remove added listener from unit!");
            }
        };
        Player instance = new Player(0);
        instance.setUnit(unit);
        instance.removeUnitListener(null);
        instance.removeUnitListener(pl);

        instance.addUnitListener(pl);
        instance.removeUnitListener(pl);
        unit.announceRemoval();
    }

    /**
     * Test of toString method, of class Player. Tests the string if no Unit
     * set. Tests the string if Unit set.
     */
    @Test
    public void testToString() {
        Player instance = new Player(0);
        assertTrue("Player: 0 Unit: NONE".equals(instance.toString()));

        instance.setUnit(unit);
        assertTrue(instance.toString().equals("Player: 0 Unit: "
                + unit.toString()));
    }

    /**
     * Test of equals method, of class Player. Tests if Player equals other type
     * of Object. Tests if Player equals another Player.
     */
    @Test
    public void testEquals() {
        Player instance = new Player(0);
        assertFalse(instance.equals(new Vector(0, 0, 0)));
        Player otherInstance = new Player(1);
        assertFalse(instance.equals(otherInstance));
        Player thirdInstance = instance;
        assertTrue(instance.equals(thirdInstance));
    }

    /**
     * Test of hashCode method, of class Player. Tests if two references to the
     * same Player have the same hash code. Tests if two Players with same Unit
     * but different IDs have same hash code.
     */
    @Test
    public void testHashCode() {
        Player instance = new Player(0);
        instance.setUnit(unit);
        Player otherInstance = instance;
        assertTrue(instance.hashCode() == otherInstance.hashCode());
        Player thirdInstance = new Player(1);
        thirdInstance.setUnit(instance.getUnit());
        assertFalse(thirdInstance.hashCode() == instance.hashCode());
    }
}
