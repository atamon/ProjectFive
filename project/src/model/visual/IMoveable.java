/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import physics.IPhysical;
import math.Vector;

/**
 *
 * @author Johannes
 */
public interface IMoveable extends IVisualisable, IPhysical {

    public void setDirection(Vector dir);

    public Vector getDirection();

    public void update(float tpf);

    public void hide();
}
