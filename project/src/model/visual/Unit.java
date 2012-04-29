package model.visual;

import model.tools.PhysType;
import model.tools.Direction;
import model.tools.Vector;
import model.physics.IPhysical;
import model.tools.Settings;
import model.tools.IObservable;

/**
 * A unit. Probably a ship.
 * @author Johannes Wikner
 * @modified Victor Lindhé, johnhu
 */
public class Unit extends MoveableAbstract implements IObservable, IPhysical {

    private Vector size = new Vector(1, 1);
    private final static int MAX_STEER_SPEED = 10;
//    private PowerUp powerUp; TODO
    private final int owner;
    private float steerAngle = Settings.getInstance().getSetting("steerAngle");
    private int hitPointsMax = Settings.getInstance().getSetting("hitPointsMax");
    private int acceleration = Settings.getInstance().getSetting("acceleration");
    private int retardation = Settings.getInstance().getSetting("retardation");
    private int hitPoints = hitPointsMax;
    private boolean isAccelerating = false;
    private Direction steerDirection = new Direction();
//  private PowerUp powerUp; TODO

    /**
     * Create a unit
     * @param pos initial position
     * @param dir initial direction
     */
    public Unit(Vector pos, Vector dir, int owner) {
        super(pos, dir);
        if (hitPointsMax <= 0) {
            throw new IllegalArgumentException("hit points must be positive");
        }
        
        this.owner = owner;
        this.maxSpeed = 20;
    }

    /**
     * Updates the units position according to speed, direction and updatefrequency
     * @param tpf Updatefrequency, i.e. time since last frame
     */
    public void update(final float tpf) {
        this.accelerate(this.isAccelerating, tpf);
        this.steer(tpf);
        this.move(tpf);

    }



    /**
     * Accelerates the unit
     * @param tpf Time per frame
     */
    private void accelerate(boolean accelUp, float tpf) {
        // v = v0 + at
        if (accelUp) {
            this.setSpeed(Math.min(this.maxSpeed, this.speed + this.acceleration * tpf));
        } else {
            this.setSpeed(Math.max(0, this.speed - this.retardation * tpf));
        }
    }

    private void steer(float tpf) {
        dir.rotate(steerDirection.getValue() * this.currentSteerAngle() * tpf);
        dir.normalize();
        this.directionUpdated();
    }

    public void damage(int damage) {
        this.setHitPoints(hitPoints - damage);
    }

    /**
     * Determines how much a unit can steer depending on its speed
     * 
     * @return a steerAngle
     */
    private float currentSteerAngle() {
        if (this.speed <= 0) {
            return 0;
        }

        if (this.speed > MAX_STEER_SPEED) {
            return this.steerAngle;
        }

        return this.speed * this.steerAngle / MAX_STEER_SPEED;
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
     * Returns the steerAngle for this unit.
     * @return A float representing how fast the unit can steer.
     */
    public float getSteerAngle() {
        return this.steerAngle;
    }

    public Vector getSize() {
        return this.size;
    }

    /**
     * 
     * @param value Are we accelerating true of false 
     */
    public void setIsAccelerating(boolean value) {
        this.isAccelerating = value;
    }

    /**
     * Sets the acceleration of th unit
     * @param acceleration How fast unit will accelerate
     * @precon acceleration >= 0 Acceleration must be positive
     * @throws IllegalArgumentException If a given acceleration is les than 0
     */
    public void setAcceleration(int acceleration) {
        if (acceleration < 0) {
            throw new IllegalArgumentException(
                    "Must have a positive acceleration value");
        }
        this.acceleration = acceleration;
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
     * Sets the retardation. 
     * @param retardation How fast the unit will slow down. Higher means faster
     * @precon retardation must be positive integer
     * @throws IllegalArgumentException If not a valid value was given
     */
    public void setRetardation(int retardation) {
        if (retardation < 0) {
            throw new IllegalArgumentException(
                    "Must have a positive retardation value");
        }
        this.retardation = retardation;
    }

    /**
     * 
     * @return The unit's current hit points (health)
     */
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Sets maximum HitPoints for this unit.
     * @param hitPointsMax 
     */
    public void setHitPointsMax(int hitPointsMax) {
        this.hitPointsMax = hitPointsMax;
    }

    /**
     * 
     * @return The unit's maximum hit points (health)
     */
    public int getHitPointsMax() {
        return this.hitPointsMax;
    }

    /**
     * 
     * @return The unit's acceleration
     */
    public int getAcceleration() {
        return this.acceleration;
    }

    /**
     * 
     * @return The unit's retardation
     */
    public int getRetardation() {
        return this.retardation;
    }

    /**
     * Returns true if and only if the boat has been sent to Davy Jones' locker.
     * @return 
     */
    public boolean isDeadAndBuried() {
        return this.pos.equals(Vector.NONE_EXISTANT);
    }

    @Override
    public String toString() {
        return super.toString() + "\nUnit{" + "steerAngle=" + steerAngle + ", hitPointsMax=" + hitPointsMax + ", acceleration=" + acceleration + ", retardation=" + retardation + ", hitPoints=" + hitPoints + ", isAccelerating=" + isAccelerating + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
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
        if (this.retardation != other.retardation) {
            return false;
        }
        if (this.hitPoints != other.hitPoints) {
            return false;
        }
        if (this.isAccelerating != other.isAccelerating) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + super.hashCode();
        hash = 23 * hash + Float.floatToIntBits(this.steerAngle);
        hash = 23 * hash + this.hitPointsMax;
        hash = 23 * hash + this.acceleration;
        hash = 23 * hash + this.retardation;
        hash = 23 * hash + this.hitPoints;
        hash = 23 * hash + (this.isAccelerating ? 1 : 0);
        return hash;
    }

    public float getMass() {
        return this.size.getX() * this.size.getY();
    }

    public PhysType getType() {
        return PhysType.BOAT;
    }

    public int getOwner() {
        return this.owner;
    }

    public void remove() {
        setIsAccelerating(false);
        setSteerAngle(0);
        setPosition(Vector.NONE_EXISTANT);

    }
}
