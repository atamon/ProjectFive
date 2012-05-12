/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.round;

import java.util.Collection;
import model.player.Player;

/**
 *
 * @author victorlindhe
 */
public interface RoundModel {
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
}
