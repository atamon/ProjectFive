/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.tools.PowerUp;
import math.Vector;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author jnes
 */
public class ItemTest {
    Vector testVector = new Vector(1,1,2);
    PowerUp testPU = new PowerUp();
    Item testInstance = new Item(testPU, testVector);
    /**
     * Test of getPosition method, of class Item.
     */
    @Test
    public void testGetPosition() {
        Vector expResult = testVector;
        Vector result = testInstance.getPosition();
        assertTrue(expResult.equals(result) && expResult != result);
    }

    /**
     * Test of getPowerUp method, of class Item.
     */
    @Test
    public void testGetPowerUp() {
        PowerUp expResult = testPU;
        PowerUp result = testInstance.getPowerUp();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getSize method, of class Item.
     */
    @Test
    public void testGetSize() {
        Vector expResult = new Vector(0.5f,0.5f);
        Vector result = testInstance.getSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of remove method, of class Item.
     */
    
    public void testRemove() {
        // cannot test remove
        this.testInstance.announceRemoval();
    }
}
