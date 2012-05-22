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
    public static IKeyPlayable getPlayerKeys(final Player player) {
        final int playerId = player.getId();
        IKeyPlayable playerKeys = null;
        switch(playerId){
            case 0:
                playerKeys = new PlayerZeroKeys();
                break;
            case 1: 
                playerKeys = new PlayerOneKeys();
                break;
            case 2:
                playerKeys = new PlayerTwoKeys();
                break;
            case 3:
                playerKeys = new PlayerThreeKeys();
                break;
            default:
                throw new IllegalArgumentException("Only player ids 0-3 supported. Received id: "+playerId);
        }
        return playerKeys;
    }
}
