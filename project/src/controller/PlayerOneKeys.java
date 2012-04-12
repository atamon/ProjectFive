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
public class PlayerOneKeys implements KeyPlayable {

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
    
}
