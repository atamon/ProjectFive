/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.tools.ItemTypes;
import model.tools.Vector;

/**
 * A class to represent an immutable Item.
 * @author Victor Lindhé
 */
public final class Item implements IVisualisable {
    private final Vector position;
    private final ItemTypes type;
    private final Vector size = new Vector(0.5f,0.5f);
    /**
     * Creates an Item of a given type and at a given position.
     * @param type ItemTypes
     * @param position Vector
     */
    public Item(ItemTypes type, Vector position) {
        this.position = new Vector(position.getX(), position.getY());
        this.type = type;
    }
    
    /**
     * Returns a copy of the position Vector.
     * @return Vector
     */
    public Vector getPosition() {
        return new Vector(this.position.getX(), this.position.getY());
    }
    
    /**
     * Returns the type of this Item.
     * @return ItemTypes
     */
    public ItemTypes getType() {
        return this.type;
    }

    public Vector getSize() {
        return this.size;
    }

    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
