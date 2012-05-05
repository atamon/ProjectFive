/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.List;
import math.Vector;
import util.Util;

/**
 * Holds the physicsworld
 *
 * @author jnes
 */
public class JMEPhysicsHandler implements PhysicsCollisionListener {

    private BulletAppState bulletAppState;
    private List<PhysicsRigidBody> rigidBodies =
            new LinkedList<PhysicsRigidBody>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private PhysicsRigidBody ground;
   
    public JMEPhysicsHandler() {
        this.bulletAppState = new BulletAppState();
        this.bulletAppState.initialize(null, null);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }
    
    /**
     * Creates the ground in the physics world.
     * Because the engine sees the Vector size as half extents, we divide by 2.
     * @param size 
     */
    public void createGround(Vector size) {
        Vector position = new Vector(size);
        position.mult(0.5f);
        this.ground = new PhysicsRigidBody(new BoxCollisionShape(Util.convertToMonkey3D(size).mult(0.5f)), 0);
        ground.setPhysicsLocation(Util.convertToMonkey3D(position));
        ground.setFriction(0);
        bulletAppState.getPhysicsSpace().addCollisionObject(ground);
    }

    public void addToWorld(PhysicalGameObject object) {
        PhysicsRigidBody body = object.getBody();
        body.addCollideWithGroup(object.getOwnerID());
        body.setGravity(new Vector3f(0,-1,0));
        this.rigidBodies.add(body);
        this.bulletAppState.getPhysicsSpace().addCollisionObject(body);
    }

    public void removeFromWorld(PhysicalGameObject object) {
        this.bulletAppState.getPhysicsSpace().removeCollisionObject(object.getBody());
        rigidBodies.remove(object);
    }
    
    public void update(float tpf) {
        this.bulletAppState.getPhysicsSpace().update(tpf);
        this.bulletAppState.update(tpf);   
        
        for (PhysicsRigidBody body : rigidBodies) {
            IPhysical unit = (IPhysical)body.getUserObject();
            unit.getGameObject().updated();
        }
    }

    public void collision(PhysicsCollisionEvent event) {
        System.out.println("COLLIIIIIIIIIISSSSIOOOOOOOOOON!!!!!!");
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.removePropertyChangeListener(ls);
    }

}
