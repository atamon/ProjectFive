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
    
    private int speed;
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
     * Acceleratates the unit.
     */
    public void accelerate(){
        this.setSpeed(speed+acceleration);
    }
    /**
     * Retardates the unit.  
     */
    public void retardate(){
        if (speed > 0) {
            this.setSpeed(this.speed-retardation);
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
    
    public void setSteerAngle(double steerAngle){
        if (steerAngle < 0 || steerAngle > Math.PI*2){
            throw new IllegalArgumentException("Angle must be positive 0 < angle < pi*2");
        }
    }
    
    public void setDirection(Vector dir) {
        this.setDirection(dir.getX(), dir.getY());
    }
    
    public void setDirection(float x, float y){
        this.dir.setX(x);
        this.dir.setY(y);
    }
    
    public void setPosition(Vector pos) {
        this.setPosition(pos.getX(),pos.getY());
    }

    public void setPosition(float x, float y){
        this.pos.setX(x);
        this.pos.setY(y);
    }
    
    public void setSpeed(int speed) {
        if(speed < 0) {
            throw new IllegalArgumentException("Must be a postitive integer");
        }
        this.speed = speed;
    }


    public void setAcceleration(int acceleration) {
        if( acceleration < 0 ){
            throw new IllegalArgumentException(
                    "Must have a positive acceleration value");
        }
        this.acceleration = acceleration;
    }

    public void setHitPoints(int hitPoints) {
        if( hitPoints <= 0 ){
            throw new IllegalArgumentException(
                    "Must have a positive hit points value");
        }
        this.hitPoints = hitPoints;
    }

    public void setRetardation(int retardation) {
        if( retardation < 0 ){
            throw new IllegalArgumentException(
                    "Must have a positive retardation value");
        }
        this.retardation = retardation;
    }
    
    public Vector getPosition(){
        return new Vector(this.pos);
    }
    
    public Vector getDirection(){
        return new Vector(this.dir);
    }
    
    public int getHitPoints() {
        return this.hitPoints;
    }
    
    public int getHitPointsMax(){
        return this.hitPointsMax;
    }
    
    public int getAcceleration(){
        return this.acceleration;
    }
    
    public int getRetardation(){
        return this.retardation;
    }
    
    public int getSpeed(){
        return this.speed;
    }
    
    // TODO Equals HashCode toString, when finished.
    
}
