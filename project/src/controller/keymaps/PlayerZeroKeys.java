/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.keymaps;

import com.jme3.input.KeyInput;

/**
 * Keys for player 0.
 **/
public class PlayerZeroKeys implements IKeyPlayable {
    
    public static final int KEY_JOIN = KeyInput.KEY_W;
    public static final int KEY_LEAVE = KeyInput.KEY_S;
    
    public int getForwardKey() {
        return KeyInput.KEY_W;
    }

    public int getLeftKey() {
        return KeyInput.KEY_A;
    }

    public int getRightKey() {
        return KeyInput.KEY_D;
    }
    
    public int getLeftFireKey() {
        return KeyInput.KEY_Q;
    }
    
    public int getRightFireKey() {
        return KeyInput.KEY_E;
    }

    public String getUpMap() {
        return "PlayerZero Up";
    }

    public String getLeftMap() {
        return "PlayerZero Left";
    }

    public String getRightMap() {
        return "PlayerZero Right";
    }
    
    public String getLeftFireMap() {
        return "PlayerZero LeftFire";
    }
    
    public String getRightFireMap() {
        return "PlayerZero RightFire";
    }
    
}
