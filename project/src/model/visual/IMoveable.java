/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import physics.IPhysicalModel;
import math.Vector;

/**
 *
 * @author Johannes
 */
public interface IMoveable extends IPhysicalModel {
    public void setPosition(Vector pos);
    public void setDirection(Vector dir);
    public Vector getPosition();
    public Vector getDirection();
    public void update(float tpf);
    public void hide();
    public void announceRemoval();
}
