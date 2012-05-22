/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeSupport;
import observable.IObservable;
import math.Vector;
import physics.ICollideable;
import physics.IPhysicalBody;
import physics.IPhysicalModel;
import physics.PhysicalCannonBall;

/**
 * A class to represent a CannonBall.
 *
 * @author victorlindhe
 */
public class CannonBall extends MoveableAbstract implements IObservable, IProjectile {
    private final Unit owner;
    private final IPhysicalBody body;
    private final PropertyChangeSupport pcs;
    
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
        body = new PhysicalCannonBall(this, position, direction, size, mass, speed);
        super.setBody(body);
        this.pcs = super.getPropertyChangeSupport();
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

    public Unit getOwner() {
        return owner;
    }
    
    public void collidedWith(ICollideable obj, float objImpactSpeed) {

        if (!(obj instanceof Item)){
            announceRemoval();
        }
    }
}
