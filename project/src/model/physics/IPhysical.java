/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.physics;

import model.tools.Vector;


/**
 *
 * @author jnes
 */
public interface IPhysical {
    public Vector getSize();
    public float getMass();
    public Vector getPosition();
    public Vector getDirection();
    public PhysType getType();
    public int getOwner();
    public Vector getVelocity();
    public float getSpeed();
    public void update(float tpf);
}