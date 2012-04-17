/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.physics;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.SphereShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.vecmath.Vector3f;
import model.visual.MoveableAbstract;

/**
 *
 * @author victorlindhe
 */
public class PhysicsModel {
    private DiscreteDynamicsWorld collisionWorld;
    private DefaultCollisionConfiguration collisionConfiguration;
    private AxisSweep3 broadPhase;
    private CollisionDispatcher collisionDispatcher;
    private SequentialImpulseConstraintSolver solver;
    private Map<RigidBody, MoveableAbstract> abstracts;
    
    public PhysicsModel() {
        this.collisionConfiguration = new DefaultCollisionConfiguration();
        this.collisionDispatcher = new CollisionDispatcher(collisionConfiguration);
        
        Vector3f worldAabbMin = new Vector3f(-10000, -10000, -10000);
        Vector3f worldAabbMax = new Vector3f(10000, 10000, 10000);
        
        this.broadPhase = new AxisSweep3(worldAabbMin, worldAabbMax);
        this.solver = new SequentialImpulseConstraintSolver();
        this.collisionWorld = new DiscreteDynamicsWorld(collisionDispatcher, 
                                                        broadPhase, 
                                                        solver, 
                                                        collisionConfiguration);
        this.abstracts = new HashMap<RigidBody, MoveableAbstract>();
    }
    
    public void add(MoveableAbstract moveable) {
        CollisionShape sphere = new SphereShape(1.0f);
        DefaultMotionState motionState = new DefaultMotionState();
        
        Transform trans = new Transform();
        trans.setIdentity();
        trans.origin.set(moveable.getPosition().getX(), 0, 
                         moveable.getPosition().getY());
        motionState.setWorldTransform(trans);
        
        RigidBody body = new RigidBody(1.0f, motionState, sphere);
        
        this.abstracts.put(body, moveable);
        this.collisionWorld.addRigidBody(body);
    }
    
    public void update(float tpf) {
        this.collisionWorld.updateActions(tpf);
    }
}