/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import math.Vector;
import math.Direction;
import util.Util;

/**
 * A body to represent our models in the physical world
 * @author atamon
 */
public class PhysicalUnit implements PhysicalGameObject {
    
    private PhysicsRigidBody body;
    private AbstractGameObject owner;
    private final Vector initSize;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Vector3f oldDirection;
    
    public PhysicalUnit(AbstractGameObject owner, Vector startPos, 
                        Vector startDir, Vector size, float mass) {
        Vector correctSize = new Vector(size);
        BoxCollisionShape shape = new BoxCollisionShape(Util.convertToMonkey3D(correctSize));
        body = new PhysicsRigidBody(shape, mass);
        
        body.setPhysicsLocation(Util.convertToMonkey3D(startPos));
        body.setLinearVelocity(Util.convertToMonkey3D(startDir));
        
        this.oldDirection = body.getLinearVelocity();
        this.owner = owner;
        this.initSize = correctSize;
        
        body.setUserObject(owner);
    }
    
    /**
     * Accelerates the body with an applied force.
     * @param tpf 
     */
    @Override
    public void accelerate(boolean isAccelerating, float tpf) {
        Vector3f force = body.getLinearVelocity();
        float acceleration = tpf*owner.getAcceleration();
        acceleration = isAccelerating ? acceleration : acceleration*-1;
        body.applyCentralForce(force.mult(acceleration));
    }
    
    /**
     * Steers the body with an applied force in a crossproduct direction of velocity.
     * @param dir
     * @param tpf 
     */
    @Override
    public void steer(Direction dir, float tpf) {
        Vector3f force = body.getLinearVelocity().cross(Vector3f.UNIT_Y).mult(dir.getValue());
        body.applyCentralForce(force.mult(5f));
    }
    
    /**
     * Places the body at a given point in the physical world.
     * @param pos 
     */
    @Override
    public void place(Vector pos) {
        body.setPhysicsLocation(Util.convertToMonkey3D(pos));
    }
    
    /**
     * Points the body in a given direction in the physical world.
     * @param dir 
     */
    @Override
    public void point(Vector dir) {
        if (body.getLinearVelocity().isUnitVector()) {
            body.setLinearVelocity(Util.convertToMonkey3D(dir));
        } else {
            float length = body.getLinearVelocity().length();
            body.setLinearVelocity(Util.convertToMonkey3D(dir).mult(length));
        }
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
        if(body.getLinearVelocity().length() < 1.0f) {
            body.setLinearVelocity(this.oldDirection);
        }
        System.out.println("length of velo: "+body.getLinearVelocity().length());
        pcs.firePropertyChange("Physical Update", body.getPhysicsLocation(), 
        body.getLinearVelocity());
    }
    
}
