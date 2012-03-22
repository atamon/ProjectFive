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
    
    private int speed;
    private int acceleration = 10;
    private int retardation = 10;
    private int turnRadius;
    
//    private PowerUp powerUp; TODO
    
    public Unit(Vector pos, Vector dir){
        this.pos = new Vector(pos);
        this.dir = new Vector(dir);
    }

    /**
     * Acceleratates the unit. As the unit accelerates, the turnRadius increases
     * @return unit's speed after the acceleration.
     */
    public int accelerate(){
        this.speed += this.acceleration;
        
        return this.speed;
    }
    /**
     * Retardates the unit. As the unit retardates, turnRadius decreases.
     * @return unit's speed after retardation
     */
    public int retardate(){
        this.speed -= this.retardation;
        
        return this.speed;
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

    public void setTurnRadius(int turnRadius) {
        if(turnRadius < 0){
            throw new IllegalArgumentException("Must be a postitive integer");
        }
        this.turnRadius = turnRadius;
    }
    
    public Vector getPosition(){
        return this.pos;
    }
    
    public Vector getDirection(){
        return this.dir;
    }
    
    public int getAcceleration(){
        return this.acceleration;
    }
    public int getSpeed(){
        return this.speed;
    }
    
    public int getTurnRadius(){
        return this.turnRadius;
    }
    
    @Override
    public String toString() {
        return "Unit{" + "pos=" + pos + ", dir=" + dir + ", speed=" + speed + ", acceleration=" + acceleration + ", retardation=" + retardation + ", turnRadius=" + turnRadius + '}';
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
        if (this.speed != other.speed) {
            return false;
        }
        if (this.acceleration != other.acceleration) {
            return false;
        }
        if (this.turnRadius != other.turnRadius) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.pos != null ? this.pos.hashCode() : 0);
        hash = 53 * hash + (this.dir != null ? this.dir.hashCode() : 0);
        hash = 53 * hash + this.speed;
        hash = 53 * hash + this.acceleration;
        hash = 53 * hash + this.turnRadius;
        return hash;
    }
    
    
}
