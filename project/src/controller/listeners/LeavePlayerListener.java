/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.listeners;

import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import controller.keymaps.PlayerOneKeys;
import controller.keymaps.PlayerThreeKeys;
import controller.keymaps.PlayerTwoKeys;
import controller.keymaps.PlayerZeroKeys;
import java.util.HashMap;
import java.util.Map;
import model.GameState;
import model.IGame;


/**
 * Listens to leaving players.
 **/
public class LeavePlayerListener implements ActionListener {

    private final Map<Integer, KeyTrigger> leaveKeys = new HashMap<Integer, KeyTrigger>();
    private final IGame game;

    public LeavePlayerListener(IGame game, InputManager inpManager) {
        this.game = game;

        // Set up join keys for all supported players and add them to manager
        leaveKeys.put(0, new KeyTrigger(PlayerZeroKeys.KEY_LEAVE));
        leaveKeys.put(1, new KeyTrigger(PlayerOneKeys.KEY_LEAVE));
        leaveKeys.put(2, new KeyTrigger(PlayerTwoKeys.KEY_LEAVE));
        leaveKeys.put(3, new KeyTrigger(PlayerThreeKeys.KEY_LEAVE));

        setKeys(inpManager);

    }

    private void setKeys(InputManager inpManager) {
        for (Integer id : leaveKeys.keySet()) {
            KeyTrigger mapTrigger = leaveKeys.get(id);
            inpManager.addMapping("leave" + id, mapTrigger);
            inpManager.addListener(this, "leave" + id);
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed && game.getState() == GameState.INACTIVE) {
            int id = -1;
            String stringID = name.replace("leave", "");
            try {
                id = Integer.parseInt(stringID);
            } catch (NumberFormatException e) {
                System.out.println("Illegal playerID registered in GlobalKeyListener."
                        + " Not a Number!");
            }
            if (game.hasPlayer(id)) {
                game.removePlayer(id);
            }
        }
    }
}
