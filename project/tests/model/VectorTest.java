package model;

import model.Vector;
import java.lang.ArithmeticException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Vector.java
 * @author Anton Lindgren
 */
public class VectorTest {

    /**
     * Test of getX method, of class Vector.
     */
    @Test
    public void testGetX() {
        // Test if constructed values are kept with get repeatedly
        double x = 1.23f;
        double y = 2.34f;
        Vector vector = new Vector(x, y);

        double getX = vector.getX();

        // Test that values are intact after one get
        assertTrue(x == getX && getX == vector.getX());
    }

    /**
     * Test of getY method, of class Vector.
     */
    @Test
    public void testGetY() {
        // Make vector
        double x = 1.23f;
        double y = 2.34f;
        Vector vector = new Vector(x, y);

        // Make sure values are intact
        double getY = vector.getY();
        assertTrue(y == getY && getY == vector.getY());
    }

    /**
     * 
     */
    @Test
    public void testSetX() {
        // Make sure that the set value sticks
        double x = 4f;
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
        double y = 4f;
        Vector vector = new Vector(0, y);
        vector.setY(y);
        assertTrue(y == vector.getY());
    }

    /**
     * Tests if all vectors returned by getLength() have length 1 after normalized.
     * Except the 0 vector which returns an arithmetic exception
     */
    @Test(expected = ArithmeticException.class)
    public void testNormalize() {
        // 0 vector should cast ArithmeticException
        Vector zeroVector = new Vector(0, 0);
        zeroVector.normalize();

        // Test a known vector
        Vector vector1 = new Vector(1f, 3f);
        vector1.normalize();
        assertTrue(1 == vector1.getLength());

        // Test a few random vectors
        for (int i = 0; i < 10; i++) {
            Vector randVector = new Vector(Math.random()*10, Math.random()*10);
            randVector.normalize();
            assertTrue(1 == randVector.getLength());
        }
    }

    /**
     * Makes sure getLength() follows pythagoras
     */
    @Test
    public void testGetLength() {
        // A vector which should have the length Math.sqrt(2)
        Vector vector1 = new Vector(1f, 1f);
        assertTrue(vector1.getLength() == Math.sqrt(2));
        // A vector with length Math.sqrt(8)
        Vector vector2 = new Vector(2f, 2f);
        assertTrue(vector2.getLength() == Math.sqrt(8));
        
    }
    
    @Test
    public void testMult() {
        //TODO
    }
    
    @Test
    public void testAdd() {
        //TODO
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
    
    @Test
    public void testRotate(){
        Vector v = new Vector(1f, 2f);
        
        for (int i = 0; i < 2000; i++){
            v.rotate(2*Math.PI/2000);
        }
        // doubles arn't 100% accurate. We are ok with a diff less than 0.03
        assertTrue(1f-v.getX() < 0.03 && 2f-v.getY() < 0.03);
    }
}