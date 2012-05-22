/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

import model.settings.Settings;

/**
 *  PowerUp that makes crew drunk.
 **/
public class PUDrunk extends AbstractPowerUp {

    private String name = "Drunk";
    private String message = "Yer drunk!!!!!!!! Inverted steering";
    
    private PUDrunk(PUDrunk that) {
        super(that);
    }
    
    public PUDrunk() {
        super();
        steerAngle = -Settings.getInstance().getSetting("steerAngle")*2;
        lifeTime = 10;
    }

    public void update(final float tpf) {
        if (lifeTime <= 0) {
            active = false;
        } else {
            lifeTime -= tpf;
        }
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    @Override
    public IPowerUp clone() {
        return new PUDrunk(this);
    }

}
