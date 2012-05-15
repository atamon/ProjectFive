/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

/**
 *
 * @author johnhu
 */
public class PUTurn extends AbstractPowerUp {

    private String name = "Turn";
    private String message = "Ya drank so much so ya do not give a crap about"
            + " the safety of yer crew! Fast turns won't be a problem matey!";

    public PUTurn() {
        super();
        this.steerAngle = 3;
        this.lifeTime = 10;
    }

    public void update(final float tpf) {
        if (lifeTime <= 0) {
            this.active = false;
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
