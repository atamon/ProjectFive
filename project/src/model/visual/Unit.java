package model.visual;

import physics.PhysType;
import math.Direction;
import math.Vector;
import model.tools.Settings;
import model.tools.IObservable;
import physics.IPhysicalModel;
import physics.IPhysicalBody;
import physics.PhysicalUnit;

/**
 * A unit. Probably a ship.
 * @author Johannes Wikner
 * @modified Victor Lindhé, johnhu
 */
public class Unit extends MoveableAbstract implements IObservable {

    private final static int MAX_STEER_SPEED = 10;
    private float steerAngle = Settings.getInstance().getSetting("steerAngle");
    private int hitPointsMax = Settings.getInstance().getSetting("hitPointsMax");
    private int hitPoints = hitPointsMax;
    private float hullStrength = Settings.getInstance().getSetting("hullStrength");
    private boolean isAccelerating = false;
    private Direction steerDirection = new Direction();

    /**
     * Create a unit
     * @param pos initial position
     * @param dir initial direction
     */
    public Unit(Vector pos, Vector dir, Vector size, float mass, int owner) {
        this.body = new PhysicalUnit(this,pos,dir,size,mass);
        if (hitPointsMax <= 0) {
            throw new IllegalArgumentException("hit points must be positive");
        }
    }

    /**
     * Updates the units position according to speed, direction and updatefrequency
     * @param tpf Updatefrequency, i.e. time since last frame
     */
    public void update(final float tpf) {
        this.accelerate(this.isAccelerating, tpf);
        positionUpdated();
        this.steer(tpf);
        directionUpdated();
    }



    /**
     * Accelerates the unit
     * @param tpf Time per frame
     */
    private void accelerate(boolean isAccelerating, float tpf) {
        if (getSpeed() < getMaxSpeed() && isAccelerating) {
            this.body.accelerate(tpf);
        }
    }

    private void steer(float tpf) {
        if (steerDirection.getValue() != 0) {
            body.steer(steerDirection, tpf);
            this.directionUpdated();
        }
    }

    public void damage(int damage) {
        this.setHitPoints(hitPoints - damage);
    }

    /**
     * Set the steer angle of the unit. 
     * @param steerAngle Angle determined in radians. Leave open interval for configuration in-game
     */
    public void setSteerAngle(float steerAngle) {
        this.steerAngle = steerAngle;
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
     * @param hitPoints The number of hit points to be set
     * @precon hitPoints must be a positive value and less than
     * or equal to the maximum number of hit points
     * @throws IllegalArgumentExcpetion if not a valid hitPoints value is given
     */
    public void setHitPoints(int hitPoints) {
        if (hitPoints > this.hitPointsMax) {
            throw new IllegalArgumentException(
                    "Hit points must be < getPointsMax()");
        }
        this.hitPoints = hitPoints;
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
     * @return 
     */
    public boolean isDeadAndBuried() {
        return body.getPosition().equals(Vector.NONE_EXISTANT);
    }
    
    public void announceRemoval(){
        this.pcs.firePropertyChange("Unit removed", null, null);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Unit other = (Unit) obj;
        if (Float.floatToIntBits(this.steerAngle) != Float.floatToIntBits(other.steerAngle)) {
            return false;
        }
        if (this.hitPointsMax != other.hitPointsMax) {
            return false;
        }
        if (this.acceleration != other.acceleration) {
            return false;
        }
        if (this.hitPoints != other.hitPoints) {
            return false;
        }
        if (this.isAccelerating != other.isAccelerating) {
            return false;
        }
        if (this.steerDirection != other.steerDirection && (this.steerDirection == null || !this.steerDirection.equals(other.steerDirection))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Float.floatToIntBits(this.steerAngle);
        hash = 67 * hash + this.hitPointsMax;
        hash = 67 * hash + this.hitPoints;
        hash = 67 * hash + (this.isAccelerating ? 1 : 0);
        hash = 67 * hash + (this.steerDirection != null ? this.steerDirection.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Unit{" + super.toString() + "steerAngle=" + steerAngle + ", "
                + "hitPointsMax=" + hitPointsMax + ", acceleration=" + 
                acceleration + ", hitPoints=" + hitPoints + ", isAccelerating=" 
                + isAccelerating + ", steerDirection=" + steerDirection + '}';
    }

    public void collidedWith(IPhysicalModel obj, float objImpactSpeed) {
        // Two units crashing
        if (obj instanceof Unit) {
            this.hitPoints -= Math.abs(objImpactSpeed/hullStrength);
        }
    }
}
