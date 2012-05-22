/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listeners;

import controller.keymaps.IKeyPlayable;
import controller.keymaps.PlayerZeroKeys;
import controller.keymaps.PlayerTwoKeys;
import controller.keymaps.PlayerOneKeys;
import controller.keymaps.PlayerThreeKeys;
import model.player.Player;


/**
 *  Creates IKeyPlayables.
 **/
public class KeyFactory {
    public static IKeyPlayable getPlayerKeys(Player player) {
        if(player.getId() == 0) {
            return new PlayerZeroKeys();
        } else if (player.getId() == 1) {
            return new PlayerOneKeys();
        } else if (player.getId() == 2) {
            return new PlayerTwoKeys();
        } else if (player.getId() == 3) {
            return new PlayerThreeKeys();
        } else {
            return null;
        }
    }
}
