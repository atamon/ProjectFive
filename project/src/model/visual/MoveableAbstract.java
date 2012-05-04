/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import model.tools.Settings;
import math.Vector;
import physics.AbstractGameObject;
import physics.PhysicalBody;
import physics.PhysicalGameObject;

/**
 *
 * @author johannes wikner
 */
public abstract class MoveableAbstract implements IMoveable, AbstractGameObject {

    protected int maxSpeed = Settings.getInstance().getSetting("maxSpeed");
    protected int acceleration = Settings.getInstance().getSetting("acceleration");
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    protected PhysicalBody body;

    public MoveableAbstract(Vector pos, Vector dir, Vector size, float height, float mass) {
        this.body = new PhysicalBody(this, new Vector(pos), new Vector(dir), new Vector(size), height, mass);
    }

    public PhysicalBody getBody() {
        return this.body;
    }

    /**
     * Sets the position of the moveable
     *
     * @param pos Vector with coordinates where to position the unit
     */
    public void setPosition(Vector pos) {
        this.body.place(pos);
        this.positionUpdated();
    }

    /**
     * Sets the position of the movable
     *
     * @param x New position in x-axis
     * @param y New positoin in y-axis
     * @param z New position in z-axis
     */
    public void setPosition(float x, float y, float z) {
        this.setPosition(new Vector(x, y, z));
    }

    /**
     * Sets our movable's direction.
     *
     * @param dir A vector holding the new direction
     */
    public void setDirection(Vector dir) {
        body.point(dir);
        this.directionUpdated();
    }

    /**
     * Sets our moveable's direction.
     *
     * @param x Direction in x-axis
     * @param y Direction in y-axis
     */
    public void setDirection(float x, float y, float z) {
        setDirection(new Vector(x, y, z));
    }

    public Vector getDirection() {
        return body.getDirection();
    }

    public Vector getPosition() {
        return body.getPosition();
    }

    /**
     *
     * @return The unit's acceleration
     */
    public int getAcceleration() {
        return this.acceleration;
    }

    /**
     *
     * @return Movable object's maximum speed
     */
    public float getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     *
     * @return The movable object's current speed
     */
    public float getSpeed() {
        return body.getSpeed();
    }

    public void hide() {
        System.out.println("LOOOOOOOOOOOOOOOOOOL");
        halt();
        this.maxSpeed = 0;
        setPosition(Vector.NONE_EXISTANT);
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
        if (Float.floatToIntBits(this.maxSpeed) != Float.floatToIntBits(other.maxSpeed)) {
            return false;
        }
        if (this.body != other.body && (this.body == null || !this.body.equals(other.body))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Float.floatToIntBits(this.maxSpeed);
        hash = 59 * hash + (this.body != null ? this.body.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "MoveableAbstract{" + ", maxSpeed=" + maxSpeed + ", body=" + body + '}';
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        body.addPropertyChangeListener(ls);
        this.pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.removePropertyChangeListener(ls);
    }

    protected void directionUpdated() {
        this.pcs.firePropertyChange("Updated Direction", null, this.getDirection());
    }

    protected void positionUpdated() {
        this.pcs.firePropertyChange("Updated Position", null, this.getPosition());
    }

    public abstract void announceRemoval();
}
