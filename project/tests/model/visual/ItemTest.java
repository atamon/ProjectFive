/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import controller.SettingsLoader;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import model.powerup.IPowerUp;
import model.powerup.PUHealth;
import model.tools.Settings;
import org.junit.*;
import static org.junit.Assert.*;
import test.Utils;

/**
 *
 * @author jnes
 */
public class ItemTest {
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
        testPU = new PUHealth();
        testInstance = new Item(testPU, testVector);
        
    }

    Vector testVector = new Vector(1,1,2);
    IPowerUp testPU ;
    Item testInstance;
    
    
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
        IPowerUp expResult = testPU;
        IPowerUp result = testInstance.getPowerUp();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getSize method, of class Item.
     */
    @Test
    public void testGetSize() {
        Vector expResult = new Vector(2f,2f,2f);
        Vector result = testInstance.getSize();
        assertEquals(expResult, result);
    }

    /**
     * Test of remove method, of class Item. tests addpropertycangelistener analogically
     */
    @Test
    public void testRemove() {
        // cannot test remove
        PropertyChangeListener pcl = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                assertTrue("Item Removed".equals(evt.getPropertyName()) && evt.getNewValue() instanceof Item);
            }
        };
        this.testInstance.addPropertyChangeListener(pcl);
        this.testInstance.announceRemoval();
        this.testInstance.removePropertyChangeListener(pcl);
    }
    @Test
    public void testCollidedWith() {
        PropertyChangeListener pcl = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                assertTrue("Item Removed".equals(evt.getPropertyName()));
            }
        };
        this.testInstance.addPropertyChangeListener(pcl);
        int uSize = Settings.getInstance().getSetting("unitSize");
        
        final Unit testUnit = new Unit(Battlefield.getStartingPosition(0, new Vector(100, 1, 100)),
                Battlefield.getStartingDir(0),
                new Vector(uSize, uSize, uSize),
                Settings.getInstance().getSetting("unitMass"));

        IPowerUp puOld = testUnit.getPowerUp();
        
        
        this.testInstance.collidedWith(testUnit, 1); // unit collides, remove item
        this.testInstance.removePropertyChangeListener(pcl);
        
       assertFalse(puOld.equals(testUnit.getPowerUp()));
       assertTrue(testUnit.getPowerUp().equals(this.testInstance.getPowerUp()));
        // collision with other, i.e cannonball nothing should happen to model.
        
    }
    
    @Test
    public void testUpdate(){
        float old = this.testInstance.getLifeTime();
        this.testInstance.update(Utils.simulateTpf());
        assertFalse(old == this.testInstance.getLifeTime());
        PropertyChangeListener pcl = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("testUpdate: Got Event!");
                assertTrue("Item Removed".equals(evt.getPropertyName()));
            }
        };
        this.testInstance.addPropertyChangeListener(pcl);
        while(this.testInstance.getLifeTime() > 0){
            this.testInstance.update(Utils.simulateTpf());
        }
        
    }
}
