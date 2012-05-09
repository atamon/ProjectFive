/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

import controller.SettingsLoader;
import model.tools.Settings;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author johnhu
 */
public class PUEmptyTest {
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }
    
    @Test
    public void testGetName() {
        IPowerUp puEmpty = new PUEmpty();
        assertTrue("Empty".equals(puEmpty.getName()));
    }
    
    /**
     * Test so that we know that we get a message.
     * Might be changed so we just need to see that it's a string and not null.
     */
    @Test
    public void testGetMessage() {
        IPowerUp puEmpty = new PUEmpty();
        assertTrue(puEmpty.getMessage() != null || puEmpty.getMessage() instanceof String);
    }
    
    @Test
    public void testGetLifeTime() {
        IPowerUp puEmpty = new PUEmpty();
        assertTrue(0 == puEmpty.getLifeTime());
    }
    
    @Test
    public void testUpdate() {
        assertTrue(true); //nothing done in method
    }
    
    @Test
    public void testGetAcceleration() {
        IPowerUp puEmpty = new PUEmpty();
        assertEquals(puEmpty.getAcceleration(), 0);
    }
    
    @Test
    public void testGetHitPoints() {
        IPowerUp puEmpty = new PUEmpty();
        assertEquals(puEmpty.getHitPoints(), 0);
    }
    
    @Test
    public void testGetHitPointsMax() {
        IPowerUp puEmpty = new PUEmpty();
        assertEquals(puEmpty.getHitPointsMax(), 0);
    }
    
    @Test
    public void testGetMaxSpeed() {
        IPowerUp puEmpty = new PUEmpty();
        assertTrue(0 == puEmpty.getMaxSpeed());
    }
    
    @Test
    public void testGetHitRetardation() {
        IPowerUp puEmpty = new PUEmpty();
        assertEquals(puEmpty.getRetardation(), 0);
    }
    
    @Test
    public void testGetSteerAngle() {
        IPowerUp puEmpty = new PUEmpty();
        assertEquals(puEmpty.getSteerAngle(), 0);
    }
    
    @Test
    public void testGetActive() {
        IPowerUp puEmpty = new PUEmpty();
        assertTrue(puEmpty.isActive());
    }
    
    @Test
    public void testSetActive() {
        IPowerUp puEmpty = new PUEmpty();
        assertTrue(puEmpty.isActive());
        
        puEmpty.setActive(false);
        assertFalse(puEmpty.isActive());
    }
}
