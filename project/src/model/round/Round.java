package model.round;

import model.player.Player;

 /**
 * Class to keep track of the current round.
 * @author johnhu
 * @modified victorlindhe
 */
public class Round {
    private RoundState roundState;
    private Player winner;
    
    /**
     * Starts this round.
     * @throws RoundAlreadyStartedException Thrown if already started.
     */
    public void start() throws RoundAlreadyStartedException {
        if (roundState == RoundState.PLAYING) {
            throw new RoundAlreadyStartedException();
        }
        roundState = RoundState.PLAYING;
    }
    
    /**
     * Ends the Round with a winner.
     * @param winner The winner of the Round.
     * @throws RoundAlreadyEndedException Thrown if already ended.
     */
    public void end(Player winner) throws RoundAlreadyEndedException {
        if (roundState == RoundState.POST) {
            throw new RoundAlreadyEndedException();
        }
        this.winner = winner;
        roundState = RoundState.POST;
    }
    
    /**
     * Pauses the Round.
     */
    public void pause() {
        this.roundState = RoundState.PAUSED;
    }
    
    
    /**
     * Unpauses the Round.
     */
    public void unPause() {
        this.roundState = RoundState.PLAYING;
    }
    
    
    /**
     * Returns the Player that won the Round.
     * @return Player
     */
    public Player getWinner() {
        return winner;
    }
    
    /**
     * Returns the current RoundState
     * @return RoundState
     */
    public RoundState getState() {
        return roundState;
    }
}
