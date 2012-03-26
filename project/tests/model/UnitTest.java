/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
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
public class UnitTest {
    
    public UnitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of updatePosition method, of class Unit.
     */
    @Test
    public void testUpdatePosition() {
        Unit unit = new Unit(new Vector(1, 1), new Vector(1, 1));
        
        System.out.println("updatePosition");
        double tpf = 0.0;
        Unit instance = null;
        instance.updatePosition(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accelerate method, of class Unit.
     */
    @Test
    public void testAccelerate() {
        System.out.println("accelerate");
        double tpf = 0.0;
        Unit instance = null;
        instance.accelerate(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of retardate method, of class Unit.
     */
    @Test
    public void testRetardate() {
        System.out.println("retardate");
        double tpf = 0.0;
        Unit instance = null;
        instance.retardate(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of steerClockwise method, of class Unit.
     */
    @Test
    public void testSteerClockwise() {
        Unit unit = new Unit(new Vector(0, 0), new Vector(1f, 0));
        Vector dir = unit.getDirection();
        unit.setSpeed(100);
        unit.setSteerAngle((float)Math.PI/2);
        // We want to see that steerClockWise can continously rotate 
        // a unit 2PI radians.
        float steers = 2.0f;
        Vector lastDir;
        for (int i = 0; i<steers; i++) {
            lastDir = unit.getDirection();
            unit.steerClockwise(4f/steers);
            // Make sure we've actually steered the unit
            System.out.println(unit.getDirection());
            assertFalse(unit.getDirection().equals(lastDir));
        }
        assertTrue(dir.equals(unit.getDirection()));
    }

    /**
     * Test of steerAntiClockwise method, of class Unit.
     */
    @Test
    public void testSteerAntiClockwise() {
        System.out.println("steerAntiClockwise");
        double tpf = 0.0;
        Unit instance = null;
        instance.steerAntiClockwise(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSteerAngle method, of class Unit.
     */
    @Test
    public void testSetSteerAngle() {
        System.out.println("setSteerAngle");
        double steerAngle = 0.0;
        Unit instance = null;
        instance.setSteerAngle(steerAngle);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirection method, of class Unit.
     */
    @Test
    public void testSetDirection_Vector() {
        System.out.println("setDirection");
        Vector dir = null;
        Unit instance = null;
        instance.setDirection(dir);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirection method, of class Unit.
     */
    @Test
    public void testSetDirection_double_double() {
        System.out.println("setDirection");
        double x = 0.0;
        double y = 0.0;
        Unit instance = null;
        instance.setDirection(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPosition method, of class Unit.
     */
    @Test
    public void testSetPosition_Vector() {
        System.out.println("setPosition");
        Vector pos = null;
        Unit instance = null;
        instance.setPosition(pos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPosition method, of class Unit.
     */
    @Test
    public void testSetPosition_double_double() {
        System.out.println("setPosition");
        double x = 0.0;
        double y = 0.0;
        Unit instance = null;
        instance.setPosition(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSpeed method, of class Unit.
     */
    @Test
    public void testSetSpeed() {
        System.out.println("setSpeed");
        double speed = 0.0;
        Unit instance = null;
        instance.setSpeed(speed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAcceleration method, of class Unit.
     */
    @Test
    public void testSetAcceleration() {
        System.out.println("setAcceleration");
        int acceleration = 0;
        Unit instance = null;
        instance.setAcceleration(acceleration);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setHitPoints method, of class Unit.
     */
    @Test
    public void testSetHitPoints() {
        System.out.println("setHitPoints");
        int hitPoints = 0;
        Unit instance = null;
        instance.setHitPoints(hitPoints);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRetardation method, of class Unit.
     */
    @Test
    public void testSetRetardation() {
        System.out.println("setRetardation");
        int retardation = 0;
        Unit instance = null;
        instance.setRetardation(retardation);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPosition method, of class Unit.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        Unit instance = null;
        Vector expResult = null;
        Vector result = instance.getPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDirection method, of class Unit.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        Unit instance = null;
        Vector expResult = null;
        Vector result = instance.getDirection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHitPoints method, of class Unit.
     */
    @Test
    public void testGetHitPoints() {
        System.out.println("getHitPoints");
        Unit instance = null;
        int expResult = 0;
        int result = instance.getHitPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHitPointsMax method, of class Unit.
     */
    @Test
    public void testGetHitPointsMax() {
        System.out.println("getHitPointsMax");
        Unit instance = null;
        int expResult = 0;
        int result = instance.getHitPointsMax();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAcceleration method, of class Unit.
     */
    @Test
    public void testGetAcceleration() {
        System.out.println("getAcceleration");
        Unit instance = null;
        int expResult = 0;
        int result = instance.getAcceleration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRetardation method, of class Unit.
     */
    @Test
    public void testGetRetardation() {
        System.out.println("getRetardation");
        Unit instance = null;
        int expResult = 0;
        int result = instance.getRetardation();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpeed method, of class Unit.
     */
    @Test
    public void testGetSpeed() {
        System.out.println("getSpeed");
        Unit instance = null;
        double expResult = 0.0;
        double result = instance.getSpeed();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addPropertyChangeListener method, of class Unit.
     */
    @Test
    public void testAddPropertyChangeListener() {
        System.out.println("addPropertyChangeListener");
        PropertyChangeListener ls = null;
        Unit instance = null;
        instance.addPropertyChangeListener(ls);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removePropertyChangeListener method, of class Unit.
     */
    @Test
    public void testRemovePropertyChangeListener() {
        System.out.println("removePropertyChangeListener");
        PropertyChangeListener ls = null;
        Unit instance = null;
        instance.removePropertyChangeListener(ls);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Unit.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Unit instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Unit.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        Unit instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class Unit.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Unit instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
