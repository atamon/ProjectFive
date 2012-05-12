/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.SettingsLoader;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import model.player.Player;
import model.round.RoundState;
import model.tools.Settings;
import model.visual.Unit;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author victorlindhe
 */
public class GameTest {
    private Game game;
    private Unit unit;
    private PropertyChangeListener listener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent pce) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    };
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
        game = new Game();
    }

    @Test
    public void testStaticVariables() {
        assertTrue(Game.VALID_PLAYER_AMOUNT == 2);
        assertTrue(Game.LAST_MAN_STANDING == 1);
    }
    
    @Test
    public void testGetState() {
        assertTrue(game.getState() == GameState.INACTIVE);
    }
    
    @Test
    public void testGetRoundState() {
        assertTrue(game.getRoundState() == RoundState.NONE_EXISTANT);
        game.start();
        assertFalse(game.getRoundState() == RoundState.NONE_EXISTANT);
    }
    
    @Test
    public void testGetBattlefieldCenter() {
        assertTrue(game.getBattlefieldCenter().getClass().equals(Vector.class));
    }
    
    @Test
    public void testGetBattlefieldPosition() {
        assertTrue(game.getBattlefieldPosition().getClass().equals(Vector.class));
    }
    
    @Test
    public void testCreatePlayer() {
        game.createPlayer(0);
        assertTrue(game.getPlayer(0) != null);
    }
    
    @Test (expected=RuntimeException.class)
    public void testCreatePlayerAgain() {
        game.createPlayer(0);
        game.createPlayer(0); // Should fail
    }
    
    @Test (expected=IllegalArgumentException.class)
    public void testGetPlayer() {
        assertTrue(game.getPlayer(0).getClass().equals(Player.class));
        unit = game.getPlayer(0).getUnit();
        game.getPlayer(1); // Doesn't exist and throws exception
    }
    
    @Test (expected=RuntimeException.class)
    public void testRemovePlayer() {
        game.removePlayer(0); // should work
        game.removePlayer(1); // should not work! throws exception
        try {
            game.getPlayer(0);
            fail(); // if we get that player we have failed the test
        } catch (IllegalArgumentException e) {
            assertTrue(unit.getPosition().equals(Vector.NONE_EXISTANT));
            assertTrue(unit.isDeadAndBuried());
        }
    }
    
    @Test
    public void testUpdate() {
        
    }
    
    @Test
    public void testHasPlayer() {
        assertFalse(game.hasPlayer(0)); // Should return false
    }
    
    @Test
    public void testBattlefieldSize() {
        Vector size = game.getBattlefieldSize();
        assertTrue(size.getClass().equals(Vector.class));
        assertTrue(size.getX() > 0 && size.getY() > 0);
    }
    
    @Test
    public void testHasValidAmountOfPlayers() {
        assertFalse(game.hasValidAmountOfPlayers()); // We have no players 
                                                     // so false
        game.createPlayer(0);
        game.createPlayer(1);
        assertTrue(game.hasValidAmountOfPlayers());
    }
    
    @Test
    public void testSwitchPauseState() {
        game.createPlayer(0);
        game.createPlayer(1);
        game.start();
        assertTrue(game.getState() == GameState.ACTIVE && 
                game.getRoundState() == RoundState.PLAYING);
        game.switchPauseState();
        assertTrue(game.getRoundState() == RoundState.PAUSED);
        game.switchPauseState();
        assertTrue(game.getRoundState() == RoundState.PLAYING);
    }
    
    @Test
    public void testNextRound() {
        game.createPlayer(0);
        game.createPlayer(1);
        game.start();
        game.nextRound();
    }
    
    @Test
    public void testEndRound() {
        game.createPlayer(0);
        game.createPlayer(1);
        game.start();
        game.getPlayer(0).getUnit().setHitPoints(-1);
        
        game.endRound();
    }
    
    @Test
    public void testClean() {
        game.clean();
        assertTrue(game.getState() == GameState.INACTIVE);
    }
    
    private boolean called = false;
    
    @Test
    public void testAddPropertyChangeListener() {
        game.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                called = !called;
            }
            
        });
        game.createPlayer(2);
        assertTrue(called);
    }
    
    @Test
    public void testRemovePropertyChangeListener() {
        game.removePropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
        });
        game.createPlayer(3); // Shouldn't throw exception
    }
    
    @Test (expected=UnsupportedOperationException.class)
    public void testAddUnitListener() {
        game.createPlayer(0);
        game.addUnitListener(0, listener); // Should work
        game.getPlayer(0).getUnit().announceRemoval();
    }
    
    @Test
    public void testRemoveUnitListener() {
        game.createPlayer(0);
        game.removeUnitListener(0, listener); // Should not work
    }
}
