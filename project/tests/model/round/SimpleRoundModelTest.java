/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.round;

import model.player.Player;
import model.settings.Settings;
import model.settings.SettingsLoader;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author victorlindhe
 */
public class SimpleRoundModelTest {
    private IRoundModel rModel;

    @Before
    public void setUp() throws Exception {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
        rModel = new SimpleRoundModel();
    }
    
    @Test
    public void testGetRoundState() {
        assertTrue(rModel.getRoundState() == RoundState.NONE_EXISTANT);
    }
    
    @Test
    public void testStartingRound() {
        rModel.newRound();
        assertTrue(rModel.getRoundState() == RoundState.PAUSED);
        rModel.startRound();
        assertTrue(rModel.getRoundState() == RoundState.PLAYING);
    }
    
    @Test
    public void testPausing() {
        testStartingRound();
        rModel.pause();
        assertTrue(rModel.getRoundState() == RoundState.PAUSED);
        rModel.unPause();
        assertTrue(rModel.getRoundState() == RoundState.PLAYING);
    }
    
    @Test
    public void testQuitting() {
        testPausing();
        Player player = new Player(0);
        rModel.endRound(player);
        assertTrue(rModel.getRoundState() == RoundState.POST);
        assertTrue(rModel.getWinner().equals(player));
    }
    
    @Test
    public void testCount() {
        rModel.setCountDown(5.0f);
        assertTrue(rModel.getCountDown() == 5.0f);
    }
    
    @Test
    public void testPlayedRounds() {
        testStartingRound();
        Player player = new Player(0);
        rModel.endRound(player);
        rModel.newRound();
        rModel.startRound();
        rModel.endRound(player);
        assertTrue(rModel.getPlayedRounds().size() == 2);
        rModel.clearPlayedRounds();
        assertTrue(rModel.getPlayedRounds().size() == 0);
    }
}
