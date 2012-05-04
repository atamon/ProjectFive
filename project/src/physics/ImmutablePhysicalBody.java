/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import math.Vector;
import util.Util;

/**
 *
 * @author atamon
 */
public class ImmutablePhysicalBody {
    
    private PhysicsRigidBody body;
    
    public ImmutablePhysicalBody(AbstractGameObject owner, Vector startPos, Vector startDir, Vector size2D, float height) {
        Vector3f correctSize = Util.convertToMonkey3D(size2D);
        correctSize.setY(height);
        BoxCollisionShape shape = new BoxCollisionShape(correctSize);
        body = new PhysicsRigidBody(shape, 10f);
        body.setUserObject(owner);
        body.setPhysicsLocation(Util.convertToMonkey3D(startPos));
        body.setLinearVelocity(Util.convertToMonkey3D(startDir).normalize());
        body.clearForces();
        body.setDamping(0, 0.1f);
    }
    
}
