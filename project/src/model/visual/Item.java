/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.powerup.IPowerUp;
import model.tools.Vector;

/**
 * A class to represent an immutable Item.
 * @author Victor Lindhé
 */
public final class Item implements IVisualisable {
    private final Vector position;
    private final IPowerUp powerUp;
    private final Vector size = new Vector(0.5f,0.5f);
    /**
     * Creates an Item of a given type and at a given position.
     * @param PowerUp with features that will be passed on to a unit when he picks it up
     * @param position Vector
     */
    public Item(IPowerUp powerUp, Vector position) {
        this.position = new Vector(position.getX(), position.getY());
        this.powerUp = powerUp;
    }
    
    /**
     * Returns a copy of the position Vector.
     * @return Vector
     */
    public Vector getPosition() {
        return new Vector(this.position.getX(), this.position.getY());
    }
    
    /**
     * Returns the power-up of this Item.
     * @return ItemTypes
     */
    public IPowerUp getPowerUp() {
        return this.powerUp; // immutable class => no copy necessary 
    }

    public Vector getSize() {
        return this.size;
    }

    public void removeFromView() {
        
    }
}
