/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.KeyInput;

/**
 *
 * @author victorlindhe
 */
public class PlayerZeroKeys implements KeyPlayable {

    public int getForwardKey() {
        return KeyInput.KEY_W;
    }

    public int getLeftKey() {
        return KeyInput.KEY_A;
    }

    public int getRightKey() {
        return KeyInput.KEY_D;
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
    
}
