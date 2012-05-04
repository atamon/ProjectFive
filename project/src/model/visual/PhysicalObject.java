/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import model.tools.Settings;
import math.Vector;
import util.Util;

/**
 *
 * @author johannes wikner
 */
public abstract class PhysicalObject implements IMoveable {

    protected PhysicsRigidBody body;
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public PhysicalObject(PhysicsRigidBody rBody) {
        this.body = rBody;
    }

    /**
     * Sets the position of the moveable
     *
     * @param pos Vector with coordinates where to position the unit
     */
    public void place(Vector pos) {
        this.body.setPhysicsLocation(Util.convertToMonkey3D(pos));
        this.positionUpdated();
    }

    /**
     * Sets the position of the movable
     *
     * @param x New position in x-axis
     * @param y New positoin in y-axis
     */
    public void place(float x, float y) {
        this.place(new Vector(x, y));
    }

//    public Vector getVelocity() {
//        final float x = dir.getX();
//        final float y = dir.getY();
//        return new Vector(x * speed, y * speed);
//    }

    /**
     * Sets our movable's direction.
     *
     * @param dir A vector holding the new direction
     */
    public void setVelocity(Vector dir) {
        this.body.setLinearVelocity(util.Util.convertToMonkey3D(dir));
        this.directionUpdated();
    }

    /**
     * Sets our moveable's direction.
     *
     * @param x Direction in x-axis
     * @param y Direction in y-axis
     */
    public void setVelocity(float x, float y) {
        this.setVelocity(new Vector(x, y));
    }

    /**
     *
     * @return A vector describing the position of the movable object
     */
    public Vector getPosition() {
        return util.Util.convertFromMonkey3D(body.getPhysicsLocation());
    }

    /**
     *
     * @return The movable object's current speed and moving-direction
     */
    public Vector getVelocity() {
        return util.Util.convertFromMonkey3D(body.getLinearVelocity());
    }

    public abstract void hasCrashed(PhysicalObject collidedObj);

    protected void directionUpdated() {
        this.pcs.firePropertyChange("Updated Direction", null, this.getDirection());
    }

    protected void positionUpdated() {
        this.pcs.firePropertyChange("Updated Position", null, this.getPosition());
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.removePropertyChangeListener(ls);
    }
}
