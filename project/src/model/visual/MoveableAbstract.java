/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import model.settings.Settings;
import math.Vector;
import physics.ICollideable;
import physics.IPhysicalBody;
import physics.PhysicalUnit;

/**
 *
 * @author johannes wikner
 */
public abstract class MoveableAbstract implements IMoveable {

    protected int maxSpeed = Settings.getInstance().getSetting("maxSpeed");
    protected int acceleration = Settings.getInstance().getSetting("acceleration");
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    private IPhysicalBody body;

    /**
     * Sets the position of the moveable
     *
     * @param pos Vector with coordinates where to position the unit
     */
    public void setPosition(Vector pos) {
        body.place(pos);
    }

    /**
     * Sets the position of the movable
     *
     * @param x New position in x-axis
     * @param y New positoin in y-axis
     * @param z New position in z-axis
     */
    public void setPosition(float x, float y, float z) {
        setPosition(new Vector(x, y, z));
    }

    public void setBody(final IPhysicalBody body) {
        this.body = body;
    }
    /**
     * Sets our movable's direction.
     *
     * @param dir A vector holding the new direction
     */
    public void setDirection(Vector dir) {
        body.point(dir);
    }

    public Vector getDirection() {
        return body.getDirection();
    }

    public Vector getPosition() {
        return body.getPosition();
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return pcs;
    }
    /**
     *
     * @return The unit's acceleration
     */
    public int getAcceleration() {
        return acceleration;
    }

    /**
     *
     * @return Movable object's maximum speed
     */
    public float getMaxSpeed() {
        return maxSpeed;
    }

    /**
     *
     * @return The movable object's current speed
     */
    public float getSpeed() {
        return body.getSpeed();
    }
    
    /**
     * Sets the maxSpeed of unit
     * @param maxSpeed How fast a unit can move 
     */
    public void setMaxSpeed(float speed) {
        maxSpeed = (int)speed;
    }

    public void halt() {
        body.halt();
    }

    public Vector getSize() {
        return body.getSize();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MoveableAbstract other = (MoveableAbstract) obj;
        if (Float.floatToIntBits(maxSpeed) != Float.floatToIntBits(other.maxSpeed)) {
            return false;
        }
        if (body != other.body && (body == null || !body.equals(other.body))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Float.floatToIntBits(maxSpeed);
        hash = 59 * hash + (body != null ? body.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MoveableAbstract{" + ", maxSpeed=" + maxSpeed + ", body=" + body + '}';
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        body.addPropertyChangeListener(ls);
        pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        pcs.removePropertyChangeListener(ls);
    }
    
    public IPhysicalBody getPhysicalObject() {
        return body;
    }
    
    public void announceShow() {
        pcs.firePropertyChange("Show Moveable", null, this);
    }
    
    public void announceHide() {
        pcs.firePropertyChange("Hide Moveable", null, this);
    }

    public void announceRemoval(){
        pcs.firePropertyChange("Visual Removed", null, this);
    }
    public abstract void collidedWith(ICollideable obj, float objImpactSpeed);

}
