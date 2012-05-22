package physics;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import java.util.LinkedList;
import java.util.List;
import math.Vector;
import math.MonkeyConverter;

/**
 * Holds the physicsworld
 *
 * @author jnes
 */
public class JMEPhysicsHandler implements PhysicsCollisionListener, IPhysicsHandler {

    private BulletAppState bulletAppState = new BulletAppState();
    private PhysicsSpace physicsSpace;
    private List<PhysicsRigidBody> rigidBodies =
            new LinkedList<PhysicsRigidBody>();
    private PhysicsRigidBody ground;

    public JMEPhysicsHandler() {
        bulletAppState.initialize(null, null);
        physicsSpace = bulletAppState.getPhysicsSpace();
        physicsSpace.addCollisionListener(this);
    }

    /**
     * Creates the ground in the physics world. Because the engine sees the
     * Vector size as half extents, we divide by 2.
     *
     * @param size
     */
    @Override
    public void createGround(Vector size, ICollideable model) {
        Vector position = new Vector(size);
        position.mult(0.5f);
        ground = new PhysicsRigidBody(new BoxCollisionShape(MonkeyConverter.convertToMonkey3D(size).mult(0.5f)), 0);
        ground.setUserObject(model);
        ground.setPhysicsLocation(MonkeyConverter.convertToMonkey3D(position));
        physicsSpace.addCollisionObject(ground);
    }

    @Override
    public void addToWorld(IPhysicalBody object) {
        PhysicsRigidBody body = object.getBody();
        body.setGravity(new Vector3f(0, -1, 0));
        rigidBodies.add(body);
        physicsSpace.addCollisionObject(body);
    }

    @Override
    public void removeFromWorld(IPhysicalBody object) {
        physicsSpace.removeCollisionObject(object.getBody());
        rigidBodies.remove(object.getBody());
    }

    @Override
    public void update(float tpf) {
        physicsSpace.update(tpf);
        bulletAppState.update(tpf);
        for (PhysicsRigidBody body : rigidBodies) {
            IPhysicalModel unit = (IPhysicalModel) body.getUserObject();
            unit.getPhysicalObject().updated();
        }
    }

    public void collision(PhysicsCollisionEvent event) {
        if (event.getObjectA().getUserObject() == null || event.getObjectB().getUserObject() == null) {
            return;
        }

        ICollideable modelA = ((ICollideable) (event.getObjectA().getUserObject()));
        ICollideable modelB = ((ICollideable) (event.getObjectB().getUserObject()));

        PhysicsCollisionObject objA = event.getObjectA();
        PhysicsCollisionObject objB = event.getObjectB();

        PhysicsRigidBody bodyA = (PhysicsRigidBody) event.getObjectA();
        PhysicsRigidBody bodyB = (PhysicsRigidBody) event.getObjectB();
        
        Vector3f collision = bodyA.getLinearVelocity().cross(Vector3f.UNIT_Y).mult(event.getAppliedImpulse());

        boolean isGrinding = event.getLifeTime() > 1;
        float impulseA = isGrinding ? 0 : collision.cross(bodyA.getLinearVelocity()).length();
        float impulseB = isGrinding ? 0 : collision.cross(bodyB.getLinearVelocity()).length();

        
        modelA.collidedWith(modelB, impulseB);
        modelB.collidedWith(modelA, impulseA);
    }
}
