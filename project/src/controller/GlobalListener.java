/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import model.GameState;
import model.IGame;
import model.RoundState;

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
        inpManager.addMapping("KillPlayerTwo", new KeyTrigger(KeyInput.KEY_L));
        inpManager.addListener(this, "KillPlayerTwo");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        // PAUSE and START is only available 
        if (isPressed && game.hasValidAmountOfPlayers()) {
            if ("Pause".equals(name)) {
                game.switchPauseState();
            }
            // Start sends different calls to model according to different states the model is in.
            // It should never do anything if we have a running round though.
            if ("Start".equals(name) && game.getRoundState() != RoundState.PLAYING) {
                // Start checking for different states the controller wishes to handle
                // differently.

                GameState gameState = game.getState();
                RoundState roundState = game.getRoundState();

                // This says game has not yet been started
                if (gameState == GameState.INACTIVE) {
                    // If we have enough players we can set ACTIVE
                    if (game.hasValidAmountOfPlayers()) {
                        game.start();
                    }
                } else if (gameState == GameState.ACTIVE && roundState == RoundState.POST) {
                    game.nextRound();
                } else if (gameState == GameState.STATS && roundState == RoundState.POST) {
                    // Game has been played. Now we wish to clean game-state and present the players
                    // With our start-interface and remove previous game-state (IE rounds).
                    game.clean();
                }
            }
        }

        // TODO DELETE THIS UPON REAL IMPLEMENTATION !!!
        if ("KillPlayerOne".equals(name) && isPressed) {
            game.getPlayer(1).getUnit().setHitPoints(0);
            // Now wait for eternal hell MOHAHAHAHAHAHAHAH :D
        }
        if ("KillPlayerTwo".equals(name) && isPressed) {
            game.getPlayer(0).getUnit().setHitPoints(0);
            // Now wait for eternal hell MOHAHAHAHAHAHAHAH :D
        }
    }
}
