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
import model.physics.IPhysical;
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
    public void addToWorld(IPhysical physModel){
        PhysicsRigidBody rBody = this.createRigidBody(physModel);
        rBody.setUserObject(physModel);
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
     * @param physModel
     * @return
     * @throws NullPointerException physModel doesn't have a RigidBody
     */
    public Vector getRigidPosition(IPhysical physModel) {
        
        PhysicsRigidBody body = getRigidBoat(physModel);

        
        Vector3f newPos = body.getPhysicsLocation();
        return new Vector(newPos.x,newPos.z);
        
    }
    
    public float getRigidSpeed(IPhysical physModel){
        return getRigidBoat(physModel).getLinearVelocity().length();
    }
    
    public void setRigidVelocity(PhysType type, IPhysical physModel, Vector vel) {
        if(type == PhysType.BOAT){
            PhysicsRigidBody rBody = this.getRigidBoat(physModel);
            rBody.setLinearVelocity(new Vector3f(vel.getX(),0,vel.getY()));
            
        }
    }
    
    public void setRigidPosition(PhysType type, IPhysical physModel, Vector pos) {
        this.getRigidBoat(physModel).setPhysicsLocation(new Vector3f(pos.getX(),0,pos.getY()));
    }
    
    private PhysicsRigidBody getRigidBoat(IPhysical physModel){
        PhysicsRigidBody body = null;
        Iterator<PhysicsRigidBody> iterator = this.rigidBodies.iterator();
        while(iterator.hasNext()){
            body = iterator.next();
            if(body.getUserObject().equals(physModel)){
                break;
            }
        }
        return body;
    }
    
    public void collision(PhysicsCollisionEvent event) {
        PhysicsRigidBody body1 = ((PhysicsRigidBody)event.getObjectA());
        PhysicsRigidBody body2 = ((PhysicsRigidBody)event.getObjectB());
        
        Vector body1Dir = new Vector(body1.getLinearVelocity().getX(), 
                                     body1.getLinearVelocity().getZ());
        Vector body2Dir = new Vector(body2.getLinearVelocity().getX(), 
                                     body2.getLinearVelocity().getZ());
        
        if (((IPhysical)body1.getUserObject()).getType() == PhysType.CANNONBALL ||
            ((IPhysical)body2.getUserObject()).getType() == PhysType.CANNONBALL) {
            this.pcs.firePropertyChange("Ball Collision", body1.getUserObject(), body2.getUserObject());
        } else if (!(body2Dir.equals(Vector.ZERO_VECTOR) || body1Dir.equals(Vector.ZERO_VECTOR))) {
            this.pcs.firePropertyChange("Collision", body1Dir, body1.getUserObject());
            this.pcs.firePropertyChange("Collision", body2Dir, body2.getUserObject());
        }
        
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.removePropertyChangeListener(ls);
    }

    public void setRigidForce(PhysType type, IPhysical model, Vector dir, float speed) {
        this.getRigidBoat(model).getPhysicsLocation().y = 0;
        Vector3f force = new Vector3f(dir.getX(), 0, dir.getY());
        force.mult(speed);
        this.getRigidBoat(model).applyCentralForce(force);
    }


}
