/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.input.KeyInput;
import util.Util;

/**
 *
 * @author victorlindhe
 */
public class PlayerThreeKeys implements KeyPlayable {
    
    public int getForwardKey() {
        return KeyInput.KEY_P;
    }

    public int getLeftKey() {
        return KeyInput.KEY_L;
    }

    public int getRightKey() {
        return Util.getSpecialKeyCode('ä');
    }
    
    public int getLeftFireKey() {
        return KeyInput.KEY_O;
    }
    
    public int getRightFireKey() {
        return Util.getSpecialKeyCode('å');
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