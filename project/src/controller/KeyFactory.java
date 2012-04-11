/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


/**
 *
 * @author victorlindhe
 */
public class KeyFactory {
    public static KeyPlayable getPlayerKeys(PlayerAdapter player) {
        if(player.getID() == 0) {
            return new PlayerZeroKeys();
        } else if (player.getID() == 1) {
            return new PlayerOneKeys();
        } else {
            return null;
        }
    }
}
