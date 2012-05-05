/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.objects.PhysicsRigidBody;
import java.beans.PropertyChangeListener;
import math.Direction;
import math.Vector;

/**
 *
 * @author victorlindhe
 */
public interface PhysicalGameObject {

    /**
     * Accelerates the body with an applied force.
     * @param tpf
     */
    void accelerate(float tpf);

    void addPropertyChangeListener(PropertyChangeListener pcl);

    Vector getDirection();

    float getMass();

    Vector getPosition();

    Vector getSize();

    float getSpeed();

    void halt();
    
    PhysicsRigidBody getBody();

    /**
     * Places the body at a given point in the physical world.
     * @param pos
     */
    void place(Vector pos);

    /**
     * Points the body in a given direction in the physical world.
     * @param dir
     */
    void point(Vector dir);

    /**
     * Steers the body with an applied force in a crossproduct direction of velocity.
     * @param dir
     * @param tpf
     */
    void steer(Direction dir, float tpf);

    void updated();
    
    int getOwnerID();
    
}
