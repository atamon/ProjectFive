/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package physics;

import observable.IObservable;
import math.Vector;

/**
 *
 * @author jnes
 */
public interface IPhysicsHandler {
    public void addToWorld(IPhysicalBody obj);
    public void removeFromWorld(IPhysicalBody obj);
    public void update(float tpf);
}
