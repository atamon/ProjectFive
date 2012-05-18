package math;

/**
 * A mutable Vector for the room (2 dimensions).
 * @author Victor Lindhé
 * @modified by Anton Lindgren
 * @tested by Anton Lindgren
 */
public class Vector {
    public static final Vector ZERO_VECTOR = new Vector(0, 0, 0);
    
    // We need a constant to define a vector that does not exist in this universe(-e +ity).
    public static final Vector NONE_EXISTANT = new Vector(1000f, 1000f, 1000f);
 
    private float x;
    private float y;
    private float z;
    
    /**
     * Creates a Vector.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Creates a Vector as a copy of another Vector
     * @param v Another Vector
     */
    public Vector(Vector v) {
        x = v.getX();
        y = v.getY();
        z = v.getZ();
    }
    
    /**
     * Getter for the x coordinate.
     * @return x
     */
    public float getX() {
        return x;
    }
    
    /**
     * Getter for the y coordinate.
     * @return y
     */
    public float getY() {
        return y;
    }
    
    /**
     * Getter for the z coordinate.
     * @return z
     */
    public float getZ() {
        return z;
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
     * Sets the z element
     * @param z
     */
    public void setZ(float z) {
        this.z = z;
    }
    
    /**
     * Adds another vector to this
     * @param vec 
     */
    public void add(Vector vec) {
        x += vec.getX();
        y += vec.getY();
        z += vec.getZ();
    }
    
    /**
     * Multiplies another vector to this
     * @param vec The vector to multiply with
     */
    public void mult(Vector vec) {
        x = x*vec.getX();
        y = y*vec.getY();
        z = z*vec.getZ();
    }
    
    /**
     * Multiplies a number to this
     * @param number The number to multiply x and y and z with
     */
    public void mult(float number) {
        x = x*number;
        y = y*number;
        z = z*number;
    }
    
    /**
     * Normalizes the vector's length to 1
     * @throws ArithmeticException if getLength() == 0
     */
    public void normalize() throws ArithmeticException {
        if (x != 0 || y != 0 || z != 0) {
            float length = getLength();
            x = x/length;
            y = y/length;
            z = z/length;
            if(getLength() != 1){
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
        return (float) Math.sqrt(x*x + y*y + z*z);
    }
    
    /**
     * Rotates a vector by multiplying with the rotating matrix
     * @param degree 
     */
    public void rotateXY(float radian){
        // Save original x since it is changed before y and used in new y's calculation
        float orgX = x;
        x = (float)( Math.cos(radian)*x - Math.sin(radian)*y);
        y = (float)( Math.sin(radian)*orgX + Math.cos(radian)*y);
    }
    
    /**
     * Returns a string with the format "X = X.XXXX, Y = Y.YYYY".
     * @return 
     */
    @Override
    public String toString() {
        return "X = " + x + ", Y = " + y + ", Z = " + z;
    }
    
    /**
     * Checks if another Vector equals this Vector.
     * If not a Vector, returns false.
     * @param obj Another object.
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Vector v = (Vector)obj;
        return x == v.getX() && y == v.getY() && z == v.getZ();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Float.floatToIntBits(x);
        hash = 53 * hash + Float.floatToIntBits(y);
        hash = 53 * hash + Float.floatToIntBits(z);
        return hash;
    }

    
}
