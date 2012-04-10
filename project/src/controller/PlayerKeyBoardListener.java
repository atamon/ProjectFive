/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import model.util.Direction;

/**
 *
 * @author victorlindhe
 */
public class PlayerKeyBoardListener implements ActionListener, AnalogListener {
    private PlayerAdapter player;
    private KeyPlayable layout;
    
    public PlayerKeyBoardListener(PlayerAdapter player, InputManager inpManager) {
        this.player = player;
        this.layout = PlayerKeyBoardFactory.getPlayerKeys(player);
        inpManager.addMapping(layout.getUpMap(), new KeyTrigger(layout.getForwardKey()));
        inpManager.addMapping(layout.getLeftMap(), new KeyTrigger(layout.getLeftKey()));
        inpManager.addMapping(layout.getRightMap(), new KeyTrigger(layout.getRightKey()));
        
        inpManager.addListener(this, layout.getLeftMap());
        inpManager.addListener(this, layout.getRightMap());
        inpManager.addListener(this, layout.getUpMap());
    }
            

    
    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals(this.layout.getUpMap())) {
                this.player.accelerateUnit(isPressed);
        }
    }

    public void onAnalog(String name, float value, float tpf) {
        
            if (name.equals(this.layout.getLeftMap())) {
                this.player.steerUnit(Direction.ANTICLOCKWISE, tpf);
            }
            if (name.equals(this.layout.getRightMap())) {
                this.player.steerUnit(Direction.CLOCKWISE, tpf);
            }
    }
    
}
