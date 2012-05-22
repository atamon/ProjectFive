/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.round;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.player.Player;

/**
 *  Class to handle rounds in pirate game.
 **/
public class SimpleRoundModel implements IRoundModel {
    private Round currentRound;
    private final Map<Integer, Round> playedRounds = new HashMap<Integer, Round>();
    private float roundCountdown;
    
    public RoundState getRoundState() {
        if (currentRound == null) {
            return RoundState.NONE_EXISTANT;
        }
        return currentRound.getState();
    }
    
    public void newRound() {
        currentRound = new Round();
    }
    
    public void startRound() {
        try {
            currentRound.start();
        } catch (RoundAlreadyStartedException e) {
            System.out.println("Warning: Round already started!");
        }
    }

    public void pause() {
        currentRound.pause();
    }

    public void unPause() {
        currentRound.unPause();
    }

    public void endRound(Player winner) {
        try {
            currentRound.end(winner);
        } catch (RoundAlreadyEndedException e) {
            System.out.println("WARNING: " + e.getMessage());
        }
        
        playedRounds.put(playedRounds(), currentRound);
    }

    public Player getWinner() {
        return currentRound.getWinner();
    }
    
    public int playedRounds() {
        return playedRounds.size();
    }

    public void clearPlayedRounds() {
        playedRounds.clear();
    }
    
    public Collection<Round> getPlayedRounds() {
        return playedRounds.values();
    }
    
    public void setCountDown(float cd) {
        roundCountdown = cd;
    }
    
    public float getCountDown() {
        return roundCountdown;
    }
}
