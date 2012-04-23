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
public interface IPhysicsHandler {
    public void addToWorld(IPhysical obj, int owner);
    public void update(float tpf);
    
    public Vector moveTo(Vector pos, Vector dir, float speed, int owner);
}
