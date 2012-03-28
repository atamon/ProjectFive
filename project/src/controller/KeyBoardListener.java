/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import model.Direction;
import model.Game;
import model.IGame;

/**
 *
 * @author victorlindhe
 */
public class KeyBoardListener implements ActionListener, AnalogListener {
    private IGame game;
    private int playerID;
    
    public KeyBoardListener(IGame game, int playerID) {
        this.game = game;
        this.playerID = playerID;
    }
            

    
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Forward" + playerID)) {
                this.game.acceleratePlayerUnit(this.playerID, isPressed);
        }
    }

    public void onAnalog(String name, float value, float tpf) {
        
            if (name.equals("Left" + playerID)) {
                this.game.steerPlayerUnit(Direction.ANTICLOCKWISE, this.playerID, tpf);
            }
            if (name.equals("Right" + playerID)) {
                this.game.steerPlayerUnit(Direction.CLOCKWISE, this.playerID, tpf);
            }
    }
    
}
