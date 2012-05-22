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
import math.MonkeyConverter;

/**
 *
 * @author atamon
 */
public abstract class PhysicalAbstractBody implements IPhysicalBody {

    private PhysicsRigidBody body;
    private IPhysicalModel owner;
    private final Vector initSize;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public PhysicalAbstractBody(IPhysicalModel owner, Vector startPos,
            Vector startDir, Vector size, float mass) {
        BoxCollisionShape shape = new BoxCollisionShape(MonkeyConverter.convertToMonkey3D(size));
        body = new PhysicsRigidBody(shape, mass);

        place(startPos);
        point(startDir);

        body.setUserObject(owner);
        this.owner = owner;
        initSize = size;
    }

    /**
     * Accelerates the body with an applied force.
     *
     * @param tpf
     */
    @Override
    public void accelerate(float tpf) {
        float acceleration = tpf * owner.getAcceleration();
        body.applyCentralForce(getSteeringDirection().mult(acceleration).setY(0));
    }

    protected Vector3f getSteeringDirection() {
        Matrix3f rot = body.getPhysicsRotationMatrix();
        return rot.multLocal(new Vector3f(0, 0, 1));
    }
    
    @Override
    public void applyForce(Vector pos, Vector force) {
        body.applyForce(MonkeyConverter.convertToMonkey3D(force), MonkeyConverter.convertToMonkey3D(pos));
    }

    /**
     * Places the body at a given point in the physical world.
     *
     * @param pos
     */
    @Override
    public void place(Vector pos) {
        body.setPhysicsLocation(MonkeyConverter.convertToMonkey3D(pos));
    }

    /**
     * Points the body in a given direction in the physical world.
     *
     * @param dir
     */
    @Override
    public void point(Vector dir) {
        Quaternion quat = new Quaternion();
        quat.lookAt(MonkeyConverter.convertToMonkey3D(dir), Vector3f.UNIT_Y);
        body.setPhysicsRotation(quat);
    }

    @Override
    public void halt() {
        body.clearForces();
        body.setLinearVelocity(Vector3f.ZERO);
        body.setAngularVelocity(Vector3f.ZERO);
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
        return new Vector(MonkeyConverter.convertFromMonkey3D(body.getPhysicsLocation()));
    }

    @Override
    public Vector getDirection() {
        Matrix3f rot = body.getPhysicsRotationMatrix();
        return new Vector(MonkeyConverter.convertFromMonkey3D(rot.mult(new Vector3f(0, 0, 1))));
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }

    @Override
    public void updated() {
        pcs.firePropertyChange("Physical Update", body.getPhysicsLocation(),
                body.getPhysicsRotation());
    }
    
    public abstract void steer(float angle, float tpf);
}
