/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

import model.tools.Settings;

/**
 *
 * @author johnhu
 */
public class PUDrunk extends AbstractPowerUp {

    private String name = "Drunk";
    private String message = "Yer drunk!!!!!!!! Inverted steering";

    public PUDrunk() {
        super();
        this.steerAngle = -Settings.getInstance().getSetting("steerAngle")*2;
        this.lifeTime = 10;
    }

    public void update(final float tpf) {
        if (lifeTime <= 0) {
            this.isActive = false;
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
}
