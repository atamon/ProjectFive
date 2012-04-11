/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Player;


/**
 *
 * @author victorlindhe
 */
public class KeyFactory {
    public static KeyPlayable getPlayerKeys(Player player) {
        if(player.getId() == 0) {
            return new PlayerZeroKeys();
        } else if (player.getId() == 1) {
            return new PlayerOneKeys();
        } else {
            return null;
        }
    }
}
