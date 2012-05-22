/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.settings.SettingsLoader;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import model.player.Player;
import model.round.IRoundModel;
import model.round.RoundState;
import model.round.SimpleRoundModel;
import model.settings.Settings;
import model.visual.Battlefield;
import model.visual.Item;
import model.visual.Unit;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author victorlindhe
 */
public class GameTest {
    private Game game;
    private Battlefield bField = new Battlefield();
    private PropertyChangeListener listener = new PropertyChangeListener() {

        public void propertyChange(PropertyChangeEvent pce) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    };
    
    @Before
    public void setUp() {
                Settings.getInstance().loadSettings(SettingsLoader.
                                               readSettings("assets/settings"));
        game = new Game(bField);
    }
    
    @Test
    public void preGame() {
        assertTrue(game.getState() == GameState.INACTIVE);
        assertTrue(game.getRoundState() == RoundState.NONE_EXISTANT);
    }
    
    @Test
    public void testCreatePlayers() {
        game.createPlayer(0);
        game.createPlayer(1);
        assertTrue(game.hasPlayer(0));
        assertTrue(game.hasPlayer(1));
        assertTrue(game.getPlayer(0).getClass().equals(Player.class));
    }
    
    @Test
    public void bfMethods() {
        assertTrue(game.getBattlefieldCenter().equals(bField.getCenter()));
        assertTrue(game.getBattlefieldPosition().equals(bField.getPosition()));
        assertTrue(game.getBattlefieldSize().equals(bField.getSize()));
    }
    
    @Test (expected=NullPointerException.class)
    public void addUnitListeners() {
        testCreatePlayers();
        game.addUnitListener(0, listener);
        game.addUnitListener(3, listener); // Throws exception
    }
    
    @Test
    public void removeUnitListeners() {
        try {
            addUnitListeners();
        } catch(Exception e) {
            
        }
        game.removeUnitListener(0, listener);
    }
    
    @Test
    public void testStartupGame() {
        testCreatePlayers();
        assertTrue(game.hasValidAmountOfPlayers());
        game.start();
        assertTrue(game.getState() == GameState.ACTIVE);
    }
    
    
    boolean itemCreated;
    
    @Test
    public void runGame() {
        testStartupGame();
        itemCreated = false;
        game.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                if(pce.getPropertyName().equals("Bottle Created")) {
                    itemCreated = true;
                }
            }
            
        });
        game.update(15.0f);
        assertTrue(itemCreated);
        game.switchPauseState();
        assertTrue(game.getRoundState() == RoundState.PAUSED);
        game.switchPauseState();
        assertTrue(game.getRoundState() == RoundState.PLAYING);
    }
    
    @Test
    public void removingPlayer() {
        testStartupGame();
        game.removePlayer(1);
        assertFalse(game.hasValidAmountOfPlayers());
        
    }
    
    @Test
    public void endingRound() {
        runGame();
        game.getPlayer(0).getUnit().setHitPoints(0);
        game.update(15.0f);
        assertTrue(game.getRoundState() == RoundState.POST);
    }
    
    @Test
    public void endingGame() {
        testStartupGame();
        for(int i=0; i<5; i++) {
            game.getPlayer(1).getUnit().setHitPoints(-1);
            game.update(15.0f);
            game.nextRound();
        }
        assertTrue(game.getState() == GameState.STATS);
    }
    
    @Test
    public void testClean() {
        testStartupGame();
        game.clean();
        assertTrue(game.getState() == GameState.INACTIVE);
    }
}
