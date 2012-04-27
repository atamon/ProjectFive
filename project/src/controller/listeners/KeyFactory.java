/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listeners;

import controller.keymaps.KeyPlayable;
import controller.keymaps.PlayerZeroKeys;
import controller.keymaps.PlayerTwoKeys;
import controller.keymaps.PlayerOneKeys;
import controller.keymaps.PlayerThreeKeys;
import model.player.Player;


/**
 *
 * @author victorlindhe
 * @modified johnhu
 */
public class KeyFactory {
    public static KeyPlayable getPlayerKeys(Player player) {
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
