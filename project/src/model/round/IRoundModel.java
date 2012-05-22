package model.round;

import java.util.Collection;
import model.player.Player;

/**
 * Interface for round handling.
 */
public interface IRoundModel {
    public void newRound();
    public void startRound();
    public void pause();
    public void unPause();
    
    public void endRound(Player winner);
    public void clearPlayedRounds();
    public Collection<Round> getPlayedRounds();
    
    public int playedRounds();
    public Player getWinner();
    public RoundState getRoundState();
    
    public float getCountDown();
    public void setCountDown(float cd);
}
