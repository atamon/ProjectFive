/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.powerup;

/**
 *
 * @author jnes
 */
public abstract class AbstractPowerUp implements IPowerUp {
    
    protected float lifeTime;
    protected float maxSpeed;
    protected int acceleration;
    protected int retardation;
    protected int steerAngle;
    protected int hitPoints;
    protected int hitPointsMax;
    protected int damage;

    protected boolean active = true;
    
    @Override
    public int getAcceleration() {
        return acceleration;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

    @Override
    public int getHitPointsMax() {
        return hitPointsMax;
    }

    @Override
    public float getLifeTime() {
        return lifeTime;
    }

    @Override
    public float getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public int getRetardation() {
        return retardation;
    }

    @Override
    public int getSteerAngle() {
        return steerAngle;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(final boolean active) {
        this.active = active;
    }
    
}
