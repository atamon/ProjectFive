/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeSupport;
import model.powerup.IPowerUp;
import math.Vector;
import model.settings.Settings;
import physics.*;

/**
 * A class to represent an immutable Item.
 *
 * @author Victor Lindhé
 */
public final class Item extends Bottle {

    private final IPowerUp powerUp;
    
    /**
     * Creates an Item of a given type and at a given position.
     *
     * @param PowerUp with features that will be passed on to a unit when he
     * picks it up
     * @param position Vector
     */
    public Item(final IPowerUp powerUp, final Vector position) {
        super(position);
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
    public void collidedWith(final ICollideable obj, final float objImpactSpeed) {
        if(obj instanceof Unit){
            this.announceRemoval();
            ((Unit)obj).applyPowerUp(powerUp);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.powerUp != other.powerUp && (this.powerUp == null || !this.powerUp.equals(other.powerUp))) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + (powerUp != null ? powerUp.hashCode() : 0);
        return hash + super.hashCode();
    }
    
    
}