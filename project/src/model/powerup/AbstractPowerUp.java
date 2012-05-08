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
    protected float retardation;
    protected int acceleration;
    protected int steerAngle;
    protected int hitPoints;
    protected int hitPointsMax;

    protected boolean isActive = true;
    
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
    public float getRetardation() {
        return retardation;
    }

    @Override
    public int getSteerAngle() {
        return steerAngle;
    }
    
    public boolean isActive() {
        return isActive;
    }

}
