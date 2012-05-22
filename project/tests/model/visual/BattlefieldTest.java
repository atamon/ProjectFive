package model.visual;

import model.settings.SettingsLoader;
import model.settings.Settings;
import math.Vector;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Battlefield
 */
public class BattlefieldTest {

    private Battlefield bField;
    private Unit unit;
    private IMoveable movBall;
    /**
     * Tests to instatiate a Battlefield object with default size.
     */
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
        bField = new Battlefield(new Vector(100, 1, 100));
        int uSize = Settings.getInstance().getSetting("unitSize");
        unit = new Unit(Battlefield.getStartingPosition(0, bField.getSize()),
                                                        Battlefield.getStartingDir(0),
                                                        new Vector(uSize, uSize, uSize),
                                                        Settings.getInstance().getSetting("unitMass"));
        movBall = new CannonBall(new Vector(unit.getPosition()),
                                          new Vector(unit.getDirection()),
                                          new Vector(0.1f, 0.1f, 0.1f),
                                          (Settings.getInstance().getSetting("cannonBallMass")),
                                          (Settings.getInstance().getSetting("cannonBallSpeed"))*1f, unit);
    }

    /**
     * Tests to create a Battlefield with negative size.
     */
    @Test(expected = NumberFormatException.class)
    public void testConstructorArgument() {
        Battlefield secondBField = new Battlefield(new Vector(-50, 1, -50));
    }

    /**
     * Test of getSize method, of class Battlefield. Tests if we get the sizes
     * we should.
     */
    @Test
    public void testGetSize() {
        assertTrue(bField.getSize().equals(new Vector(100.0f, 1, 100.0f)));
    }

    /**
     * Test of getPosition method, of class Battlefield. Tests if the position
     * is origo (nullvector).
     *
     */
    @Test
    public void testGetPosition() {
        assertTrue(bField.getPosition().equals(new Vector(0, 0, 0)));
    }

    /**
     * Test of getCenter method, of class Battlefield.
     */
    @Test
    public void testGetCenter() {
        Vector expFirstSize = new Vector(50, 1, 50);
        assertTrue(expFirstSize.equals(bField.getCenter()));
    }

    /**
     * Test of equals method, of class Battlefield.
     */
    @Test
    public void testEquals() {
        Battlefield secondBField = new Battlefield(new Vector(50.0f, 1, 50.0f));
        assertTrue(bField.equals(bField));
        assertFalse(bField.equals(secondBField));
    }

    /**
     * Test of hashCode method, of class Battlefield.
     */
    @Test
    public void testHashCode() {
        Battlefield secondBField = new Battlefield(new Vector(50.0f, 1, 50.0f));
        assertFalse(bField.hashCode() == secondBField.hashCode());
    }

    /**
     * Test of addToBattlefield method, of class Battlefield.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddToBattlefield() {
        Battlefield instance = new Battlefield();
        instance.addToBattlefield(movBall);
        // Now we crash
        instance.addToBattlefield(movBall);
    }

    /**
     * Test of removeFromBattlefield method, of class Battlefield.
     */
    @Test
    public void testRemoveFromBattlefield() {
        Battlefield instance = new Battlefield();
        instance.addToBattlefield(unit);
        instance.removeFromBattlefield(unit);
        instance.addToBattlefield(unit);
        // Removing should only assert that we can add again without crash.
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
        Vector dir = new Vector(1, 0, 1);
        unit.setPosition(pos);
        unit.setDirection(dir);
        unit.setIsAccelerating(true);
        instance.addToBattlefield(unit);
        instance.update(0.016f);
        assertFalse(pos.equals(unit));
    }

    /**
     * Test of clearForNewRound method, of class Battlefield.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testClear() {        
        Battlefield instance = new Battlefield();
        instance.addToBattlefield(unit);
        instance.addToBattlefield(movBall);
        // Same as removeFromBattlefield, only calls other methods, so no crash = okay
        instance.clearForNewRound();
        instance.addToBattlefield(movBall);
        assertTrue(true);
        // Throws exception
        instance.addToBattlefield(unit);
    }

    /**
     * Test of getStartingPosition method, of class Battlefield.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testGetStartingPosition() {
        Vector bfSize = new Vector(100, 1, 100);
        Vector posZero = new Vector(85f, 4f, 85f);
        Vector posOne = new Vector(15f, 4f, 15f);
        Vector posTwo = new Vector(85f, 4f, 15f);
        Vector posThree = new Vector(15f, 4f, 85f);
        Vector result = Battlefield.getStartingPosition(0, bfSize);
        assertEquals(posZero, result);
        result = Battlefield.getStartingPosition(1, bfSize);
        assertEquals(posOne, result);
        result = Battlefield.getStartingPosition(2, bfSize);
        assertEquals(posTwo, result);
        result = Battlefield.getStartingPosition(3, bfSize);
        assertEquals(posThree, result);
        
        Battlefield.getStartingPosition(4, bfSize);
    }

    /**
     * Test of getStartingDir method, of class Battlefield.
     */
    @Test (expected=IllegalArgumentException.class)
    public void testGetStartingDir() {
        Vector dirZero = new Vector(-1, 0, -1);
        Vector dirOne = new Vector(1, 0, 1);
        Vector dirTwo = new Vector(-1, 0, 1);
        Vector dirThree = new Vector(1, 0, -1);
        Vector result = Battlefield.getStartingDir(0);
        assertEquals(dirZero, result);
        result = Battlefield.getStartingDir(1);
        assertEquals(dirOne, result);
        result = Battlefield.getStartingDir(2);
        assertEquals(dirTwo, result);
        result = Battlefield.getStartingDir(3);
        assertEquals(dirThree, result);
        
        Battlefield.getStartingDir(4);
    }
}
