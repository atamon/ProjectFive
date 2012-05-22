/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.keymaps;

import com.jme3.input.KeyInput;

/**
 * Keys for player 2.
 **/
public class PlayerTwoKeys implements IKeyPlayable {

    public static final int KEY_JOIN = KeyInput.KEY_Y;
    public static final int KEY_LEAVE = KeyInput.KEY_H;
    
    public int getForwardKey() {
        return KeyInput.KEY_Y;
    }

    public int getLeftKey() {
        return KeyInput.KEY_G;
    }

    public int getRightKey() {
        return KeyInput.KEY_J;
    }
    
    public int getLeftFireKey() {
        return KeyInput.KEY_T;
    }
    
    public int getRightFireKey() {
        return KeyInput.KEY_U;
    }
    
    public int getLeaveKey() {
        return KeyInput.KEY_H;
    }
    
    public String getUpMap() {
        return "PlayerTwo Up";
    }

    public String getLeftMap() {
        return "PlayerTwo Left";
    }

    public String getRightMap() {
        return "PlayerTwo Right";
    }
    
    public String getLeftFireMap() {
        return "PlayerTwo LeftFire";
    }
    
    public String getRightFireMap() {
        return "PlayerTwo RightFire";
    }
}