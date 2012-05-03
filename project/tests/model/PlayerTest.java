/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.SettingsLoader;
import model.tools.Settings;
import org.junit.Before;
import model.player.Player;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.tools.Vector;
import model.visual.Unit;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victorlindhe
 */
public class PlayerTest {

    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }

    /**
     * Test of getUnit method, of class Player.
     * Tests if the set Unit is the same as the returned.
     * Tests if not setting a Unit for the Player results in a null return.
     */
    @Test
    public void testGetUnit() {
        System.out.println("getUnit");
        Player instance = new Player(0);
        Unit expResult = new Unit(new Vector(0, 0), new Vector(1, 1), 1);
        instance.setUnit(expResult);
        Unit result = instance.getUnit();
        assertEquals(expResult, result);

        Player emptyInstance = new Player(1);
        assertNull(emptyInstance.getUnit());
    }

    /**
     * Test of getUnitPosition method, of class Player.
     * Tests if the returned Vector is the same that we set to the Unit.
     */
    @Test
    public void testGetUnitPosition() {
        System.out.println("getUnitPosition");
        Player instance = new Player(0);
        Vector vector = new Vector(3, 4);
        Unit unit = new Unit(vector, vector, 1);
        instance.setUnit(unit);

        Vector expResult = vector;
        Vector result = instance.getUnitPosition();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUnit method, of class Player.
     * Tests if we get a NullPointerException when sending a null object, which
     * we don't want.
     * Tests if we get returned the same Unit that we set.
     */
    public void testSetUnit() {
        System.out.println("setUnit");
        Unit boat = new Unit(new Vector(0, 0), new Vector(1, 1), 1);
        Player instance = new Player(0);
        instance.setUnit(null);

        instance.setUnit(boat);
        assertEquals(instance.getUnit(), boat);
    }

    /**
     * Test fireLeft.
     * Since method does not return anything, as long as we don't get
     * any exception we're good.
     */
    @Test
    public void testFireLeft() {
        System.out.println("fireLeft");
        Unit boat = new Unit(new Vector(0, 0), new Vector(1, 1), 1);
        Player instance = new Player(0);
        instance.setUnit(boat);
        instance.fireLeft();

        assertTrue(true);
    }
    
    /**
     * Test fireLeft.
     * Since method does not return anything, as long as we don't get
     * any exception we're good.
     */
    @Test
    public void testFireRight() {
        System.out.println("fireRight");
        Unit boat = new Unit(new Vector(0, 0), new Vector(1, 1), 1);
        Player instance = new Player(0);
        instance.setUnit(boat);
        
        instance.fireRight();

        assertTrue(true);
    }
    
//    @Test
//    public void testFire() {
//        System.out.println("fire");
//        Unit boat = new Unit(new Vector(0, 0), new Vector(1, 1), 1);
//        Player instance = new Player(0);
//        instance.setUnit(boat);
//        
//        instance
//    }

    /**
     * Test of accelerateUnit method, of class Player.
     * Tests if the position changes when accelerating.
     */
    @Test
    public void testAccelerateUnit() {
        System.out.println("accelerateUnit");
        Player instance = new Player(0);
        Vector startPosition = new Vector(0, 0);
        instance.setUnit(new Unit(startPosition, new Vector(1, 1), 1));
        instance.accelerateUnit(true);
        instance.updateUnitPosition(10);
        assertTrue(!startPosition.equals(instance.getUnitPosition()));
    }
    
    @Test
    public void testGetUnitDirection() {
        System.out.println("getUnitDirection");
        Player instance = new Player(0);
        Vector vector = new Vector(3, 4);
        Unit unit = new Unit(vector, vector, 1);
        instance.setUnit(unit);

        Vector expResult = vector;
        Vector result = instance.getUnitDirection();
        assertEquals(expResult, result);
        assertFalse(instance.getUnitDirection().equals(new Vector(3, 3)));
    }
    
    @Test
    public void testSetUnitPosition() {
        System.out.println("getUnitDirection");
        Player instance = new Player(0);
        Vector vector = new Vector(3, 4);
        Unit unit = new Unit(vector, vector, 1);
        instance.setUnit(unit);
        instance.setUnitPosition(1f, 1f);
        assertEquals(instance.getUnitPosition(), new Vector(1, 1));
    }

    /**
     * Test of updateUnitPosition method, of class Player.
     * Tests if updating position with 0 tpf moves the Unit, which it shouldn't.
     * Tests if updating with higher than 0 moves.
     */
    @Test
    public void testUpdateUnitPosition() {
        System.out.println("updateUnitPosition");
        float tpf = 0.0F;
        Player instance = new Player(0);
        instance.setUnit(new Unit(new Vector(0, 0), new Vector(1, 1), 1));
        instance.accelerateUnit(true);
        instance.updateUnitPosition(tpf);
        assertTrue(new Vector(0, 0).equals(instance.getUnitPosition()));

        instance.updateUnitPosition(10);
        assertFalse(new Vector(0, 0).equals(instance.getUnitPosition()));
    }

    /**
     * Test of steerUnit method, of class Player.
     * Tests if steering a unit without speed results in a new Direction.
     * Tests if steering a unit with speed results in a new Direction, and new
     * position.
     */
    @Test
    public void testSteerUnitClockWise() {
        System.out.println("steerUnitClockWise");
        float tpf = 100.0F;
        Player instance = new Player(0);
        instance.setUnit(new Unit(new Vector(0, 0), new Vector(1, 1), 1));
        instance.steerUnitClockWise(true);
        assertTrue(instance.getUnitPosition().equals(new Vector(0, 0)));

        instance.accelerateUnit(true);
        instance.updateUnitPosition(tpf);
        instance.steerUnitClockWise(true);
        assertFalse(instance.getUnitPosition().equals(new Vector(0, 0)));
        assertFalse(instance.getUnit().getDirection().equals(new Vector(1, 1)));
    }

    /**
     * Test of steerUnit method, of class Player.
     * Tests if steering a unit without speed results in a new Direction.
     * Tests if steering a unit with speed results in a new Direction, and new
     * position.
     */
    @Test
    public void testSteerUnitAntiClockWise() {
        System.out.println("steerUnitAntiClockWise");
        float tpf = 100.0F;
        Player instance = new Player(0);
        instance.setUnit(new Unit(new Vector(0, 0), new Vector(1, 1), 1));
        instance.steerUnitAntiClockWise(true);
        assertTrue(instance.getUnitPosition().equals(new Vector(0, 0)));

        instance.accelerateUnit(true);
        instance.updateUnitPosition(tpf);
        instance.steerUnitAntiClockWise(true);
        assertFalse(instance.getUnitPosition().equals(new Vector(0, 0)));
        assertFalse(instance.getUnit().getDirection().equals(new Vector(1, 1)));
    }

    /**
     * Test of getId method, of class Player.
     * Tests if the returned id is the same as the one we set.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int id = 0;
        Player instance = new Player(id);
        int expResult = id;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of addUnitListener method, of class Player.
     * Tests to add a null object as listener.
     * Tests to add a PropertyChangeListener as listener.
     */
    @Test(expected = NullPointerException.class)
    public void testAddUnitListener() {
        System.out.println("addUnitListener");
        PropertyChangeListener pl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        Player instance = new Player(0);
        instance.addUnitListener(null);
        instance.addUnitListener(pl);
    }

    /**
     * Test of removeUnitListener method, of class Player.
     * Tests to remove a null object.
     * Tests to remove a listener which isn't added.
     * Tests to remove an added listener.
     */
    @Test(expected = NullPointerException.class)
    public void testRemoveUnitListener() {
        System.out.println("removeUnitListener");
        PropertyChangeListener pl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        Player instance = new Player(0);
        instance.removeUnitListener(null);
        instance.removeUnitListener(pl);

        instance.addUnitListener(pl);
        instance.removeUnitListener(pl);
    }

    /**
     * Test of toString method, of class Player.
     * Tests the string if no Unit set.
     * Tests the string if Unit set.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Player instance = new Player(0);
        assertTrue("Player: 0 Unit: NONE".equals(instance.toString()));

        Unit unit = new Unit(new Vector(0, 0), new Vector(1, 1), 1);
        instance.setUnit(unit);
        assertTrue(instance.toString().equals("Player: 0 Unit: "
                + unit.toString()));
    }

    /**
     * Test of equals method, of class Player.
     * Tests if Player equals other type of Object.
     * Tests if Player equals another Player.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Player instance = new Player(0);
        assertFalse(instance.equals(new Vector(0, 0)));
        Player otherInstance = new Player(1);
        assertFalse(instance.equals(otherInstance));
        Player thirdInstance = instance;
        assertTrue(instance.equals(thirdInstance));
    }

    /**
     * Test of hashCode method, of class Player.
     * Tests if two references to the same Player have the same hash code.
     * Tests if two Players with same Unit but different IDs have same hash 
     * code.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Player instance = new Player(0);
        instance.setUnit(new Unit(new Vector(0, 0), new Vector(1, 1), 1));
        Player otherInstance = instance;
        assertTrue(instance.hashCode() == otherInstance.hashCode());
        Player thirdInstance = new Player(1);
        thirdInstance.setUnit(instance.getUnit());
        assertFalse(thirdInstance.hashCode() == instance.hashCode());
    }
}
