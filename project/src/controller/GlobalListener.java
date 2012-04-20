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
        
        // TODO THIS MUST BE DELETED LATER ON
        addDummyButton();
    }
    
    private void addDummyButton() {
        inpManager.addMapping("KillPlayerOne", new KeyTrigger(KeyInput.KEY_O));
        inpManager.addListener(this, "KillPlayerOne");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        // PAUSE and START is only available 
        if (isPressed && game.hasValidAmountPlayers()) {
            if ("Pause".equals(name)) {
                game.switchPauseState();
            } else if ("Start".equals(name) && !game.roundStarted()) {
                game.startRound();
            }
        }
        
        // TODO DELETE THIS UPON REAL IMPLEMENTATION !!!
        if ("KillPlayerOne".equals(name) && isPressed) {
            game.getPlayer(1).getUnit().setHitPoints(0);
            // Now waiat for eternal hell MOHAHAHAHAHAHAHAH :D
        }
    }
}
