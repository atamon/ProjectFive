/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

/**
 *  PowerUp that makes the steering harder.
 **/
public class PUTurn extends AbstractPowerUp {

    private String name = "Turn";
    private String message = "Ya drank so much so ya do not give a crap about"
            + " the safety of yer crew! Fast turns won't be a problem matey!";
    
    public PUTurn() {
        super();
        steerAngle = 3;
        lifeTime = 10;
    }

    private PUTurn(PUTurn that) {
        super(that);
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
        return new PUTurn(this);
    }
}
