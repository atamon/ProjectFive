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
    public PhysType getPhysType();
    public Vector getSize();
    public float getMass();
    public Vector getPosition();
}