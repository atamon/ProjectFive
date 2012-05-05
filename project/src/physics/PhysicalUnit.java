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
    
    public PhysicalUnit(AbstractGameObject owner, Vector startPos, 
                        Vector startDir, Vector size, float mass) {
        Vector correctSize = new Vector(size);
        BoxCollisionShape shape = new BoxCollisionShape(Util.convertToMonkey3D(correctSize));
        body = new PhysicsRigidBody(shape, mass);
        body.setUserObject(owner);
        body.setPhysicsLocation(Util.convertToMonkey3D(startPos).setY(2.0f));
        body.setLinearVelocity(Util.convertToMonkey3D(startDir).normalize());
        body.setDamping(0, 0.1f);
        
        this.owner = owner;
        this.initSize = correctSize;
    }
    
    /**
     * Accelerates the body with an applied force.
     * @param tpf 
     */
    @Override
    public void accelerate(float tpf) {
        Vector3f force = body.getLinearVelocity();
        body.applyCentralForce(force.mult(owner.getAcceleration()));
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
        System.out.println(body.getLinearVelocity());
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
        return new Vector(Util.convertFromMonkey3D(body.getLinearVelocity().normalize()));
    }
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }
    
    @Override
    public void updated() {
        pcs.firePropertyChange("Physical Update", body.getPhysicsLocation(), 
                                       body.getLinearVelocity().normalize());
    }

    public int getOwnerID() {
        return this.owner.getID();
    }
    
}
