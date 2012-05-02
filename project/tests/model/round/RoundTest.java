/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.round;

import model.player.Player;
import org.junit.*;
import static org.junit.Assert.*;
import model.round.RoundAlreadyStartedException;

/**
 * Tests Round.
 * @author victorlindhe
 */
public class RoundTest {
    private Round round;
    
    public RoundTest() {
        this.round = new Round();
    }
    
    /**
     * Tests to start the Round.
     * Tests to start it again, and there we will get an exception.
     */
    @Test (expected=RoundAlreadyStartedException.class)
    public void testStart() throws RoundAlreadyStartedException {
        this.round.start();
        this.round.start();
    }
    
    /**
     * Tests to pause the Round.
     * And then to unpause the Round.
     */
    @Test
    public void testPausing() {
        this.round.pause();
        assertTrue(this.round.getState() == RoundState.PAUSED);
        this.round.unPause();
        assertTrue(this.round.getState() == RoundState.PLAYING);
    }
    
    /**
     * Tests to end the Round with a winning Player.
     * Tests to end it again with another Player as winner, we will get
     * an exception.
     */
    @Test (expected=RoundAlreadyEndedException.class)
    public void testEndAndGetWinner() throws RoundAlreadyEndedException {
        this.round.end(new Player(1));
        assertTrue(this.round.getWinner().getId() == 1);
        this.round.end(new Player(2));
    }
   
}
