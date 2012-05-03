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
        
        PhysicsRigidBody body = getRigid(physModel);

        
        Vector3f newPos = body.getPhysicsLocation();
        return new Vector(newPos.x,newPos.z);
        
    }
    
    public float getRigidSpeed(IPhysical physModel){
        return getRigid(physModel).getLinearVelocity().length();
    }
    
    public void setRigidVelocity(IPhysical physModel, Vector vel) {
        PhysicsRigidBody rBody = this.getRigid(physModel);
        rBody.setLinearVelocity(new Vector3f(vel.getX(),0,vel.getY()));
    }
    

    public Vector getRigidDirection(IPhysical model) {
        Vector3f dir3f = getRigid(model).getLinearVelocity().normalize();
        return new Vector(dir3f.getX(), dir3f.getZ());
    }
    public void setRigidPosition(IPhysical physModel, Vector pos) {
        this.getRigid(physModel).setPhysicsLocation(new Vector3f(pos.getX(),0,pos.getY()));
    }
    
    private PhysicsRigidBody getRigid(IPhysical physModel){
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
        final PhysicsRigidBody body1 = ((PhysicsRigidBody)event.getObjectA());
        final PhysicsRigidBody body2 = ((PhysicsRigidBody)event.getObjectB());
        
        final IPhysical phys1 = (IPhysical)body1.getUserObject();
        final IPhysical phys2 = (IPhysical)body2.getUserObject();
        
        final PhysType type1 = phys1.getType();
        final PhysType type2 = phys2.getType();
        
        String propertyName = "";
        
        if(type1==type2){
            if(type1==PhysType.CANNONBALL){
                propertyName="Collision CannonBalls";
                this.bulletAppState.getPhysicsSpace().removeCollisionObject(body1);
                this.bulletAppState.getPhysicsSpace().removeCollisionObject(body2);
            } else {
                propertyName="Collision Boats";    
            }
        } else {
            System.out.println("It's \"removed\" ");
            if(type1 == PhysType.CANNONBALL && phys1.getOwner() != phys2.getOwner()){ // Friendlyfire Off shouldn't be determined here though.....
                propertyName="Collision CannonBallBoat";
                this.bulletAppState.getPhysicsSpace().removeCollisionObject(body1);
            } else if(phys1.getOwner() != phys2.getOwner()) {
                propertyName="Collision BoatCannonBall";
                this.bulletAppState.getPhysicsSpace().removeCollisionObject(body2);
            }
        }
        
        if (!propertyName.isEmpty()) {
            this.pcs.firePropertyChange(propertyName, phys1, phys2 );
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.removePropertyChangeListener(ls);
    }

    public void setRigidForce(IPhysical model, Vector dir, float speed) {

        this.getRigid(model).getPhysicsLocation().y = 0;
        Vector3f force = new Vector3f(dir.getX(), 0, dir.getY());
        force.mult(speed);
        this.getRigid(model).applyCentralForce(force);

    }

    public void remove(IPhysical phys) {
        this.bulletAppState.getPhysicsSpace().removeCollisionObject(this.getRigid(phys));
    }


}
