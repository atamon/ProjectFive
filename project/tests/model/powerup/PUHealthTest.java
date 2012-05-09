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
public class PUHealthTest {
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }
    
    @Test
    public void testGetName() {
        IPowerUp puHealth = new PUHealth();
        assertTrue("Health".equals(puHealth.getName()));
    }
    
    /**
     * Test so that we know that we get a message.
     * Might be changed so we just need to see that it's a string and not null.
     */
    @Test
    public void testGetMessage() {
        IPowerUp puHealth = new PUHealth();
        assertTrue(puHealth.getMessage() != null && puHealth.getMessage() instanceof String);
    }
    
    @Test
    public void testGetLifeTime() {
        IPowerUp puHealth = new PUHealth();
        assertTrue(0 == puHealth.getLifeTime()); //instant powerup so no lifetime
    }
    
    @Test
    public void testUpdate() {
        assertTrue(true); //instant powerup, no update
    }
    
    @Test
    public void testGetAcceleration() {
        IPowerUp puHealth = new PUHealth();
        assertEquals(puHealth.getAcceleration(), 0);
    }
    
    @Test
    public void testGetHitPoints() {
        IPowerUp puHealth = new PUHealth();
        assertEquals(puHealth.getHitPoints(), 100);
    }
    
    @Test
    public void testGetHitPointsMax() {
        IPowerUp puHealth = new PUHealth();
        assertEquals(puHealth.getHitPointsMax(), 0);
    }
    
    @Test
    public void testGetMaxSpeed() {
        IPowerUp puHealth = new PUHealth();
        assertTrue(0 == puHealth.getMaxSpeed());
    }
    
    @Test
    public void testGetHitRetardation() {
        IPowerUp puHealth = new PUHealth();
        assertEquals(puHealth.getRetardation(), 0);
    }
    
    @Test
    public void testGetSteerAngle() {
        IPowerUp puHealth = new PUHealth();
        assertEquals(puHealth.getSteerAngle(), 0);
    }
    
    @Test
    public void testGetActive() {
        IPowerUp puHealth = new PUHealth();
        assertTrue(puHealth.isActive());
    }
    
    @Test
    public void testSetActive() {
        IPowerUp puHealth = new PUHealth();
        assertTrue(puHealth.isActive());
        
        puHealth.setActive(false);
        assertFalse(puHealth.isActive());
    }
}
