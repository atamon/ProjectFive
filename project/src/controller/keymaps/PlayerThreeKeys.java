/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.keymaps;

import com.jme3.input.KeyInput;

/**
 * Keys for player 3.
 **/
public class PlayerThreeKeys implements IKeyPlayable {
    
    public static final int KEY_JOIN = KeyInput.KEY_NUMPAD8;
    public static final int KEY_LEAVE = KeyInput.KEY_NUMPAD5;
    
    public int getForwardKey() {
        return KeyInput.KEY_NUMPAD8;
    }

    public int getLeftKey() {
        return KeyInput.KEY_NUMPAD4;
    }

    public int getRightKey() {
        return KeyInput.KEY_NUMPAD6;
    }
    
    public int getLeftFireKey() {
        return KeyInput.KEY_NUMPAD7;
    }
    
    public int getRightFireKey() {
        return KeyInput.KEY_NUMPAD9;
    }
    
    public String getUpMap() {
        return "PlayerThree Up";
    }

    public String getLeftMap() {
        return "PlayerThree Left";
    }

    public String getRightMap() {
        return "PlayerThree Right";
    }
    
    public String getLeftFireMap() {
        return "PlayerThree LeftFire";
    }
    
    public String getRightFireMap() {
        return "PlayerThree RightFire";
    }
    
}