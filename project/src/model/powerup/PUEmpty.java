/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

/**
 *  PowerUp that has nothing.
 **/
public class PUEmpty extends AbstractPowerUp {

    public PUEmpty() {
        super();
    }

    private PUEmpty(PUEmpty that) {
        super(that);
    }
    
    public void update(final float tpf) {
        // nothing to be done here..
    }

    public String getMessage() {
        return "No powerUp";
    }

    public String getName() {
        return "Empty";
    }

    @Override
    public IPowerUp clone() {
        return new PUEmpty(this);
    }
    
}
