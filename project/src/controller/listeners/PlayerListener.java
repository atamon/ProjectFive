/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listeners;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import controller.keymaps.IKeyPlayable;
import model.IGame;
import model.player.Player;
import model.round.RoundState;

/**
 * Listens to a specific Player.
 **/
public class PlayerListener implements ActionListener, AnalogListener {

    private Player player;
    private IKeyPlayable layout;
    private IGame game;

    public PlayerListener(Player player, InputManager inpManager, IGame game) {
        this.player = player;
        layout = KeyFactory.getPlayerKeys(player);
        this.game = game;

        inpManager.addMapping(layout.getUpMap(), new KeyTrigger(layout.getForwardKey()));
        inpManager.addMapping(layout.getLeftMap(), new KeyTrigger(layout.getLeftKey()));
        inpManager.addMapping(layout.getRightMap(), new KeyTrigger(layout.getRightKey()));
        inpManager.addMapping(layout.getLeftFireMap(), new KeyTrigger(layout.getLeftFireKey()));
        inpManager.addMapping(layout.getRightFireMap(), new KeyTrigger(layout.getRightFireKey()));

        inpManager.addListener(this, layout.getLeftMap());
        inpManager.addListener(this, layout.getRightMap());
        inpManager.addListener(this, layout.getUpMap());
        inpManager.addListener(this, layout.getLeftFireMap());
        inpManager.addListener(this, layout.getRightFireMap());
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed && game.getRoundState() == RoundState.PLAYING
                && game.getPlayer(player.getId()).canUnitFire()) {
            if (name.equals(layout.getLeftFireMap())) {
                player.fireLeft();
            }

            if (name.equals(layout.getRightFireMap())) {
                player.fireRight();
            }
        }

        if (name.equals(layout.getUpMap())) {
            player.accelerateUnit(isPressed);
        }

        if (name.equals(layout.getLeftMap())) {
            player.steerUnitAntiClockWise(isPressed);
        }

        if (name.equals(layout.getRightMap())) {
            player.steerUnitClockWise(isPressed);
        }
    }

    public void onAnalog(String name, float value, float tpf) {

        if (game.getRoundState() == RoundState.PLAYING 
                && game.getPlayer(player.getId()).canUnitFire()) {
            if (name.equals(layout.getLeftFireMap())) {
                player.increaseFirePowerLeft(value);
            }
            if (name.equals(layout.getRightFireMap())) {
                player.increaseFirePowerRight(value);
            }
        }
    }
}