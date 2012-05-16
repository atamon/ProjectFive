/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listeners;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import controller.keymaps.KeyPlayable;
import model.IGame;
import model.player.Player;
import model.round.RoundState;

/**
 *
 * @author victorlindhe @modified johnhu
 */
public class PlayerListener implements ActionListener, AnalogListener {

    private Player player;
    private KeyPlayable layout;
    private IGame game;

    public PlayerListener(Player player, InputManager inpManager, IGame game) {
        this.player = player;
        this.layout = KeyFactory.getPlayerKeys(player);
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
            if (name.equals(this.layout.getLeftFireMap())) {
                this.player.fireLeft();
            }

            if (name.equals(this.layout.getRightFireMap())) {
                this.player.fireRight();
            }
        }

        if (name.equals(this.layout.getUpMap())) {
            this.player.accelerateUnit(isPressed);
        }

        if (name.equals(this.layout.getLeftMap())) {
            this.player.steerUnitAntiClockWise(isPressed);
        }

        if (name.equals(this.layout.getRightMap())) {
            this.player.steerUnitClockWise(isPressed);
        }
    }

    public void onAnalog(String name, float value, float tpf) {

        if (game.getRoundState() == RoundState.PLAYING 
                && game.getPlayer(player.getId()).canUnitFire()) {
            if (name.equals(this.layout.getLeftFireMap())) {
                this.player.increaseFirePowerLeft(value);
            }
            if (name.equals(this.layout.getRightFireMap())) {
                this.player.increaseFirePowerRight(value);
            }
        }
    }
}