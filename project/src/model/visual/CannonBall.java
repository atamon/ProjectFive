/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import model.physics.IPhysical;
import model.tools.IObservable;
import model.tools.Vector;

/**
 *
 * @author victorlindhe
 */
public class CannonBall extends MoveableAbstract implements IObservable, IPhysical {
    final private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    final private Vector size = new Vector(0.1f, 0.1f);
    final private int owner;
    
    public CannonBall(final int owner, final Vector position, final Vector direction,
                      final float speed) {
        super(position, direction);
        this.owner = owner;
        this.setSpeed(speed);
    }
    
    @Override
    protected void directionUpdated() {
        this.pcs.firePropertyChange("Updated Direction", null, this.getDirection());
    }

    @Override
    protected void positionUpdated() {
        this.pcs.firePropertyChange("Updated Position", null, this.getPosition());
    }

    public Vector getSize() {
        return this.size;
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.removePropertyChangeListener(ls);
    }

    public float getMass() {
        return this.size.getX()+this.size.getY();
    }
    
}
