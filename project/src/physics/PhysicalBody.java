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
public class PhysicalBody {
    
    private PhysicsRigidBody body;
    private PhyciscalBodyOwner owner;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public PhysicalBody(PhyciscalBodyOwner owner, Vector startPos, Vector startDir, Vector size2D, float height, float mass) {
        Vector3f correctSize = Util.convertToMonkey3D(size2D).setY(height);
        BoxCollisionShape shape = new BoxCollisionShape(correctSize);
        body = new PhysicsRigidBody(shape, mass);
        body.setUserObject(owner);
        body.setPhysicsLocation(Util.convertToMonkey3D(startPos));
        body.setLinearVelocity(Util.convertToMonkey3D(startDir).normalize());
        body.clearForces();
        body.setDamping(0, 0.1f);
        
        this.owner = owner;
    }
    
    /**
     * Accelerates the body with an applied force.
     * @param tpf 
     */
    public void accelerate(float tpf) {
        Vector3f force = body.getLinearVelocity();
        body.applyCentralForce(force.mult(owner.getAcceleration()));
    }
    
    /**
     * Steers the body with an applied force in a crossproduct direction of velocity.
     * @param dir
     * @param tpf 
     */
    public void steer(Direction dir, float tpf) {
        Vector3f force = body.getLinearVelocity().cross(Vector3f.UNIT_Y).mult(dir.getValue());
        body.applyCentralForce(force.mult(5f));
    }
    
    /**
     * Places the body at a given point in the physical world.
     * @param pos 
     */
    public void place(Vector pos) {
        body.setPhysicsLocation(Util.convertToMonkey3D(pos));
    }
    
    /**
     * Points the body in a given direction in the physical world.
     * @param dir 
     */
    public void point(Vector dir) {
        if (body.getLinearVelocity().isUnitVector()) {
            body.setLinearVelocity(Util.convertToMonkey3D(dir));
        } else {
            float length = body.getLinearVelocity().length();
            body.setLinearVelocity(Util.convertToMonkey3D(dir).mult(length));
        }
        System.out.println(body.getLinearVelocity());
    }
    
    public void halt() {
        body.clearForces();
    }
    
    protected PhysicsRigidBody getBody() {
        return body;
    }
    
    public float getSpeed() {
        return body.getLinearVelocity().length();
    }
    
    public Vector getSize() {
        return Util.convertFromMonkey3D(body.getCollisionShape().getScale());
    }
    
    public float getMass() {
        return body.getMass();
    }
    
    public Vector getPosition() {
        System.out.println(body.getPhysicsLocation());
        return new Vector(Util.convertFromMonkey3D(body.getPhysicsLocation()));
    }
    
    public Vector getDirection() {
        return new Vector(Util.convertFromMonkey3D(body.getLinearVelocity().normalize()));
    }
    
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }
    
    public void updated() {
        pcs.firePropertyChange("Physical Update", body.getPhysicsLocation(), body.getLinearVelocity().normalize());
    }
}
