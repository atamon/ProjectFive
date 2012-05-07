/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import math.Vector;
import math.Direction;
import util.Util;

/**
 * A body to represent our models in the physical world
 *
 * @author atamon
 */
public class PhysicalUnit implements IPhysicalBody {

    private PhysicsRigidBody body;
    private IPhysicalModel owner;
    private final Vector initSize;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public PhysicalUnit(IPhysicalModel owner, Vector startPos,
            Vector startDir, Vector size, float mass) {
        Vector correctSize = new Vector(size);
        BoxCollisionShape shape = new BoxCollisionShape(Util.convertToMonkey3D(correctSize));
        body = new PhysicsRigidBody(shape, mass);

        place(startPos);
        point(startDir);

        this.owner = owner;
        this.initSize = correctSize;

        body.setFriction(0.5f);
        body.setDamping(0.5f, 0.5f);
        body.setUserObject(owner);
    }

    /**
     * Accelerates the body with an applied force.
     *
     * @param tpf
     */
    @Override
    public void accelerate(float tpf) {
        float acceleration = tpf * owner.getAcceleration();
        body.applyCentralForce(getSteeringDirection().mult(acceleration));
    }

    /**
     * Steers the body with an applied force in a crossproduct direction of
     * velocity.
     *
     * @param dir
     * @param tpf
     */
    @Override
    public void steer(Direction dir, float tpf) {
        // Update the physical objects rotation
        float angle = -1*dir.getValue()*tpf;
        Quaternion rot = body.getPhysicsRotation().mult(new Quaternion().fromAngleAxis(angle, Vector3f.UNIT_Y));
        body.setPhysicsRotation(rot);
        
        // Adjust the linear velocity to always travel in the boats direction
        float speed = body.getLinearVelocity().length();
        body.setLinearVelocity(getSteeringDirection().mult(speed));
    }
    
    private Vector3f getSteeringDirection() {
        Matrix3f rot = body.getPhysicsRotationMatrix();
        return rot.multLocal(new Vector3f(0, 0, 1));
    }

    /**
     * Places the body at a given point in the physical world.
     *
     * @param pos
     */
    @Override
    public void place(Vector pos) {
        body.setPhysicsLocation(Util.convertToMonkey3D(pos));
    }

    /**
     * Points the body in a given direction in the physical world.
     *
     * @param dir
     */
    @Override
    public void point(Vector dir) {
        Quaternion quat = new Quaternion();
        quat.lookAt(Util.convertToMonkey3D(dir), Vector3f.UNIT_Y);
        body.setPhysicsRotation(quat);
    }

    @Override
    public void halt() {
        body.clearForces();
    }

    public PhysicsRigidBody getBody() {
        return body;
    }

    @Override
    public float getSpeed() {
        return body.getLinearVelocity().length();
    }

    @Override
    public Vector getSize() {
        return initSize;
    }

    @Override
    public float getMass() {
        return body.getMass();
    }

    @Override
    public Vector getPosition() {
        return new Vector(Util.convertFromMonkey3D(body.getPhysicsLocation()));
    }

    @Override
    public Vector getDirection() {
        return new Vector(Util.convertFromMonkey3D(body.getLinearVelocity()));
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    @Override
    public void updated() {
        pcs.firePropertyChange("Physical Update", body.getPhysicsLocation(),
                body.getPhysicsRotation());
    }
}
