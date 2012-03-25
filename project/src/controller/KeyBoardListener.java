/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Direction;
import model.Game;
import com.jme3.input.controls.AnalogListener;

/**
 *
 * @author victorlindhe
 */
public class KeyBoardListener implements AnalogListener {
    private Game game;
    private int playerID;
    
    public KeyBoardListener(Game game, int playerID) {
        this.game = game;
        this.playerID = playerID;
    }
            
    public void onAnalog(String name, float value, float tpf) {
        if(name.equals("Forward")) {
            this.game.acceleratePlayerUnit(this.playerID, tpf);
        } else if (name.equals("Left")) {
            this.game.steerPlayer(Direction.ANTICLOCKWISE, this.playerID, tpf);
        } else if (name.equals("Right")) {
            this.game.steerPlayer(Direction.CLOCKWISE, this.playerID, tpf);
        }
    }
    
}
