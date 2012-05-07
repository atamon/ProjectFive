/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import physics.PhysType;
import model.tools.IObservable;
import math.Vector;
import physics.PhysicalCannonBall;
import physics.PhysicalGameObject;

/**
 * A class to represent a CannonBall.
 *
 * @author victorlindhe
 */
public class CannonBall extends MoveableAbstract implements IObservable {
    private final Unit owner;
    
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
            final float mass,
            final float speed,
            final Unit owner) {
        this.body = new PhysicalCannonBall(this, position, direction, size, mass, speed);
        this.owner = owner;
    }

    public void update(float tpf) {
        setPosition(this.body.getPosition());
        setDirection(this.body.getDirection());
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

    public Unit getOwner() {
        return this.owner;
    }
}
