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
public class PlayerThreeKeys implements KeyPlayable {
    
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
        return KeyInput.KEY_NUMPAD8;
    }
    
    public int getRightFireKey() {
        return KeyInput.KEY_NUMPAD9;
    }
    
    public int getLeaveKey() {
        return KeyInput.KEY_NUMPAD5;
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
    
    public String getLeaveMap() {
        return "PlayerThree Leave";
    }
    
}