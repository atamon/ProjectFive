/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tools;

import java.util.HashMap;
import java.util.Map;


/**
 * Sketch of what a power up might look like
 * A PowerUp can have multiple affecting attributes 
 * An integer value corresponds to an attribute
 * 
 * @author jnes
 */
public final class PowerUp {
    private Map<Integer, Float> attributes = new HashMap<Integer, Float>();
    public static int PU_SPEED = 1;
    public static int PU_ACCELERATE = 2;
    public static int PU_HP = 3;
    
    
    public PowerUp(Map<Integer, Float> attributes){
        //Contruct for a powerup with multiple attributes
    }
    
    public PowerUp(int attribKey, Float value){
        //Constructor for a powerup with a single attribute
    }
    
    public PowerUp(){
        //Constructor for a randomized PowerUp
    }
    
    public float getValue(int attrib) {
        return attributes.get(attrib);
    }
}
