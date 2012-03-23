/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * A class to represent an immutable Item.
 * @author Victor Lindhé
 */
public class Item {
    private Vector position;
    private ItemTypes type;
    
    /**
     * Creates an Item of a given type and at a given position.
     * @param type ItemTypes
     * @param position Vector
     */
    public Item(ItemTypes type, Vector position) {
        this.position = new Vector(position.getX(), position.getY());
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
}
