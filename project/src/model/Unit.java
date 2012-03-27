package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


/**
 * A unit. Probably a ship.
 * @author Johannes Wikner
 * @modified Victor Lindhé
 */
public class Unit {

    private final Vector pos;
    private final Vector dir;
    private final int hitPointsMax;
    private float steerAngle = 2;
    private float speed;
    private int acceleration = 10;
    private int retardation = 10;
    private int maxSpeed = 200;
    private int hitPoints;
    private boolean isAccelerating = false;
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
    public void updateUnit(float tpf) {
        this.accelerate(this.isAccelerating, tpf);
        this.move(tpf);
    }

    /**
     * Move the unit in its current direction and speed
     * @param tpf Time per frame 
     */
    private void move(float tpf){
        //no speed, no time per frame means no movement
        if (this.speed*tpf != 0) {
            // Update directions length according to speed and tpf
            Vector v = new Vector(dir);
            v.mult(this.speed * tpf);
            this.pos.add(v);
            this.pcs.firePropertyChange("Updated Position", null, this.getPosition());
        }
    }
    
    /**
     * Accelerates the unit
     * @param tpf Time per frame
     */
    private void accelerate(boolean accelUp, float tpf) {
        // v = v0 + at
        if (accelUp){
            this.setSpeed(Math.min(this.maxSpeed, this.speed + this.acceleration * tpf));
        } else {
            this.setSpeed(Math.max(0, this.speed - this.retardation * tpf));
        }
    }

    public void steer(Direction steerDirection, float tpf){
        if(this.speed != 0){
            dir.rotate(steerDirection.getValue()*this.steerAngle*tpf);
            this.updateDirection();
        }
    }
    
    private void updateDirection(){
        this.pcs.firePropertyChange("Updated Direction", null, this.getDirection());
    }
    /**
     * Set the steer angle of the unit. 
     * @param steerAngle Angle determined in radians
     */
    public void setSteerAngle(float steerAngle) {
        if (steerAngle < 0 || steerAngle > Math.PI * 2) {
            throw new IllegalArgumentException("Angle must be positive 0 < angle < pi*2");
        }
        this.steerAngle = steerAngle;
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
        this.updateDirection();
    }
    
    /**
     * 
     * @param value Are we accelerating true of false 
     */
    public void setIsAccelerating(boolean value){
        this.isAccelerating = value;
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
        if (speed < 0) {
            throw new IllegalArgumentException("Must be a postitive integer");
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
                    "Must have a positive hit points value");
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
        if (this.pcs != other.pcs && (this.pcs == null || !this.pcs.equals(other.pcs))) {
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
