/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.beans.PropertyChangeListener;
import math.Vector;
import math.Direction;
import util.Util;

/**
 * A body to represent our models in the physical world
 *
 * @author atamon
 */
public class PhysicalUnit extends PhysicalAbstractBody {
    
    private PhysicsRigidBody body;

    public PhysicalUnit(IPhysicalModel owner, Vector startPos,
            Vector startDir, Vector size, float mass) {
        super(owner, startPos, startDir, size, mass);

        body = getBody();
        body.setFriction(0);
        body.setDamping(0.7f, 0.7f);
    }

    /**
     * Steers the body with an applied force in a crossproduct direction of
     * velocity.
     *
     * @param dir
     * @param tpf
     */
    public void steer(float angle, float tpf) {

        // Update the physical objects rotation
        float corrAngle = -1 * angle * tpf;
        float currentSpeed = getSpeed();
        if (currentSpeed < 1) {
            corrAngle = corrAngle*currentSpeed*currentSpeed;
        }
        Quaternion rot = body.getPhysicsRotation().mult(new Quaternion().fromAngleAxis(corrAngle, Vector3f.UNIT_Y));
        body.setPhysicsRotation(rot);

        // Adjust the linear velocity to always travel in the boats direction
        float speed = body.getLinearVelocity().length();
        body.setLinearVelocity(getSteeringDirection().mult(speed));
    }
}
