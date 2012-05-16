/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

import model.settings.SettingsLoader;
import model.settings.Settings;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author johnhu
 */
public class PUDrunkTest {
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }
    
    @Test
    public void testGetName() {
        IPowerUp puDrunk = new PUDrunk();
        assertTrue("Drunk".equals(puDrunk.getName()));
    }
    
    /**
     * Test so that we know that we get a message.
     * Might be changed so we just need to see that it's a string and not null.
     */
    @Test
    public void testGetMessage() {
        IPowerUp puDrunk = new PUDrunk();
        assertTrue(puDrunk.getMessage() != null && puDrunk.getMessage() instanceof String);
    }
    
    @Test
    public void testGetLifeTime() {
        IPowerUp puDrunk = new PUDrunk();
        assertTrue(10 == puDrunk.getLifeTime());
    }
    
    @Test
    public void testUpdate() {
        IPowerUp puDrunk = new PUDrunk();
        puDrunk.update(0.5f);
        assertTrue(10 != puDrunk.getLifeTime());
    }
    
    @Test
    public void testGetAcceleration() {
        IPowerUp puDrunk = new PUDrunk();
        assertEquals(puDrunk.getAcceleration(), 0);
    }
    
    @Test
    public void testGetHitPoints() {
        IPowerUp puDrunk = new PUDrunk();
        assertEquals(puDrunk.getHitPoints(), 0);
    }
    
    @Test
    public void testGetHitPointsMax() {
        IPowerUp puDrunk = new PUDrunk();
        assertEquals(puDrunk.getHitPointsMax(), 0);
    }
    
    @Test
    public void testGetMaxSpeed() {
        IPowerUp puDrunk = new PUDrunk();
        assertTrue(0 == puDrunk.getMaxSpeed());
    }
    
    @Test
    public void testGetHitRetardation() {
        IPowerUp puDrunk = new PUDrunk();
        assertEquals(puDrunk.getRetardation(), 0);
    }
    
    @Test
    public void testGetSteerAngle() {
        IPowerUp puDrunk = new PUDrunk();
        assertEquals(puDrunk.getSteerAngle(), -Settings.getInstance().getSetting("steerAngle")*2);
    }
    
    @Test
    public void testGetActive() {
        IPowerUp puDrunk = new PUDrunk();
        assertTrue(puDrunk.isActive());
    }
    
    @Test
    public void testSetActive() {
        IPowerUp puDrunk = new PUDrunk();
        assertTrue(puDrunk.isActive());
        
        puDrunk.setActive(false);
        assertFalse(puDrunk.isActive());
    }
}
