/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import controller.SettingsLoader;
import java.beans.PropertyChangeEvent;
import model.tools.Settings;
import math.Vector;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victorlindhe
 */
public class BattlefieldTest {

    private Battlefield bField;

    /**
     * Tests to instatiate a Battlefield object with default size.
     */
    @Before
    public void setUp() {
        this.bField = new Battlefield();
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }

    /**
     * Tests to create a Battlefield with negative size.
     */
    @Test(expected = NumberFormatException.class)
    public void testConstructorArgument() {
        Battlefield secondBField = new Battlefield(new Vector(-50, -50));
    }

    /**
     * Test of getSize method, of class Battlefield. Tests if we get the sizes
     * we should.
     */
    @Test
    public void testGetSize() {
        assertTrue(bField.getSize().equals(new Vector(100.0f, 100.0f)));
    }

    /**
     * Test of getPosition method, of class Battlefield. Tests if the position
     * is origo (nullvector).
     *
     */
    @Test
    public void testGetPosition() {
        assertTrue(bField.getPosition().equals(new Vector(0, 0)));
    }

    /**
     * Test of getCenter method, of class Battlefield.
     */
    @Test
    public void testGetCenter() {
        Vector expFirstSize = new Vector(50, 50);
        assertTrue(expFirstSize.equals(this.bField.getCenter()));
    }

    /**
     * Test of equals method, of class Battlefield.
     */
    @Test
    public void testEquals() {
        Battlefield secondBField = new Battlefield(new Vector(50.0f, 50.0f));
        assertTrue(this.bField.equals(this.bField));
        assertFalse(this.bField.equals(secondBField));
    }

    /**
     * Test of hashCode method, of class Battlefield.
     */
    @Test
    public void testHashCode() {
        Battlefield secondBField = new Battlefield(new Vector(50.0f, 50.0f));
        assertFalse(this.bField.hashCode() == secondBField.hashCode());
    }

    /**
     * Test of addToBattlefield method, of class Battlefield.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddToBattlefield() {
        IMoveable mov = new Unit(new Vector(1, -1), new Vector(1, 1), 0);
        Battlefield instance = new Battlefield();
        instance.addToBattlefield(mov);
        // Now we crash
        instance.addToBattlefield(mov);
    }

    /**
     * Test of removeFromBattlefield method, of class Battlefield.
     */
    @Test
    public void testRemoveFromBattlefield() {
        IMoveable mov = new CannonBall(0, new Vector(1, 1), new Vector(1, 0), 10f);
        Battlefield instance = new Battlefield();
        instance.addToBattlefield(mov);
        instance.removeFromBattlefield(mov);
        // It only calls other methods, so if it does not crash it's okay
        assertTrue(true);
    }

    /**
     * Test of update method, of class Battlefield.
     * Will want to force it to perform magellan journey.
     */
    @Test
    public void testUpdate() {
        Battlefield instance = new Battlefield();
        Vector pos = new Vector(instance.getSize());
        Vector dir = new Vector(1,1);
        Unit unit = new Unit(pos, dir, 0);
        unit.setSpeed(10f);
        instance.addToBattlefield(unit);
        instance.update(0.016f);
        assertFalse(pos.equals(unit));
    }

    /**
     * Test of clearForNewRound method, of class Battlefield.
     */
    @Test
    public void testClear() {
        IMoveable mov = new Unit(new Vector(1, 1), new Vector(1, 1), 0);
        Battlefield instance = new Battlefield();
        instance.addToBattlefield(mov);
        mov = new CannonBall(0, new Vector(1,1), new Vector(1,1), 25f);
        instance.addToBattlefield(mov);
        // Same as removeFromBattlefield, only calls other methods, so no crash = okay
        instance.clearForNewRound();
        assertTrue(true);
    }

    @Test (expected=UnsupportedOperationException.class)
    public void testRemove() {
        Battlefield instance = new Battlefield();
        instance.announceRemoval();
    }

    /**
     * Test of getStartingPosition method, of class Battlefield.
     */
    @Test
    public void testGetStartingPosition() {
        Vector bfSize = new Vector(100, 100);
        Vector posZero = new Vector(85f, 85f);
        Vector posOne = new Vector(15f, 15f);
        Vector posTwo = new Vector(85f, 15f);
        Vector posThree = new Vector(15f, 85f);
        Vector result = Battlefield.getStartingPosition(0, bfSize);
        assertEquals(posZero, result);
        result = Battlefield.getStartingPosition(1, bfSize);
        assertEquals(posOne, result);
        result = Battlefield.getStartingPosition(2, bfSize);
        assertEquals(posTwo, result);
        result = Battlefield.getStartingPosition(3, bfSize);
        assertEquals(posThree, result);
    }

    /**
     * Test of getStartingDir method, of class Battlefield.
     */
    @Test
    public void testGetStartingDir() {
        Vector dirZero = new Vector(-1, -1);
        Vector dirOne = new Vector(1, 1);
        Vector dirTwo = new Vector(-1, 1);
        Vector dirThree = new Vector(1, -1);
        Vector result = Battlefield.getStartingDir(0);
        assertEquals(dirZero, result);
        result = Battlefield.getStartingDir(1);
        assertEquals(dirOne, result);
        result = Battlefield.getStartingDir(2);
        assertEquals(dirTwo, result);
        result = Battlefield.getStartingDir(3);
        assertEquals(dirThree, result);
    }
}
