package physics;

import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import math.Vector;

/**
 * A class to represent a physical unit.
 */
public class PhysicalUnit extends PhysicalAbstractBody {
    
    private PhysicsRigidBody body;

    public PhysicalUnit(IPhysicalModel owner, Vector startPos,
            Vector startDir, Vector size, float mass) {
        super(owner, startPos, startDir, size, mass);

        body = getBody();
        body.setFriction(0.1f);
        body.setDamping(0.2f, 0.6f);
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
        body.setLinearVelocity(getSteeringDirection().mult(speed).setY(0));
    }
    
    /**
     * Returns true if the unit is able to navigate in the physics space
     * @return True if the unit is pointing parallell to the battlefield without a rotation
     */
    public boolean canNavigate() {
        Quaternion rot = body.getPhysicsRotation();
        return !(Math.abs(rot.getZ()) > 0.35 || Math.abs(rot.getX()) > 0.35);
    }
            
}
