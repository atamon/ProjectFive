/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tools;

import controller.ValueLoader;
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
    private Map<String, Integer> attributes = new HashMap<String, Integer>();
    public float timeLeft;
    
    
    public PowerUp() {
        this.attributes = ValueLoader.readValues("assets/powerUps", getRandomPower());
        this.timeLeft = this.attributes.get("lifetime");
    }
    
    public Integer getValue(String attrib) {
        return attributes.get(attrib);
    }
    
    private String getRandomPower() {
        String power = "";
        int randNumber = (int)(Math.random()*2+1);
        switch (randNumber) {
            case 1:
                power = "SPEED";
                break;
            case 2:
                power = "HEALTH";
                break;
        } return power;
    }

    public void update(float tpf) {
        timeLeft -= tpf;
    }
}
