package Model;

/**
 * A unit. Probably a ship.
 * @author Johannes Wikner
 */
public class Unit {
    public static final int MAX_SPEED = 100;
    public static final int MIN_TURN_RADIUS = 1;
    public static final int MAX_TURN_RADIUS = 10;
    
    private final Vector pos;
    private final Vector dir;
    private final int hitPointsMax;
    
    private int speed;
    private int acceleration = 10;
    private int retardation = 10;
    private int hitPoints;
//    private PowerUp powerUp; TODO
    
    public Unit(Vector pos, Vector dir, int hitPointsMax){
        if(hitPointsMax <= 0 ){
            throw new IllegalArgumentException("hit points must be positive");
        }
        
        this.pos = new Vector(pos);
        this.dir = new Vector(dir);
        this.hitPointsMax = hitPointsMax;
        this.hitPoints = hitPointsMax;
    }

    public Unit(Vector pos, Vector dir){
        this(pos,dir,100);
    }
    
    /**
     * Acceleratates the unit. As the unit accelerates, the turnRadius increases
     */
    public void accelerate(){
        this.setSpeed(speed+acceleration);
    }
    /**
     * Retardates the unit. As the unit retardates, turnRadius decreases.
     * 
     */
    public void retardate(){
        this.setSpeed(this.speed-retardation);
    }

    public void steerClockwise(float steerAngle){
        if (this.speed != 0){
            setDirection(dir.mult(UtilMath.rotate(-steerAngle))); // any other suggestion ? maybe a method in vector?   
        }
    }
    
    public void steerAntiClockwise(float steerAngle){
        if (this.speed != 0){
            setDirection(dir.mult(UtilMath.rotate(steerAngle)));
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
        return this.pos;
    }
    
    public Vector getDirection(){
        return this.dir;
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
