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
public class PUTurnTest {
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }
    
    @Test
    public void testGetName() {
        IPowerUp puTurn = new PUTurn();
        assertTrue("Turn".equals(puTurn.getName()));
    }
    
    /**
     * Test so that we know that we get a message.
     * Might be changed so we just need to see that it's a string and not null.
     */
    @Test
    public void testGetMessage() {
        IPowerUp puTurn = new PUTurn();
        assertTrue(puTurn.getMessage() != null && puTurn.getMessage() instanceof String);
    }
    
    @Test
    public void testGetLifeTime() {
        IPowerUp puTurn = new PUTurn();
        assertTrue(10 == puTurn.getLifeTime());
    }
    
    @Test
    public void testUpdate() {
        IPowerUp puTurn = new PUTurn();
        puTurn.update(0.5f);
        assertTrue(10 != puTurn.getLifeTime());
    }
    
    @Test
    public void testGetAcceleration() {
        IPowerUp puTurn = new PUTurn();
        assertEquals(puTurn.getAcceleration(), 0);
    }
    
    @Test
    public void testGetHitPoints() {
        IPowerUp puTurn = new PUTurn();
        assertEquals(puTurn.getHitPoints(), 0);
    }
    
    @Test
    public void testGetHitPointsMax() {
        IPowerUp puTurn = new PUTurn();
        assertEquals(puTurn.getHitPointsMax(), 0);
    }
    
    @Test
    public void testGetMaxSpeed() {
        IPowerUp puTurn = new PUTurn();
        assertTrue(0 == puTurn.getMaxSpeed());
    }
    
    @Test
    public void testGetHitRetardation() {
        IPowerUp puTurn = new PUTurn();
        assertEquals(puTurn.getRetardation(), 0);
    }
    
    @Test
    public void testGetSteerAngle() {
        IPowerUp puTurn = new PUTurn();
        assertEquals(puTurn.getSteerAngle(), 3);
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
