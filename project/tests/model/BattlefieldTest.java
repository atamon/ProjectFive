
package model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Johannes
 */
public class BattlefieldTest {
    
    public BattlefieldTest() {
    }

    /**
     * Test of getSize method, of class Battlefield.
     */
    @Test
    public void testGetSize() {
        Battlefield instance = new Battlefield();
        Vector expResult = new Vector(100.0f, 100.0f);
        Vector result = instance.getSize();
        assertEquals(expResult, result);
        
        // make sure we dont resize this way
        result.add(result);
        result = instance.getSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of getItem method, of class Battlefield.
     */
    @Test
    public void testGetItem() {
        //Todo..?
    }

    /**
     * Test of removeItem method, of class Battlefield.
     */
    @Test
    public void testRemoveItem() {
        System.out.println("removeItem");
        Battlefield instance = new Battlefield();
        instance.removeItem();
        assertTrue(instance.getItem() == null);
    }

    /**
     * Test of addItem method, of class Battlefield.
     */
    @Test
    public void testAddItem() {
        System.out.println("addItem");
        Battlefield instance = new Battlefield();
        Item i = instance.getItem();
        instance.addItem();
        //no new item added if one already exists
        assertTrue(i == instance.getItem());
        instance.removeItem();
        instance.addItem();
        assertTrue(i != instance.getItem());
    }

    /**
     * Test of equals method, of class Battlefield.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Battlefield instance = new Battlefield();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        result = instance.equals(new Battlefield());
        expResult = true;
        assertEquals(result, expResult);
        assertTrue(instance != new Battlefield());
    }

    /**
     * Test of hashCode method, of class Battlefield.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Battlefield instance = new Battlefield();
        Battlefield instance2 = new Battlefield();
        int expResult = instance2.hashCode();
        int result = instance.hashCode();
        assertTrue(expResult != result);
        
        instance.removeItem();
        instance2.removeItem();
        expResult = instance2.hashCode();
        result = instance.hashCode();
        assertEquals(expResult, result);
        
    }
}
