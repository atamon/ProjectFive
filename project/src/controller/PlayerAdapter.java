/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Direction;
import model.Player;

/**
 *
 * @author victorlindhe
 */
public class PlayerAdapter {
    private Player player;
    private KeyBoardListener kbListener;
    
    public PlayerAdapter(Player player) {
        this.player = player;
    }
    
    public PlayerAdapter(Player player, KeyBoardListener kbListener) {
        this.player = player;
        this.kbListener = kbListener;
    }
    
    public void setKeyBoardListener(KeyBoardListener kbListener) {
        this.kbListener = kbListener;
    }
    
    public void accelerateUnit(boolean accel) {
        this.player.accelerateUnit(accel);
    }
    
    public void steerUnit(Direction dir, float tpf) {
        this.player.steerUnit(dir, tpf);
    }
    
    public int getID() {
        return this.player.getId();
    }
}
