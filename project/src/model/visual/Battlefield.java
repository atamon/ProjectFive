package model.visual;

import model.tools.Vector;

/**
 * A class to represent a Battlefield.
 * @author Victor Lindhé
 * @modified johnnes
 */
public final class Battlefield implements IVisualisable {
    private final float size;
    private final Vector pos = new Vector(0,0);
    
    /**
     * Creates a Battlefield with default size (100,100) and an Item.
     */
    public Battlefield() {
        this(20.0f);
    }
    
    /**
     * Creates a Battlefield with a size x-wise and size y-wise.
     * @param xWidth 
     * @param yWidth 
     */
    public Battlefield(float size) {
        this.size = size;
    }
    
    
    /**
     * Returns a copy of the size Vector.
     * @return Vector
     */
    public float getSize() {
        return this.size;
    }
    
    public Vector getPosition() {
        return new Vector(this.pos);
    }

    /**
     * Compares Battlefield to another Battlefield with respect to the size.
     * @param obj Another Battlefield
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Battlefield other = (Battlefield) obj;
        if (Float.floatToIntBits(this.size) != Float.floatToIntBits(other.size)) {
            return false;
        }
        if (this.pos != other.pos && (this.pos == null || !this.pos.equals(other.pos))) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Float.floatToIntBits(this.size);
        hash = 97 * hash + (this.pos != null ? this.pos.hashCode() : 0);
        return hash;
    }    
}
