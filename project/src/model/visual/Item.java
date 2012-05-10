/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.powerup.IPowerUp;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import math.Vector;
import model.tools.IObservable;
import physics.IPhysicalModel;

/**
 * A class to represent an immutable Item.
 *
 * @author Victor Lindhé
 */
public final class Item extends MoveableAbstract {

    private final Vector position;
    private final IPowerUp powerUp;
    private final Vector size = new Vector(0.5f, 0.5f, 0.5f);
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Creates an Item of a given type and at a given position.
     *
     * @param PowerUp with features that will be passed on to a unit when he
     * picks it up
     * @param position Vector
     */
    public Item(IPowerUp powerUp, Vector position) {
        this.position = new Vector(position);
        this.powerUp = powerUp;
    }

    /**
     * Returns the power-up of this Item.
     *
     * @return ItemTypes
     */
    public IPowerUp getPowerUp() {
        return this.powerUp; // immutable class => no copy necessary 
    }

    @Override
    public void announceRemoval() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void collidedWith(IPhysicalModel obj, float objImpactSpeed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(float tpf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}