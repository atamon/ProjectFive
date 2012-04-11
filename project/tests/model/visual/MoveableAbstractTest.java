/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.tools.Vector;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anton Lindgren
 */
public class MoveableAbstractTest {
    
    public MoveableAbstractTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of move method, of class MoveableAbstract.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        float tpf = 0.0F;
        MoveableAbstract instance = null;
        instance.move(tpf);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of directionUpdated method, of class MoveableAbstract.
     */
    @Test
    public void testDirectionUpdated() {
        System.out.println("directionUpdated");
        MoveableAbstract instance = null;
        instance.directionUpdated();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of positionUpdated method, of class MoveableAbstract.
     */
    @Test
    public void testPositionUpdated() {
        System.out.println("positionUpdated");
        MoveableAbstract instance = null;
        instance.positionUpdated();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPosition method, of class MoveableAbstract.
     */
    @Test
    public void testSetPosition_Vector() {
        System.out.println("setPosition");
        Vector pos = null;
        MoveableAbstract instance = null;
        instance.setPosition(pos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPosition method, of class MoveableAbstract.
     */
    @Test
    public void testSetPosition_float_float() {
        System.out.println("setPosition");
        float x = 0.0F;
        float y = 0.0F;
        MoveableAbstract instance = null;
        instance.setPosition(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSpeed method, of class MoveableAbstract.
     */
    @Test
    public void testSetSpeed() {
        System.out.println("setSpeed");
        float speed = 0.0F;
        MoveableAbstract instance = null;
        instance.setSpeed(speed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirection method, of class MoveableAbstract.
     */
    @Test
    public void testSetDirection_Vector() {
        System.out.println("setDirection");
        Vector dir = null;
        MoveableAbstract instance = null;
        instance.setDirection(dir);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDirection method, of class MoveableAbstract.
     */
    @Test
    public void testSetDirection_float_float() {
        System.out.println("setDirection");
        float x = 0.0F;
        float y = 0.0F;
        MoveableAbstract instance = null;
        instance.setDirection(x, y);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPosition method, of class MoveableAbstract.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        MoveableAbstract instance = null;
        Vector expResult = null;
        Vector result = instance.getPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDirection method, of class MoveableAbstract.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        MoveableAbstract instance = null;
        Vector expResult = null;
        Vector result = instance.getDirection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxSpeed method, of class MoveableAbstract.
     */
    @Test
    public void testGetMaxSpeed() {
        System.out.println("getMaxSpeed");
        MoveableAbstract instance = null;
        float expResult = 0.0F;
        float result = instance.getMaxSpeed();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSpeed method, of class MoveableAbstract.
     */
    @Test
    public void testGetSpeed() {
        System.out.println("getSpeed");
        MoveableAbstract instance = null;
        float expResult = 0.0F;
        float result = instance.getSpeed();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class MoveableAbstract.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        MoveableAbstract instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class MoveableAbstract.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Object obj = null;
        MoveableAbstract instance = null;
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hashCode method, of class MoveableAbstract.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        MoveableAbstract instance = null;
        int expResult = 0;
        int result = instance.hashCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class MoveableAbstractImpl extends MoveableAbstract {

        public MoveableAbstractImpl() {
            super(null, null);
        }

        public void directionUpdated() {
        }

        public void positionUpdated() {
        }
    }
}
