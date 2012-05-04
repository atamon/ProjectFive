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

    public JMEPhysicsHandler() {
        this.bulletAppState = new BulletAppState();
        this.bulletAppState.initialize(null, null);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(this);

    }
    
    public void createGround(Vector size, float height) {
        PhysicsRigidBody body = new PhysicsRigidBody(new BoxCollisionShape(Util.convertToMonkey3D(size).setY(height)), 0);
        body.setFriction(0);
        body.setPhysicsLocation(Vector3f.ZERO.setY(-1f));
        bulletAppState.getPhysicsSpace().addCollisionObject(body);
    }

    public void addToWorld(PhysicalBody object) {
        PhysicsRigidBody body = object.getBody();
        body.setGravity(Vector3f.ZERO);
        this.rigidBodies.add(body);
        this.bulletAppState.getPhysicsSpace().addCollisionObject(body);
    }

    public void removeFromWorld(PhysicalBody object) {
        this.bulletAppState.getPhysicsSpace().removeCollisionObject(object.getBody());
        rigidBodies.remove(object);
    }
    
    public void update(float tpf) {
        this.bulletAppState.getPhysicsSpace().update(tpf);
        this.bulletAppState.update(tpf);   
        
        for (PhysicsRigidBody body : rigidBodies) {
            IPhysical unit = (IPhysical)body.getUserObject();
            unit.getBody().updated();
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
