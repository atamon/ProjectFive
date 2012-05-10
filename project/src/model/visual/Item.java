/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.powerup.IPowerUp;
import math.Vector;
import physics.IPhysicalModel;
import physics.PhysicalItem;

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
    /**
     * Creates an Item of a given type and at a given position.
     *
     * @param PowerUp with features that will be passed on to a unit when he
     * picks it up
     * @param position Vector
     */
    public Item(final IPowerUp powerUp, final Vector position) {
        this.body = new PhysicalItem(this, position, pointingDir, size, mass);
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
        this.pcs.firePropertyChange("Item Removed", null, this);
    }

    @Override
    public void collidedWith(final IPhysicalModel obj, final float objImpactSpeed) {
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
            return;
        }
    }
}