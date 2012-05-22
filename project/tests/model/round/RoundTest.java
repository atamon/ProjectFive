package model.round;

import model.player.Player;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests Round.
 */
public class RoundTest {
    private Round round;
    
    public RoundTest() {
        round = new Round();
    }
    
    /**
     * Tests to start the Round.
     * Tests to start it again, and there we will get an exception.
     */
    @Test (expected=RoundAlreadyStartedException.class)
    public void testStart() throws RoundAlreadyStartedException {
        round.start();
        round.start();
    }
    
    /**
     * Tests to pause the Round.
     * And then to unpause the Round.
     */
    @Test
    public void testPausing() {
        round.pause();
        assertTrue(round.getState() == RoundState.PAUSED);
        round.unPause();
        assertTrue(round.getState() == RoundState.PLAYING);
    }
    
    /**
     * Tests to end the Round with a winning Player.
     * Tests to end it again with another Player as winner, we will get
     * an exception.
     */
    @Test (expected=RoundAlreadyEndedException.class)
    public void testEndAndGetWinner() throws RoundAlreadyEndedException {
        round.end(new Player(1));
        assertTrue(round.getWinner().getId() == 1);
        round.end(new Player(2));
    }
   
}
