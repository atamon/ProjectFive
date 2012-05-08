/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

import model.tools.Settings;

/**
 *
 * @author CaptainAwesome
 */
public class PUHealth extends AbstractPowerUp {

    private final String name = "Health";
    private final String message = "Yaarrrr! Ye health is replenished from the bottle o' rum!";
    
    public PUHealth() {
        super();
        this.hitPoints = Settings.getInstance().getSetting("hitPointsMax");
    }
    
    public void update(float tpf) {
        //Nothing should be done here since it's an instant PowerUp
    }

    public String getMessage() {
        return this.message;
    }

    public String getName() {
        return this.name;
    }
    
}
