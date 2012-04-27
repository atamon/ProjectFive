/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.keymaps;

import com.jme3.input.KeyInput;

/**
 *
 * @author victorlindhe
 * @modified johnhu
 */
public class PlayerOneKeys implements KeyPlayable {
    
    public static final int KEY_JOIN = KeyInput.KEY_UP;
    public static final int KEY_LEAVE = KeyInput.KEY_DOWN;
    
    public int getForwardKey() {
        return KeyInput.KEY_UP;
    }

    public int getLeftKey() {
        return KeyInput.KEY_LEFT;
    }

    public int getRightKey() {
        return KeyInput.KEY_RIGHT;
    }
    
    public int getLeftFireKey() {
        return KeyInput.KEY_MINUS;
    }
    
    public int getRightFireKey() {
        return KeyInput.KEY_RSHIFT;
    }
    
    public String getUpMap() {
        return "PlayerOne Up";
    }

    public String getLeftMap() {
        return "PlayerOne Left";
    }

    public String getRightMap() {
        return "PlayerOne Right";
    }
    
    public String getLeftFireMap() {
        return "PlayerOne LeftFire";
    }
    
    public String getRightFireMap() {
        return "PlayerOne RightFire";
    }
    
}
