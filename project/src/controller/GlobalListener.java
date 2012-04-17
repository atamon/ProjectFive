/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import model.IGame;

/**
 *
 * @author johnhu
 */
public class GlobalListener implements ActionListener {
    
    private IGame game;
    private InputManager inpManager;

    
    public GlobalListener(IGame game, InputManager inpManager) {
        this.game = game;
        this.inpManager = inpManager;
        
        inpManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inpManager.addMapping("Start", new KeyTrigger(KeyInput.KEY_SPACE));
        
        inpManager.addListener(this, "Pause");
        inpManager.addListener(this, "Start");
    }
    
    public void onAction(String name, boolean isPressed, float tpf) {
        System.out.println(name);
        if (name.equals("Pause") && isPressed) {
            game.switchPauseState();
        }
        else if (name.equals("Start") && isPressed) {
            game.startRound();
        }
    }
    
}
