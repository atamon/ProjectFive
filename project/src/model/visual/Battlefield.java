package model.visual;

import model.tools.Vector;

/**
 * A class to represent a Battlefield.
 * @author Victor Lindhé
 * @modified johnnes
 */
public final class Battlefield implements IVisualisable {
    private final Vector size;
    private final Vector pos = new Vector(0,0);
    
    /**
     * Creates a Battlefield with default size (100,100) and an Item.
     */
    public Battlefield() {
        this(new Vector(100.0f,100.0f));
    }
    
    /**
     * Creates a Battlefield with a size x-wise and size y-wise.
     * @param xWidth 
     * @param yWidth 
     * @throws NumberFormatException
     */
    public Battlefield(Vector size) throws NumberFormatException {
        if(size.getX() > 0 && size.getY() > 0) {
            this.size = new Vector(size);
        } else {
            throw new NumberFormatException("Size must be > 0");
        }
    }
    
    
    /**
     * Returns a copy of the size Vector.
     * @return Vector
     */
    public Vector getSize() {
        return this.size;
    }
    
    public Vector getPosition() {
        return new Vector(this.pos);
    }
    
    
    /**
     * Returns the position of the center.
     * @return Vector
     */
    public Vector getCenter() {
        return new Vector(this.size.getX()/2, this.size.getY()/2);
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
        if (this.size != other.size && (this.size == null || !this.size.equals(other.size))) {
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
        hash = 37 * hash + (this.size != null ? this.size.hashCode() : 0);
        hash = 37 * hash + (this.pos != null ? this.pos.hashCode() : 0);
        return hash;
    }

    
    
}
