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
import java.util.HashMap;
import java.util.Map;
import model.tools.Vector;


/**
 * Holds the physicsworld
 * @author jnes
 */
public class JMEPhysicsHandler implements IPhysicsHandler, PhysicsCollisionListener {

    private BulletAppState bulletAppState;
    public final static float SHAPE_HEIGHT = 1f;
    private Map<Integer, PhysicsRigidBody> RigidBodies = 
            new HashMap<Integer, PhysicsRigidBody>();
    
    public JMEPhysicsHandler() {
        this.bulletAppState = new BulletAppState();
        this.bulletAppState.initialize(null, null);
        this.bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);
        this.bulletAppState.getPhysicsSpace().addCollisionListener(this);
        
    }
    public void addToWorld(IPhysical physModel, int owner){
        PhysicsRigidBody rBody = this.createRigidBody(physModel);
        RigidBodies.put(owner, rBody);
        this.bulletAppState.getPhysicsSpace().addCollisionObject(rBody);
    }
    
    public void update(float tpf){
        this.bulletAppState.update(tpf);
        this.bulletAppState.getPhysicsSpace().update(tpf);
        
            
    }
    
    private PhysicsRigidBody createRigidBody(IPhysical physModel){
        Vector3f size = new Vector3f(physModel.getSize().getX(),
                        SHAPE_HEIGHT, physModel.getSize().getY());
        
        Vector3f pos = new Vector3f(physModel.getPosition().getX(),
                        0, physModel.getPosition().getY());
        
        PhysicsRigidBody body = new PhysicsRigidBody(
                new BoxCollisionShape(size),
                physModel.getMass());
        
        body.setPhysicsLocation(pos);
        return body;
    }

    public Vector moveTo(Vector pos, Vector dir, float speed, int owner) {
        
        PhysicsRigidBody body = this.RigidBodies.get(owner);
        
        Vector3f vel = new Vector3f(dir.getX()*speed, 0, dir.getY()*speed);
        body.setLinearVelocity(vel);
        
        body.setPhysicsLocation(new Vector3f(pos.getX(), 0, pos.getY()));
        Vector3f newPos = body.getPhysicsLocation();
        return new Vector(newPos.x,newPos.z);
    }

    public void collision(PhysicsCollisionEvent event) {
        System.out.println("HELLO! :D"+event.getPositionWorldOnA());
    }
}
