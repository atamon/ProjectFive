package model.tools;

/**
 * A mutable Vector for the plane (2 dimensions).
 * @author Victor Lindhé
 * @modified by Anton Lindgren
 * @tested by Anton Lindgren
 */
public class Vector {
    public static final Vector ZERO_VECTOR = new Vector(0, 0);
    
    // We need a constant to define a vector that does not exist in this universe(-e +ity).
    public static final Vector NONE_EXISTANT = new Vector(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
 
    private float x;
    private float y;
    
    /**
     * Creates a Vector.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Creates a Vector as a copy of another Vector
     * @param v Another Vector
     */
    public Vector(Vector v) {
        this.x = v.getX();
        this.y = v.getY();
    }
    
    /**
     * Getter for the x coordinate.
     * @return x
     */
    public float getX() {
        return this.x;
    }
    
    /**
     * Getter for the y coordinate.
     * @return y
     */
    public float getY() {
        return this.y;
    }
    
    /**
     * Sets the x-element
     * @param x 
     */
    public void setX(float x) {
        this.x = x;
    }
    
    /**
     * Sets the y-element
     * @param y 
     */
    public void setY(float y) {
        this.y = y;
    }
    
    /**
     * Adds another vector to this
     * @param vec 
     */
    public void add(Vector vec) {
        this.x += vec.getX();
        this.y += vec.getY();
    }
    
    /**
     * Multiplies another vector to this
     * @param vec The vector to multiply with
     */
    public void mult(Vector vec) {
        this.x = this.x*vec.getX();
        this.y = this.y*vec.getY();
    }
    
    /**
     * Multiplies a number to this
     * @param number The number to multiply x and y with
     */
    public void mult(float number) {
        this.x = this.x*number;
        this.y = this.y*number;
    }
    
    /**
     * Normalizes the vector's length to 1
     * @throws ArithmeticException if getLength() == 0
     */
    public void normalize() throws ArithmeticException {
        if (x != 0 || y != 0) {
            float length = this.getLength();
            this.x = this.x/length;
            this.y = this.y/length;
            if(this.getLength() != 1){
                normalize();
            }
        } else {
            throw new ArithmeticException(
                    "ERROR: Vector.normalize(), division by Zero!");
        }
    }
    
    /**
     * Returns the length of the vector
     * @return 
     */
    public float getLength() {        
        return (float) Math.sqrt(x*x + y*y);
    }
    
    /**
     * Rotates a vector by multiplying with the rotating matrix
     * @param degree 
     */
    public void rotate(float radian){
        // Save original x since it is changed before y and used in new y's calculation
        float orgX = this.x;
        this.x = (float)( Math.cos(radian)*x - Math.sin(radian)*y);
        this.y = (float)( Math.sin(radian)*orgX + Math.cos(radian)*y);
    }
    /**
     * Returns a string with the format "X = X.XXXX, Y = Y.YYYY".
     * @return 
     */
    @Override
    public String toString() {
        return "X = " + this.x + ", Y = " + this.y;
    }
    
    /**
     * Checks if another Vector equals this Vector.
     * If not a Vector, returns false.
     * @param obj Another object.
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Vector v = (Vector)obj;
        return this.x == v.getX() && this.y == v.getY();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Float.floatToIntBits(this.x);
        hash = 53 * hash + Float.floatToIntBits(this.y);
        return hash;
    }

    
}
