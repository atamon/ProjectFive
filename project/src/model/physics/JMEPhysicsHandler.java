/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.physics;


import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import model.tools.IObservable;
import model.tools.Vector;


/**
 * Holds the physicsworld
 * @author jnes
 */
public class JMEPhysicsHandler implements IPhysicsHandler, IObservable, PhysicsCollisionListener {

    private BulletAppState bulletAppState;
    public final static float SHAPE_HEIGHT = 1f;
    
    private List<PhysicsRigidBody> rigidBodies = 
            new LinkedList<PhysicsRigidBody>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public JMEPhysicsHandler() {
        this.bulletAppState = new BulletAppState();
        this.bulletAppState.initialize(null, null);
        this.bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(this);
        
    }
    public void addToWorld(IPhysical physModel, int owner){
        PhysicsRigidBody rBody = this.createRigidBody(physModel);
        rBody.setUserObject(owner);
        this.rigidBodies.add(rBody);
        this.bulletAppState.getPhysicsSpace().addCollisionObject(rBody);
    }
    
    public void update(float tpf){
        this.bulletAppState.getPhysicsSpace().update(tpf);
        this.bulletAppState.update(tpf);
    }
    
    private PhysicsRigidBody createRigidBody(IPhysical physModel){
        Vector3f size = new Vector3f(physModel.getSize().getX(),
                        SHAPE_HEIGHT, physModel.getSize().getY()).mult(3f);
        Vector3f pos = new Vector3f(physModel.getPosition().getX(),
                        0, physModel.getPosition().getY());
        
        PhysicsRigidBody body = new PhysicsRigidBody(
                new BoxCollisionShape(size),
                physModel.getMass());
        body.setPhysicsLocation(pos);
        return body;
    }

    /**
     * 
     * @param owner
     * @return
     * @throws NullPointerException owner doesn't have a RigidBody
     */
    public Vector getRigidPosition(int owner) {
        
        PhysicsRigidBody body = getRigidBoat(owner);

        
        Vector3f newPos = body.getPhysicsLocation();
        return new Vector(newPos.x,newPos.z);
        
    }
    public float getRigidSpeed(int owner){
        
        return getRigidBoat(owner).getLinearVelocity().length();
    }
    public void setRigidVelocity(PhysType type, int owner, Vector vel) {
        if(type == PhysType.BOAT){
            PhysicsRigidBody rBody = this.getRigidBoat(owner);
            rBody.setLinearVelocity(new Vector3f(vel.getX(),0,vel.getY()));
            
        }
    }
    
    public void setRigidPosition(PhysType type, int owner, Vector pos) {
        this.getRigidBoat(owner).setPhysicsLocation(new Vector3f(pos.getX(),0,pos.getY()));
    }
    
    private PhysicsRigidBody getRigidBoat(int owner){
        PhysicsRigidBody body = null;
        Iterator<PhysicsRigidBody> iterator = this.rigidBodies.iterator();
        while(iterator.hasNext()){
            body = iterator.next();
            if(body.getUserObject().equals(owner)){
                break;
            }
        }
        return body;
    }
    
    public void collision(PhysicsCollisionEvent event) {
        PhysicsRigidBody body1 = ((PhysicsRigidBody)event.getObjectA());
        PhysicsRigidBody body2 = ((PhysicsRigidBody)event.getObjectB());
        body1.applyCentralForce(body1.getLinearVelocity().mult(-1.0f));
        body2.applyCentralForce(body2.getLinearVelocity().mult(-1.0f));
        
        // The user object is the rigidbodys owner, model representation
        this.pcs.firePropertyChange("Collision Boat",body1.getUserObject(), body2.getUserObject());
        
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.removePropertyChangeListener(ls);
    }


}
