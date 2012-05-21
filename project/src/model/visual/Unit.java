package model.visual;

import java.beans.PropertyChangeSupport;
import math.Direction;
import math.Vector;
import model.powerup.IPowerUp;
import model.powerup.PUEmpty;
import observable.IObservable;
import model.settings.Settings;
import physics.ICollideable;
import physics.IPhysicalBody;
import physics.PhysicalUnit;

/**
 * A unit. Probably a ship.
 *
 * @author Johannes Wikner @modified Victor Lindhé, johnhu
 */
public class Unit extends MoveableAbstract implements IObservable {

    private final static int MAX_STEER_SPEED = 10;
    private float steerAngle = Settings.getInstance().getSetting("steerAngle");
    private int hitPointsMax = Settings.getInstance().getSetting("hitPointsMax");
    private int hitPoints = hitPointsMax;
    private float hullStrength = Settings.getInstance().getSetting("hullStrength");
    private boolean isAccelerating = false;
    private final Direction steerDirection = new Direction();
    private IPowerUp powerUp = new PUEmpty();
    private final IPhysicalBody body;
    private final PropertyChangeSupport pcs;
    private float fireDelay = Settings.getInstance().getSetting("fireDelay");


    /**
     * Create a unit
     *
     * @param pos initial position
     * @param dir initial direction
     */
    public Unit(Vector pos, Vector dir, Vector size, float mass) {
        if (hitPointsMax <= 0) {
            throw new IllegalArgumentException("hit points must be positive");
        }
        
        this.body = new PhysicalUnit(this, pos, dir, size, mass);
        super.setBody(body);
        this.pcs = super.getPropertyChangeSupport();
    }

    /**
     * Updates the units position according to speed, direction and
     * updatefrequency
     *
     * @param tpf Updatefrequency, i.e. time since last frame
     */
    public void update(final float tpf) {
        this.accelerate(this.isAccelerating, tpf);
        this.steer(tpf);
        if (this.powerUp != null) {
            if (powerUp.isActive()) {
                this.powerUp.update(tpf);
            } else {
                this.removePowerUp();
            }
        }
        this.fireDelay = fireDelay <= 0 ? 0 : fireDelay - tpf;
    }

    /**
     * Accelerates the unit
     *
     * @param tpf Time per frame
     */
    private void accelerate(boolean isAccelerating, float tpf) {
        if (getSpeed() < getMaxSpeed() && isAccelerating) {
            this.body.accelerate(tpf);
        }
    }

    private void steer(float tpf) {
        if (steerDirection.getValue() != 0) {
            body.steer(steerDirection.getValue() * currentSteerAngle(), tpf);
        }
    }

    /**
     * TODO IS THIS REALLY USED SOMEWHERE? IF SO ADD TEST Determines how much a
     * unit can steer depending on its speed
     *
     * @return a steerAngle
     */
    private float currentSteerAngle() {
        if (this.getSpeed() <= 0) {
            return 0;
        }

        if (this.getSpeed() > MAX_STEER_SPEED) {
            return this.getSteerAngle();
        }

        return this.getSpeed() * this.getSteerAngle() / MAX_STEER_SPEED;
    }

    /**
     * Set the steer angle of the unit.
     *
     * @param steerAngle Angle determined in radians. Leave open interval for
     * configuration in-game Set the steer angle of the unit.
     */
    public void setSteerAngle(float steerAngle) {
        this.steerAngle = steerAngle;
    }

    public float getSteerAngle() {
        return this.steerAngle;
    }

    public void steerClockWise(boolean bool) {
        this.steerDirection.steerClockWise(bool);
    }

    public void steerAntiClockWise(boolean bool) {
        this.steerDirection.steerAntiClockWise(bool);
    }

    /**
     *
     * @param value Are we accelerating true of false
     */
    public void setIsAccelerating(boolean value) {
        this.isAccelerating = value;
    }

    /**
     * Sets the unit's current hit points (health).
     *
     * @param hitPoints The number of hit points to be set @precon hitPoints
     * must be a positive value and less than or equal to the maximum number of
     * hit points
     */
    public void setHitPoints(int hitPoints) {
        if (hitPoints > this.hitPointsMax) {
            this.hitPoints = this.hitPointsMax;
        } else {
            this.hitPoints = hitPoints;
        }
    }

    /**
     *
     * @return The unit's current hit points (health)
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     *
     * @return The unit's maximum hit points (health)
     */
    public int getHitPointsMax() {
        return this.hitPointsMax;
    }

    /**
     * Returns true if and only if the boat has been sent to Davy Jones' locker.
     *
     * @return
     */
    public boolean isDeadAndBuried() {
        return body.getPosition().equals(Vector.NONE_EXISTANT);
    }

    public void announceRemoval() {
        this.pcs.firePropertyChange("Unit removed", null, null);
    }

    public void collidedWith(ICollideable obj, float objImpactSpeed) {
        // Two units crashing
        if (obj instanceof Unit) {
            this.hitPoints -= Math.abs(objImpactSpeed / hullStrength);
        }

        if (obj instanceof IProjectile) {
            final float damage = ((IProjectile) obj).getDamage();
            this.hitPoints -= damage;
            StatusBox.getInstance().message("Cannonball hit and dealt "+damage+" damage!!" );
            
        }
    }

    /**
     * A unit is only equal to itself, no man is another man alike.
     *
     * @param obj Must be == this to be equal
     * @return Returns true if obj is the same as this.
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Float.floatToIntBits(this.steerAngle);
        hash = 23 * hash + this.hitPointsMax;
        hash = 23 * hash + this.hitPoints;
        hash = 23 * hash + Float.floatToIntBits(this.hullStrength);
        hash = 23 * hash + (this.isAccelerating ? 1 : 0);
        hash = 23 * hash + (this.steerDirection != null ? this.steerDirection.hashCode() : 0);
        hash = 23 * hash + (this.powerUp != null ? this.powerUp.hashCode() : 0);
        return hash;
    }

    public IPowerUp getPowerUp() {
        return this.powerUp;
    }

    public void applyPowerUp(IPowerUp power) {
        this.removePowerUp(); //remove old powerUp before adding a new one
        this.powerUp = power;
        StatusBox.getInstance().message(powerUp.getMessage());
        this.hitPointsMax += powerUp.getHitPointsMax();
        this.setHitPoints(this.getHitPoints() + powerUp.getHitPoints());
        this.acceleration += powerUp.getAcceleration();
        this.setMaxSpeed(this.getMaxSpeed() + powerUp.getMaxSpeed());
        this.setSteerAngle(this.getSteerAngle() + powerUp.getSteerAngle());
    }

    public void removePowerUp() {
        this.hitPointsMax -= powerUp.getHitPointsMax();
        this.acceleration -= powerUp.getAcceleration();
        this.maxSpeed -= powerUp.getMaxSpeed();
        this.steerAngle -= powerUp.getSteerAngle();
        this.powerUp = new PUEmpty();
    }
    
    public boolean canFire() {
        return fireDelay <= 0;
    }
    
    public void reload(float delay) {
        this.fireDelay = delay;
    }

    @Override
    public String toString() {
        return "Unit{" + super.toString() + "steerAngle=" + steerAngle + ", "
                + "hitPointsMax=" + hitPointsMax + ", acceleration="
                + acceleration + ", hitPoints=" + hitPoints + ", isAccelerating="
                + isAccelerating + ", steerDirection=" + steerDirection + '}';
    }
}