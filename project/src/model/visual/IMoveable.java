/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeListener;
import physics.IPhysicalModel;
import math.Vector;
import observable.IObservable;

/**
 *
 * @author Johannes
 */
public interface IMoveable extends IPhysicalModel, IObservable {
    public void setPosition(Vector pos);
    public void setDirection(Vector dir);
    public Vector getPosition();
    public Vector getDirection();
    public void update(float tpf);
    public void announceShow();
    public void announceHide();
    public void announceRemoval();
}
