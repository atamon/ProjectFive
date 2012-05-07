/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.physics.IPhysical;
import model.tools.PhysType;
import model.tools.IObservable;
import model.tools.Vector;

/**
 * A class to represent a CannonBall.
 * @author victorlindhe
 */
public class CannonBall extends MoveableAbstract implements IObservable, IPhysical {
    final private Vector size = new Vector(0.1f, 0.1f);
    final private int owner;
    private final int damage;
    
    /**
     * Creates a CannonBall.
     * @param owner Player ID of the Player that owns the CannonBall.
     * @param position Position on the Battlefield.
     * @param direction Direction of the CannonBall.
     * @param speed Speed of the CannonBall.
     */
    public CannonBall(final int owner, final Vector position, final Vector direction,
                      final float speed) {
        super(position, direction);
        this.owner = owner;
        this.setSpeed(speed);
        this.damage = (int) (this.speed*this.getMass());
    }

    /**
     * Moves CannonBall in its direction.
     * @param tpf Time per frame.
     */
    public void update(float tpf) {
        this.move(tpf);
    }
    
    public void hide(){
        setSpeed(0);
        this.maxSpeed = 0;
        setPosition(Vector.NONE_EXISTANT);
    }
    
    /**
     * Returns the size as a Vector.
     * @return Vector
     */
    public Vector getSize() {
        return new Vector(this.size.getX(), this.size.getY());
    }

    /**
     * Returns the mass of the CannonBall.
     * @return 
     */
    public float getMass() {
        return Math.abs(this.size.getX()+this.size.getY());
    }

    /**
     * Returns PhysType of the CannonBall.
     * That is a CANNONBALL.
     * @return PhysType enum
     */
    public PhysType getType() {
        return PhysType.CANNONBALL;
    }

    /**
     * Removes the CannonBall from any instance that has it.
     */
    public void removeFromView() {
        this.pcs.firePropertyChange("CannonBall Removed", null, null);
    }

    /**
     * Returns the damage of the CannonBall.
     * Damage is calculated
     * @return int
     */
    public int getDamage() {
        return this.damage;
    }

    /**
     * Returns ID of the owner of this CannonBall.
     * @return int
     */
    public int getOwner() {
        return this.owner;
    }
    
}
