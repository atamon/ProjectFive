/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import com.jme3.bullet.objects.PhysicsRigidBody;
import math.Vector;

/**
 *
 * @author jnes
 */
public class PhysicalItem extends PhysicalAbstractBody {

    
    public PhysicalItem(IPhysicalModel owner, Vector startPos, Vector startDir, Vector size, float mass){
        super(owner, startPos, startDir, size, mass);

        
    }
    
    @Override
    public void steer(float angle, float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
