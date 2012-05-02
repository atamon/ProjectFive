/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import model.physics.IPhysical;
import model.physics.PhysType;
import model.tools.IObservable;
import model.tools.Vector;

/**
 *
 * @author victorlindhe
 */
public class CannonBall extends MoveableAbstract implements IObservable, IPhysical {
    final private Vector size = new Vector(0.1f, 0.1f);
    final private int owner;
    private final int damage;
    
    public CannonBall(final int owner, final Vector position, final Vector direction,
                      final float speed) {
        super(position, direction);
        this.owner = owner;
        this.setSpeed(speed);
        this.damage = (int) (speed*this.getMass());
    }

    public void update(float tpf) {
        this.move(tpf);
    }
    public void hide(){
        setSpeed(0);
        this.maxSpeed = 0;
        setPosition(Vector.NONE_EXISTANT);
    }
    public Vector getSize() {
        return this.size;
    }

    public float getMass() {
        return this.size.getX()+this.size.getY();
    }

    public PhysType getType() {
        return PhysType.CANNONBALL;
    }

    public void removeFromView() {
        this.pcs.firePropertyChange("CannonBall Removed", null, null);
    }

    public int getDamage() {
        return this.damage;
    }

    public int getOwner() {
        return this.owner;
    }
    
}
