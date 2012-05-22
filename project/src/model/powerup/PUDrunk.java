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
        this.steerAngle = -Settings.getInstance().getSetting("steerAngle")*2;
        this.lifeTime = 10;
    }

    public void update(final float tpf) {
        if (lifeTime <= 0) {
            active = false;
        } else {
            this.lifeTime -= tpf;
        }
    }

    public String getMessage() {
        return this.message;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public IPowerUp clone() {
        return new PUDrunk(this);
    }

}
