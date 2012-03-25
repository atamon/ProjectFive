package Model;

/**
 * A unit. Probably a ship.
 * @author Johannes Wikner
 */
public class Unit {
    
    private final Vector pos;
    private final Vector dir;
    private final int hitPointsMax;
    
    private double steerAngle = Math.PI/4;
    
    private double speed;
    private int acceleration = 10;
    private int retardation = 10;
    private int maxSpeed = 100;
    private int hitPoints;
//    private PowerUp powerUp; TODO
    
    /**
     * Creates a new unit
     * @param pos Initial position
     * @param dir Initial position
     * @param hitPointsMax 
     */
    public Unit(Vector pos, Vector dir, int hitPointsMax){
        if(hitPointsMax <= 0 ){
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
    public Unit(Vector pos, Vector dir){
        this(pos,dir,100);
    }
    
    /**
     * Accelerates the unit
     * @param tpf Time per frame
     */
    public void accelerate(float tpf){
        // v = v0 + at
        this.setSpeed(speed+acceleration*tpf);
    }
    /**
     * Retardates the unit.  
     * @param tfp Time per frame
     */
    public void retardate(float tpf){
        if (this.speed > 0) {
            this.setSpeed(this.speed-retardation*tpf);
        }
    }
    
    
    /**
     * Steers the unit clockwise
     * @param tpf Time per frame
     */
    public void steerClockwise(float tpf){
        if (this.speed != 0){
//            setDirection(dir.rotate(-this.steerAngle*tpf))); // any other suggestion ? maybe a method in vector?   

        }
    }
    
    /**
     * Steers the unit anti-clockwise
     * @param tpf Time per frame
     */
    public void steerAntiClockwise(float tpf){
        if (this.speed != 0){
//            setDirection(dir.rotate(this.steerAngle*tpf)));
        }
    }
    /**
     * Set the steer angle of the unit. 
     * @param steerAngle Angle determined in radians
     */
    public void setSteerAngle(double steerAngle){
        if (steerAngle < 0 || steerAngle > Math.PI*2){
            throw new IllegalArgumentException("Angle must be positive 0 < angle < pi*2");
        }
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
    public void setDirection(float x, float y){
        this.dir.setX(x);
        this.dir.setY(y);
    }
    
    /**
     * Sets the position of the unit. Use move() to move unit in its
     * current direction
     * @param pos Vector with coordinates where to position the unit
     */
    public void setPosition(Vector pos) {
        this.setPosition(pos.getX(),pos.getY());
    }

    /**
     * Sets the position of the unit. Use move() to move unit in its
     * current direction
     * @param x New position in x-axis
     * @param y New positoin in y-axis
     */
    public void setPosition(float x, float y){
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
    public void setSpeed(double speed) {
        if(speed < 0) {
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
        if( acceleration < 0 ){
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
        if( hitPoints < 0 || hitPoints > this.hitPointsMax ){
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
        if( retardation < 0 ){
            throw new IllegalArgumentException(
                    "Must have a positive retardation value");
        }
        this.retardation = retardation;
    }
    
    /**
     * 
     * @return A vector describing the position of the unit
     */
    public Vector getPosition(){
        return new Vector(this.pos);
    }
    /**
     * 
     * @return A vector describing the direciton of the unit
     */
    public Vector getDirection(){
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
    public int getHitPointsMax(){
        return this.hitPointsMax;
    }
    
    /**
     * 
     * @return The unit's acceleration
     */
    public int getAcceleration(){
        return this.acceleration;
    }
    
    /**
     * 
     * @return The unit's retardation
     */
    public int getRetardation(){
        return this.retardation;
    }
    
    /**
     * 
     * @return The unit's current speed
     */
    public double getSpeed(){
        return this.speed;
    }
    
    // TODO Equals HashCode toString, when finished.
    
}
