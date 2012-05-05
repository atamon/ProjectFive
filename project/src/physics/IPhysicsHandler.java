/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import model.tools.IObservable;
import math.Vector;

/**
 *
 * @author jnes
 */
public interface IPhysicsHandler {
    public void addToWorld(PhysicalGameObject obj);
    public void removeFromWorld(PhysicalGameObject obj);
    public void update(float tpf);
}
