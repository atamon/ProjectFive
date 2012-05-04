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
    public void addToWorld(PhysicalBody obj);
    public void removeFromWorld(PhysicalBody obj);
    public void update(float tpf);
}
