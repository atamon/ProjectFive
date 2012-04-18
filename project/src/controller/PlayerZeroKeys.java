/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.KeyInput;

/**
 *
 * @author victorlindhe
 * @modified johnhu
 */
public class PlayerZeroKeys implements KeyPlayable {
    
    public static final int KEY_JOIN = KeyInput.KEY_W;

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
