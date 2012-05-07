/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

/**
 *
 * @author jnes
 */
public class PUEmpty extends AbstractPowerUp {

    public void update(final float tpf) {
        // nothing to be done here..
    }

    public String getMessage() {
        return "No powerUp";
    }

    public String getName() {
        return "Empty";
    }
    
}
