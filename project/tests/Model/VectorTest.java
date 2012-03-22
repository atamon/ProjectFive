/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import junit.framework.Assert;
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
public class VectorTest {

    /**
     * Test of getX method, of class Vector.
     */
    @Test
    public void testGetX() {
        // Test if constructed values are kept with get repeatedly
        float x = 1.23f;
        float y = 2.34f;
        Vector vector = new Vector(x, y);

        float getX = vector.getX();

        // Test that values are intact after one get
        assertTrue(x == getX && getX == vector.getX());
    }

    /**
     * Test of getY method, of class Vector.
     */
    @Test
    public void testGetY() {
        // Make vector
        float x = 1.23f;
        float y = 2.34f;
        Vector vector = new Vector(x, y);

        // Make sure values are intact
        float getY = vector.getY();
        assertTrue(y == getY && getY == vector.getY());
    }
    
    /**
     * 
     */
    @Test
    public void testSetX() {
        // Make sure that the set value sticks
        float x = 4f;
        Vector vector = new Vector(1f, 0);
        vector.setX(x);
        assertTrue(x == vector.getX());
    }
    
    /**
     * 
     */
    @Test
    public void testSetY() {
        // Make sure that the set value sticks
        float y = 4f;
        Vector vector = new Vector(0, y);
        vector.setY(y);
        assertTrue(y == vector.getY());        
    }

    /**
     * Test of toString method, of class Vector.
     */
    @Test
    public void testToString() {
        boolean isString = (new Vector(1f, 2f).toString() instanceof String);
        assertTrue(isString);
    }

    /**
     * Test of equals method, of class Vector.
     */
    @Test
    public void testEquals() {
        // These two should be equals
        Vector vector1 = new Vector(1f, 2f);
        Vector vector2 = new Vector(1f, 2f);
        Vector vector3 = new Vector(1f, 3f);
        Vector vector4 = new Vector(2f, 3f);
        // These two should be true
        assertTrue(vector1.equals(vector2) && vector2.equals(vector1));

        // These two should return false
        assertFalse(vector3.equals(vector4) && vector4.equals(vector2));
    }

    /**
     * Test of hashCode method, of class Vector.
     */
    @Test
    public void testHashCode() {
        // If .equals we have hash1 == hash2
        Vector vector1 = new Vector(1f, 2f);
        Vector vector2 = new Vector(1f, 2f);

        if (vector1.equals(vector2)) {
            assertTrue(vector1.hashCode() == vector2.hashCode());
        }
    }
}
