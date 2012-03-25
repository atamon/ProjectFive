/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Victor Lindhé
 */
public class PlayerTest {

    /**
     * Test of getUnit method, of class Player.
     * Checks if we get null with no Unit set.
     * Checks if we get the same unit that we set.
     */
    @Test
    public void testGetUnit() {
        System.out.println("getUnit");
        Player instance = new Player(1);
        assertNull(instance.getUnit());
        Unit unit = new Unit(new Vector(5f,4f), new Vector(1f, 1f), 100);
        instance.setUnit(unit);
        assertTrue(instance.getUnit().equals(unit));
    }

    /**
     * Test of setUnit method, of class Player.
     * Checks what happens if we set a null object (nothing should happen).
     * Checks if a unit actually is set by comparing to get getters return.
     */
    public void testSetUnit() {
        System.out.println("setUnit");
        Unit boat = null;
        Player instance = new Player(1);
        instance.setUnit(boat);
        Unit unit = new Unit(new Vector(5f,4f), new Vector(1f, 1f), 100);
        instance.setUnit(boat);
        assertTrue(instance.getUnit().equals(unit));
    }

    /**
     * Test of getId method, of class Player.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        int expResult = 0;
        Player instance = new Player(expResult);
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Player.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Player instance = new Player(1);
        assert(instance.toString().getClass().equals(String.class));
    }

    /**
     * Test of equals method, of class Player.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Player player = new Player(1);
        assertFalse(player.equals(obj));
        
        Player playerTwo = player;
        assertTrue(player.equals(player));
    }

    /**
     * Test of hashCode method, of class Player.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Player player = new Player(1);
        Player playerTwo = player;
        assert(player.hashCode() == playerTwo.hashCode());
    }
}
