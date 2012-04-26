/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.physics;

import model.tools.IObservable;
import model.tools.Vector;

/**
 *
 * @author jnes
 */
public interface IPhysicsHandler extends IObservable {
    public void addToWorld(IPhysical obj, int owner);
    public void update(float tpf);
    public void setRigidPosition(final PhysType type, final int owner, final Vector pos);
    public Vector getRigidPosition(int owner);
    public float getRigidSpeed(int owner);
    public void setRigidVelocity(final PhysType type, int owner, Vector vel);
}
