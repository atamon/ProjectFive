/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.physics;

import model.tools.IObservable;
import model.tools.Vector;
import model.visual.Unit;

/**
 *
 * @author jnes
 */
public interface IPhysicsHandler extends IObservable {
    public void addToWorld(IPhysical obj);
    public void update(float tpf);
    public void setRigidPosition(final PhysType type, final IPhysical model, final Vector pos);
    public Vector getRigidPosition(final IPhysical model);
    public float getRigidSpeed(final IPhysical model);
    public void setRigidVelocity(final PhysType type, final IPhysical model, Vector vel);
    public void setRigidForce(final PhysType type, final IPhysical model, final Vector dir, final float speed);

    public Vector getRigidDirection(IPhysical model);
}
