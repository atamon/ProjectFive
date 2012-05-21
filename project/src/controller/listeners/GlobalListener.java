/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listeners;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import model.GameState;
import model.IGame;
import model.round.RoundState;

/**
 *
 * @author johnhu
 */
public class GlobalListener implements ActionListener {

    private IGame game;

    public GlobalListener(IGame game, InputManager inpManager) {
        this.game = game;

        inpManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inpManager.addMapping("Start", new KeyTrigger(KeyInput.KEY_SPACE));
        inpManager.addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        
        inpManager.addListener(this, "Pause");
        inpManager.addListener(this, "Start");
        inpManager.addListener(this, "Exit");
    }

    public void onAction(String name, boolean isPressed, float tpf) {

        if (isPressed && game.hasValidAmountOfPlayers()) {
            if ("Pause".equals(name)) {
                game.switchPauseState();
            }
            // Start sends different calls to model according to different states the model is in.
            if ("Start".equals(name) && game.getRoundState() != RoundState.PLAYING) {
                // Start checking for different states the controller wishes to handle
                // differently.

                GameState gameState = game.getState();
                RoundState roundState = game.getRoundState();

                // This says game has not yet been started
                if (gameState == GameState.INACTIVE) {
                    if (game.hasValidAmountOfPlayers()) {
                        game.start();
                    }
                } else if (gameState == GameState.ACTIVE && roundState == RoundState.POST) {
                    game.nextRound();
                } else if (gameState == GameState.STATS && roundState == RoundState.POST) {
                    // Game has been played. Clean it!
                    game.clean();
                }
            }
            
            if ("Exit".equals(name)) {
                System.exit(0);
            }
        }
    }
}
