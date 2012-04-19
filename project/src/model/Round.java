package model;
 /**
 * Class to keep track of the current round.
 * @author johnhu
 */
public class Round {
    
    private boolean roundActive = false;
    private Player winner;
    
    public Round() {
        
    }
    
    public void start() {
        this.roundActive = true;
    }
    
    public void end(Player winner) {
        this.winner = winner;
        this.roundActive = false;
    }
    
    public Player getWinner() {
        return winner;
    }
    
    public boolean getActiveRound() {
        return this.roundActive;
    }
}
