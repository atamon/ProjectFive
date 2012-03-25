/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Direction;
import model.Game;
import com.jme3.input.controls.ActionListener;

/**
 *
 * @author victorlindhe
 */
public class KeyBoardListener implements ActionListener {
    private Game game;
    private int playerID;
    
    public KeyBoardListener(Game game, int playerID) {
        this.game = game;
        this.playerID = playerID;
    }
            
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Forward")) {
            this.game.acceleratePlayer(this.playerID, tpf);
        } else if (name.equals("Left")) {
            this.game.steerPlayer(Direction.ANTICLOCKWISE, tpf);
        } else if (name.equals("Right")) {
            this.game.steerPlayer(Direction.CLOCKWISE, tpf);
        }
    }
    
}