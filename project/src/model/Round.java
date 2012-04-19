package model;
 /**
 * Class to keep track of the current round.
 * @author johnhu
 */
public class Round {
    
    private boolean roundActive = false;
    
    public Round() {
        
    }
    
    public void start() {
        this.roundActive = true;
    }
    
    public void end() {
        this.roundActive = false;
    }
    
    public boolean getActiveRound() {
        return this.roundActive;
    }
}
