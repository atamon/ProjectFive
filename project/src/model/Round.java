package model;
 /**
 * Class to keep track of the current round.
 * @author johnhu
 */
public class Round {
    
    private RoundState roundState;
    private Player winner;
    
    public Round() {
        
    }
    
    public void start() {
        this.roundState = RoundState.PLAYING;
    }
    
    public void end(Player winner) {
        this.winner = winner;
        this.roundState = RoundState.POST;
    }
    
    public void pause() {
        this.roundState = RoundState.PAUSED;
    }
    
    public void unPause() {
        this.roundState = RoundState.PLAYING;
    }
    
    public Player getWinner() {
        return winner;
    }
    
    public RoundState getState() {
        return this.roundState;
    }
}
