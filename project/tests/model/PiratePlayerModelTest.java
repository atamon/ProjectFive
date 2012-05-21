/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import model.player.Player;
import model.round.WinnerNotFoundException;
import model.settings.Settings;
import model.settings.SettingsLoader;
import model.visual.Battlefield;
import model.visual.Unit;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author victorlindhe
 */
public class PiratePlayerModelTest {
    private PlayerModel pModel;
    private Battlefield bField = new Battlefield();
    PropertyChangeListener listener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        };
    
    @Before
    public void setUp() throws Exception {
        Settings.getInstance().loadSettings(SettingsLoader.
                                               readSettings("assets/settings"));
        pModel = new PiratePlayerModel(bField);
    }
    
    @Test ()
    public void testCreatePlayer() {
        pModel.createPlayer(0);
        assertTrue(pModel.hasPlayer(0));
        assertTrue(pModel.getPlayer(0).getClass().equals(Player.class));
    }
    
    @Test (expected=RuntimeException.class)
    public void testRemovePlayer() {
        testCreatePlayer();
        pModel.removePlayer(0);
        assertFalse(pModel.hasPlayer(0));
        pModel.removePlayer(0); // Throws exception
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testGetPlayer() {
        pModel.getPlayer(0); // Doesn't exist
    }
    
    @Test
    public void testReset() {
        pModel.createPlayer(0);
        pModel.createPlayer(1);
        pModel.getPlayer(0).getUnit().setPosition(Vector.ZERO_VECTOR);
        pModel.resetUnits();
        assertFalse(pModel.getPlayer(0).getUnit().getPosition().
                                                    equals(Vector.ZERO_VECTOR));
    }
    
    @Test
    public void testGetPlayerMap() {
        testReset();
        assertTrue(pModel.getPlayerMap().size() == 2);
    }
    
    @Test (expected=UnsupportedOperationException.class)
    public void testAddListeners() {
        pModel.addPropertyChangeListener(listener);
        pModel.createPlayer(0);
    }
    
    @Test
    public void testRemoveListener() {
        try {
            testAddListeners();
        } catch(Exception e) {
            
        }
        pModel.removePropertyChangeListener(listener);
        pModel.createPlayer(1);
    }
    
    @Test
    public void testGameOver() {
        pModel.createPlayer(0);
        pModel.createPlayer(1);
        Player winner = pModel.getPlayer(1);
        assertFalse(pModel.gameOver());
        pModel.getPlayer(0).getUnit().setHitPoints(0); // Should kill it
        assertTrue(pModel.gameOver());
        assertTrue(pModel.findRoundWinner().equals(winner));
    }
    
    @Test (expected=WinnerNotFoundException.class)
    public void testFindRoundWinner() {
        pModel.createPlayer(0);
        pModel.createPlayer(1);
        pModel.findRoundWinner(); // Should throw exception
    }
}
