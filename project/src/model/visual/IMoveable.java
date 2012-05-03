/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.physics.IPhysical;
import model.physics.PhysType;
import model.tools.Vector;

/**
 *
 * @author Johannes
 */
public interface IMoveable extends IVisualisable, IPhysical {
    public void setPosition(Vector pos);
    public void setDirection(Vector dir);
    public Vector getDirection();

    public void update(float tpf);
    public Vector getVelocity();
    public PhysType getType();

    public void hide();
}
