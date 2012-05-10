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
public class PUSpeedTest {
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }
    
    @Test
    public void testGetName() {
        IPowerUp puSpeed = new PUSpeed();
        assertTrue("Speed".equals(puSpeed.getName()));
    }
    
    /**
     * Test so that we know that we get a message.
     * Might be changed so we just need to see that it's a string and not null.
     */
    @Test
    public void testGetMessage() {
        IPowerUp puSpeed = new PUSpeed();
        assertTrue(puSpeed.getMessage() != null && puSpeed.getMessage() instanceof String);
    }
    
    @Test
    public void testGetLifeTime() {
        IPowerUp puSpeed = new PUSpeed();
        assertTrue(10 == puSpeed.getLifeTime());
    }
    
    @Test
    public void testUpdate() {
        IPowerUp puSpeed = new PUSpeed();
        puSpeed.update(0.5f);
        assertTrue(10 != puSpeed.getLifeTime());
    }
    
    @Test
    public void testGetAcceleration() {
        IPowerUp puSpeed = new PUSpeed();
        assertEquals(puSpeed.getAcceleration(), 10000);
    }
    
    @Test
    public void testGetHitPoints() {
        IPowerUp puSpeed = new PUSpeed();
        assertEquals(puSpeed.getHitPoints(), 0);
    }
    
    @Test
    public void testGetHitPointsMax() {
        IPowerUp puSpeed = new PUSpeed();
        assertEquals(puSpeed.getHitPointsMax(), 0);
    }
    
    @Test
    public void testGetMaxSpeed() {
        IPowerUp puSpeed = new PUSpeed();
        assertTrue(20 == puSpeed.getMaxSpeed());
    }
    
    @Test
    public void testGetHitRetardation() {
        IPowerUp puSpeed = new PUSpeed();
        assertEquals(puSpeed.getRetardation(), 0);
    }
    
    @Test
    public void testGetSteerAngle() {
        IPowerUp puSpeed = new PUSpeed();
        assertEquals(puSpeed.getSteerAngle(), 0);
    }
    
    @Test
    public void testGetActive() {
        IPowerUp puSpeed = new PUSpeed();
        assertTrue(puSpeed.isActive());
    }
    
    @Test
    public void testSetActive() {
        IPowerUp puSpeed = new PUSpeed();
        assertTrue(puSpeed.isActive());
        
        puSpeed.setActive(false);
        assertFalse(puSpeed.isActive());
    }
}
