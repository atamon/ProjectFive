package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A unit. Probably a ship.
 * @author Johannes Wikner
 * @modified Victor Lindhé
 */
public class Unit {

    // Used in .equals for accepting a difference between vectors
    public static final float ACCEPTED_STEER_DIFF = 0.60f;
    public static final int GLOBAL_MAX_SPEED = 200;
    
    private final Vector pos;
    private final Vector dir;
    private float steerAngle = 2;
    private float speed;
    private int hitPointsMax;
    private int acceleration = 10;
    private int retardation = 10;
    private int maxSpeed = GLOBAL_MAX_SPEED;
    private int hitPoints;
//    private PowerUp powerUp; TODO
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Creates a new unit
     * @param pos Initial position
     * @param dir Initial position
     * @param hitPointsMax 
     */
    public Unit(Vector pos, Vector dir, int hitPointsMax) {
        if (hitPointsMax <= 0) {
            throw new IllegalArgumentException("hit points must be positive");
        }

        this.pos = new Vector(pos);
        this.dir = new Vector(dir);
        this.hitPointsMax = hitPointsMax;
        this.hitPoints = hitPointsMax;
    }

    /**
     * Create a unit with default hp set to 100
     * @param pos initial position
     * @param dir initial direction
     */
    public Unit(Vector pos, Vector dir) {
        this(pos, dir, 100);
    }

    /**
     * Updates the units position according to speed, direction and updatefrequency
     * @param tpf Updatefrequency, i.e. time since last frame
     */
    public void updatePosition(float tpf) {
        // If we're standing still there's no need to compute this at all
        if (this.speed * tpf != 0) {
            Vector oldPosition = new Vector(this.getPosition());
            // Update directions length according to speed and tpf
            dir.mult(this.speed * tpf);
            this.pos.add(dir);

            // Normalize for next update
            dir.normalize();
            this.pcs.firePropertyChange("Updated Position", oldPosition, this.getPosition());
        }
    }

    /**
     * Accelerates the unit
     * @param tpf Time per frame
     */
    public void accelerate(final float tpf) {
        // v = v0 + at
        if (this.speed < this.maxSpeed) {
            float newSpeed = this.speed + this.acceleration * tpf;
            newSpeed = newSpeed >= this.maxSpeed ? this.maxSpeed : newSpeed;
            this.setSpeed(newSpeed);
        }
    }

    /**
     * Retardates the unit.  
     * @param tfp Time per frame
     */
    public void retardate(final float tpf) {
        if (this.speed > 0) {
            // The unit should never get a negative speed so we only retardate
            // until zero is reached
            float newSpeed = this.speed - retardation * tpf;
            newSpeed = newSpeed < 0 ? 0 : newSpeed;
            this.setSpeed(newSpeed);
        }
    }

    public float getMaxSpeed() {
        return this.maxSpeed;
    }

    /**
     * Steers the unit clockwise
     * @param tpf Time per frame
     */
    public void steerClockwise(float tpf) {
        if (this.speed != 0) {
            dir.rotate(-this.steerAngle * tpf); // any other suggestion ? maybe a method in vector?   
        }
    }

    /**
     * Steers the unit anti-clockwise
     * @param tpf Time per frame
     */
    public void steerAntiClockwise(float tpf) {
        if (this.speed != 0) {
            dir.rotate(this.steerAngle * tpf);
        }
    }

    /**
     * Set the steer angle of the unit. 
     * @param steerAngle Angle determined in radians. Leave open interval for configuration in-game
     */
    public void setSteerAngle(float steerAngle) {
        this.steerAngle = steerAngle;
    }

    /**
     * Returns the steerAngle for this unit.
     * @return A float representing how fast the unit can steer.
     */
    public float getSteerAngle() {
        return this.steerAngle;
    }

    /**
     * Sets our unit's direction. Use steer(Anti)Clockwise() to steer 
     * the unit. This will set its direction instantly
     * @param dir A vector holding the new direction
     */
    public void setDirection(Vector dir) {
        this.setDirection(dir.getX(), dir.getY());
    }

    /**
     * Sets our unit's direction. Use steer(Anti)Clockwise() to steer 
     * the unit. This will set its direction instantly
     * @param x Direction in x-axis
     * @param y Direction in y-axis
     */
    public void setDirection(float x, float y) {
        this.dir.setX(x);
        this.dir.setY(y);
    }

    /**
     * Sets the position of the unit. Use updatePosition() to move unit in its
     * current direction with its current speed
     * @param pos Vector with coordinates where to position the unit
     */
    public void setPosition(Vector pos) {
        this.setPosition(pos.getX(), pos.getY());
    }

    /**
     * Sets the position of the unit. Use updatePosition() to move unit in its
     * current direction with its current speed
     * @param x New position in x-axis
     * @param y New positoin in y-axis
     */
    public void setPosition(float x, float y) {
        this.pos.setX(x);
        this.pos.setY(y);
    }

    /**
     * Sets the speed of the unit. Use accelerate/retardate to gradialy
     * speed up/slow down the unit
     * @param speed The speed to be set
     * @precon speed >= 0 msut be a a value greater than or equal to 0
     * @throws IllegalArgumentException If a given speed is less than 0
     */
    public void setSpeed(float speed) {
        if (speed < 0 || speed > this.maxSpeed) {
            throw new IllegalArgumentException("Must be a postitive integer < getMaxSpeed()");
        }
        this.speed = speed;
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
        if (hitPoints < 0 || hitPoints > this.hitPointsMax) {
            throw new IllegalArgumentException(
                    "Must have a positive hit points value < getPointsMax()");
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
     * @return A vector describing the position of the unit
     */
    public Vector getPosition() {
        return new Vector(this.pos);
    }

    /**
     * 
     * @return A vector describing the direciton of the unit
     */
    public Vector getDirection() {
        return new Vector(this.dir);
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
     * 
     * @return The unit's current speed
     */
    public float getSpeed() {
        return this.speed;
    }

    public void addPropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.addPropertyChangeListener(ls);
    }

    public void removePropertyChangeListener(PropertyChangeListener ls) {
        this.pcs.removePropertyChangeListener(ls);
    }

    @Override
    public String toString() {
        return "Unit{" + "pos=" + pos + ", dir=" + dir + ", hitPointsMax="
                + hitPointsMax + ", steerAngle=" + steerAngle + ", speed="
                + speed + ", acceleration=" + acceleration + ", retardation="
                + retardation + ", maxSpeed=" + maxSpeed + ", hitPoints="
                + hitPoints + '}';
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
        if (this.pos != other.pos && (this.pos == null || !this.pos.equals(other.pos))) {
            return false;
        }
        if (this.dir != other.dir && (this.dir == null || !this.dir.equals(other.dir))) {
            return false;
        }
        if (this.hitPointsMax != other.hitPointsMax) {
            return false;
        }
        if (Float.floatToIntBits(this.steerAngle) != Float.floatToIntBits(other.steerAngle)) {
            return false;
        }
        if (Float.floatToIntBits(this.speed) != Float.floatToIntBits(other.speed)) {
            return false;
        }
        if (this.acceleration != other.acceleration) {
            return false;
        }
        if (this.retardation != other.retardation) {
            return false;
        }
        if (this.maxSpeed != other.maxSpeed) {
            return false;
        }
        if (this.hitPoints != other.hitPoints) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.pos != null ? this.pos.hashCode() : 0);
        hash = 47 * hash + (this.dir != null ? this.dir.hashCode() : 0);
        hash = 47 * hash + this.hitPointsMax;
        hash = 47 * hash + Float.floatToIntBits(this.steerAngle);
        hash = 47 * hash + Float.floatToIntBits(this.speed);
        hash = 47 * hash + this.acceleration;
        hash = 47 * hash + this.retardation;
        hash = 47 * hash + this.maxSpeed;
        hash = 47 * hash + this.hitPoints;
        hash = 47 * hash + (this.pcs != null ? this.pcs.hashCode() : 0);
        return hash;
    }
}
