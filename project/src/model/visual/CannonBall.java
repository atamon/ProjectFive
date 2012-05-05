/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import physics.PhysType;
import model.tools.IObservable;
import math.Vector;

/**
 * A class to represent a CannonBall.
 *
 * @author victorlindhe
 */
public class CannonBall extends MoveableAbstract implements IObservable {

    /**
     * Creates a CannonBall.
     *
     * @param owner Player ID of the Player that owns the CannonBall.
     * @param position Position on the Battlefield.
     * @param direction Direction of the CannonBall.
     * @param speed Speed of the CannonBall.
     */
    public CannonBall(final Vector position,
            final Vector direction,
            final Vector size,
            final float height,
            final float mass,
            final float speed) {
        super(position, direction, size, mass);
    }

    public void update(float tpf) {
        ; // TODO Left for fututre implementation of cool splittable cannonballs
    }

    /**
     * Returns the damage of the CannonBall. Damage is calculated
     *
     * @return int
     */
    public int getDamage() {
        return (int)(body.getMass()*body.getSpeed());
    }

    /**
     * Removes the CannonBall from any instance that has it.
     */
    public void announceRemoval() {
        this.pcs.firePropertyChange("CannonBall Removed", null, null);
    }
}
