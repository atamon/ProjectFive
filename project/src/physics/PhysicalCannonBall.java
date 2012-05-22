/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import math.Vector;

/**
 * A class to represent a physical cannonball.
 */
public class PhysicalCannonBall extends PhysicalAbstractBody {
    
    private PhysicsRigidBody body;
    
    public PhysicalCannonBall(IPhysicalModel owner, Vector startPos, 
                        Vector startDir, Vector size, float mass, float speed) {
       
        super(owner, startPos, startDir, size, mass);
        
        body = getBody();
        Vector3f vec = getSteeringDirection();
        vec.normalizeLocal();
        body.setLinearVelocity(vec.mult(speed));
        body.setDamping(0, 0.1f);
    }

    @Override
    public void steer(float angle, float tpf) {
        // TODO, Add homing missilies! :D
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
