/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeSupport;
import model.powerup.IPowerUp;
import math.Vector;
import physics.*;

/**
 * A class to represent an immutable Item.
 *
 * @author Victor Lindhé
 */
public final class Item extends MoveableAbstract {

    private final IPowerUp powerUp;
    private final Vector size = new Vector(2f, 2f, 2f);
    private float lifeTime=10;
    private final Vector pointingDir = new Vector(1,1,1);
    private final float mass = 0.1f;
    private final IPhysicalBody body;
    private final PropertyChangeSupport pcs;
    
    /**
     * Creates an Item of a given type and at a given position.
     *
     * @param PowerUp with features that will be passed on to a unit when he
     * picks it up
     * @param position Vector
     */
    public Item(final IPowerUp powerUp, final Vector position) {
        this.powerUp = powerUp;
        
        this.body = new PhysicalItem(this, position, pointingDir, size, mass);
        super.setBody(body);
        this.pcs = super.getPropertyChangeSupport();
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
        this.pcs.firePropertyChange("Item Removed", null, this);
    }

    @Override
    public void collidedWith(final ICollideable obj, final float objImpactSpeed) {
        if(obj instanceof Unit){
            this.announceRemoval();
            ((Unit)obj).applyPowerUp(powerUp);
        }
    }

    public float getLifeTime(){
        return this.lifeTime;
    }
    public void update(final float tpf) {
        
        this.lifeTime -= tpf;
        if(this.lifeTime <= 0){
            this.announceRemoval();
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
        if (this.size != other.size && (this.size == null || !this.size.equals(other.size))) {
            return false;
        }
        if (Float.floatToIntBits(this.lifeTime) != Float.floatToIntBits(other.lifeTime)) {
            return false;
        }
        if (this.pointingDir != other.pointingDir && (this.pointingDir == null || !this.pointingDir.equals(other.pointingDir))) {
            return false;
        }
        if (Float.floatToIntBits(this.mass) != Float.floatToIntBits(other.mass)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.powerUp != null ? this.powerUp.hashCode() : 0);
        hash = 79 * hash + (this.size != null ? this.size.hashCode() : 0);
        hash = 79 * hash + Float.floatToIntBits(this.lifeTime);
        hash = 79 * hash + (this.pointingDir != null ? this.pointingDir.hashCode() : 0);
        hash = 79 * hash + Float.floatToIntBits(this.mass);
        return hash;
    }

    @Override
    public String toString() {
        return "Item{" + "powerUp=" + powerUp + ", size=" + size + ", lifeTime=" + lifeTime + ", pointingDir=" + pointingDir + ", mass=" + mass + '}';
    }
    
    
}