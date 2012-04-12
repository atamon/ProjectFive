/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.tools.Vector;
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
    }
    
    /**
     * Tests to create a Battlefield with negative size.
     */
    @Test (expected=NumberFormatException.class)
    public void testConstructorArgument() {
        Battlefield secondBField = new Battlefield(-50);
    }

    /**
     * Test of getSize method, of class Battlefield.
     * Tests if we get the sizes we should.
     */
    @Test
    public void testGetSize() {
        System.out.println("getSize");
        assertTrue(bField.getSize() == 100.0f);
    }

    /**
     * Test of getPosition method, of class Battlefield.
     * Tests if the position is origo (nullvector).
     * 
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        assertTrue(bField.getPosition().equals(new Vector(0,0)));
    }

    /**
     * Test of getCenter method, of class Battlefield.
     */
    @Test
    public void testGetCenter() {
        System.out.println("getCenter");
        Vector expFirstSize = new Vector(50,50);
        assertTrue(expFirstSize.equals(this.bField.getCenter()));
    }

    /**
     * Test of equals method, of class Battlefield.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Battlefield secondBField = new Battlefield(50.0f);
        assertTrue(this.bField.equals(this.bField));
        assertFalse(this.bField.equals(secondBField));
    }

    /**
     * Test of hashCode method, of class Battlefield.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Battlefield secondBField = new Battlefield(50.0f);
        assertFalse(this.bField.hashCode() == secondBField.hashCode());
    }
}
