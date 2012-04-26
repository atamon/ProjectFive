/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.tools.Vector;

/**
 *
 * @author johannes wikner
 */
public abstract class MoveableAbstract implements IVisualisable {
    public static final int GLOBAL_MAX_SPEED = 50;
    protected Vector pos;
    protected Vector dir;
    protected float speed;
    protected float maxSpeed = GLOBAL_MAX_SPEED;
    
    public MoveableAbstract(Vector pos, Vector dir){

        this.pos = new Vector(pos);
        this.dir = new Vector(dir);
        
        
    }
    
    /**
     * Move the moveable in its current direction and speed
     * @param tpf Time per frame 
     */
    protected void move(float tpf){
        //no speed, no time per frame means no movement
        if (this.speed*tpf != 0) {
            // Update directions length according to speed and tpf
            Vector v = new Vector(dir);
            v.mult(this.speed * tpf);
            this.pos.add(v);
            this.positionUpdated();
        }
    }
    
    /**
     * A movable needs to be - in some way - able to notify when it has changed
     */
    protected abstract void directionUpdated();
    
    
    /**
     * A movable needs to be - in some way - able to notify when it has changed
     */
    protected abstract void positionUpdated();
    

    /**
     * Sets the position of the moveable
     * @param pos Vector with coordinates where to position the unit
     */
    public void setPosition(Vector pos) {
        this.setPosition(pos.getX(), pos.getY());
    }

    /**
     * Sets the position of the movable
     * @param x New position in x-axis
     * @param y New positoin in y-axis
     */
    public void setPosition(float x, float y) {
        this.pos.setX(x);
        this.pos.setY(y);
        this.positionUpdated();
    }

    /**
     * Sets the speed of the movable. 
     * @param speed The speed to be set
     * @precon speed >= 0 msut be a a value greater than or equal to 0
     * @throws IllegalArgumentException If a given speed is less than 0
     */
    public void setSpeed(float speed) {
//        if (speed < 0 || speed > this.maxSpeed) {
//            throw new IllegalArgumentException("Must be a postitive integer < getMaxSpeed()");
//        }
        this.speed = speed;
    }
    
    
    /**
     * Sets our movable's direction. 
     * @param dir A vector holding the new direction
     */
    public void setDirection(Vector dir) {
        this.setDirection(dir.getX(), dir.getY());
    }

    /**
     * Sets our moveable's direction. 
     * @param x Direction in x-axis
     * @param y Direction in y-axis
     */
    public void setDirection(float x, float y) {
        this.dir.setX(x);
        this.dir.setY(y);
        this.dir.normalize();
        this.directionUpdated();
    }

    
    /**
     * 
     * @return A vector describing the position of the movable object
     */
    public Vector getPosition() {
        return new Vector(this.pos);
    }

    /**
     * 
     * @return A vector describing the direciton of the movable object
     */
    public Vector getDirection() {
        return new Vector(this.dir);
    }

    /**
     * 
     * @return Movable object's maximum speed
     */
    public float getMaxSpeed() {
        return this.maxSpeed;
    }
    
    /**
     * 
     * @return The movable object's current speed
     */
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public String toString() {
        return "MoveableAbstract{" + "pos=" + pos + ", dir=" + dir + ", speed=" 
                + speed + ", maxSpeed=" + maxSpeed + '}';
    }

    /**
     * This method must be overrided by subclasses, otherwise two compared 
     * subclasses may return true even though they are different
     * @param obj
     * @return true if this and object's all instances are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MoveableAbstract)) {
            return false;
        }
        final MoveableAbstract other = (MoveableAbstract) obj;
        if (this.pos != other.pos && (this.pos == null || !this.pos.equals(other.pos))) {
            return false;
        }
        if (this.dir != other.dir && (this.dir == null || !this.dir.equals(other.dir))) {
            return false;
        }
        if (Float.floatToIntBits(this.speed) != Float.floatToIntBits(other.speed)) {
            return false;
        }
        if (Float.floatToIntBits(this.maxSpeed) != Float.floatToIntBits(other.maxSpeed)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.pos != null ? this.pos.hashCode() : 0);
        hash = 29 * hash + (this.dir != null ? this.dir.hashCode() : 0);
        hash = 29 * hash + Float.floatToIntBits(this.speed);
        hash = 29 * hash + Float.floatToIntBits(this.maxSpeed);
        return hash;
    }

    
}
