/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import observable.IObservable;
import math.Vector;
import physics.ICollideable;
import physics.IPhysicalModel;
import physics.PhysicalCannonBall;

/**
 * A class to represent a CannonBall.
 *
 * @author victorlindhe
 */
public class CannonBall extends MoveableAbstract implements IObservable, IProjectile {
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
        ;
        // TODO ADD COOL UPDATELOOP?
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
        this.pcs.firePropertyChange("CannonBall Removed", null, this);
    }

    public Unit getOwner() {
        return this.owner;
    }
    
    public void collidedWith(ICollideable obj, float objImpactSpeed) {
        // On crash we just disappear, Unit takes care of damage
        if (obj instanceof Unit) {
            announceRemoval();
        }
        
        if (obj instanceof Battlefield) {
            announceRemoval();
        }
    }
}
