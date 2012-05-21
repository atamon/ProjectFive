/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.objects.PhysicsRigidBody;
import java.beans.PropertyChangeListener;
import math.Vector;
import observable.IObservable;

/**
 *
 * @author victorlindhe
 */
public interface IPhysicalBody extends IObservable {

    /**
     * Accelerates the body with an applied force.
     *
     * @param tpf
     */
    public void accelerate(float tpf);

    public void applyForce(Vector pos, Vector force);

    public void addPropertyChangeListener(PropertyChangeListener pcl);

    public void removePropertyChangeListener(PropertyChangeListener pcl);

    public Vector getDirection();

    public float getMass();

    public Vector getPosition();

    public Vector getSize();

    public float getSpeed();

    public void halt();

    public PhysicsRigidBody getBody();

    /**
     * Places the body at a given point in the physical world.
     *
     * @param pos
     */
    void place(Vector pos);

    /**
     * Points the body in a given direction in the physical world.
     *
     * @param dir
     */
    void point(Vector dir);

    /**
     * Steers the body with an applied force in a crossproduct direction of
     * velocity.
     *
     * @param dir
     * @param tpf
     */
    void steer(float angle, float tpf);

    void updated();
}