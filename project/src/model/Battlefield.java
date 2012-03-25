package model;

import java.util.List;
import java.util.ListIterator;

/**
 * A class to represent a Battlefield.
 * @author Victor Lindhé
 */
public class Battlefield {
    private List<Unit> units;
    private Item item;
    private Vector size;
    
    /**
     * Creates a Battlefield with default size (100,100) and an Item.
     */
    public Battlefield() {
        this.size = new Vector(100.0f, 100.0f);
        this.item = ItemFactory.createNewItem(this.getSize());
    }
    
    /**
     * Creates a Battlefield with a size x-wise and size y-wise.
     * @param xWidth 
     * @param yWidth 
     */
    public Battlefield(double xWidth, double yWidth) {
        // Checking for errors
        if(xWidth == 0 && yWidth != 0) {
            xWidth = yWidth;
        } else if (xWidth != 0 && yWidth == 0) {
            yWidth = xWidth;
        }
        
        if(xWidth == 0 && yWidth == 0) {
            xWidth = yWidth = 100.0f;
        }
        
        // Negating negative numbers
        if (xWidth < 0) {
            xWidth *= -1;
        }
        if (yWidth < 0) {
            yWidth *= -1;
        }
        
        this.size = new Vector(xWidth, yWidth);
        this.item = ItemFactory.createNewItem(this.getSize());
    }
    
    /**
     * Creates a Battlefield with a size Vector.
     * @param size Vector
     */
    public Battlefield(Vector size) {
        if(size == null) {
            this.size = new Vector(100.0f, 100.0f);
        } else {
            this.size = new Vector(size);  
        }
        this.item = ItemFactory.createNewItem(this.getSize());
    }
    
    /**
     * Returns a copy of the size Vector.
     * @return Vector
     */
    public Vector getSize() {
        return new Vector(this.size);
    }
    
    /**
     * Returns all the Unit objects on the Battlefield.
     * @return List
     */
    public List<Unit> getUnits() {
        return this.units;
    }
    
    /**
     * Returns the Item object on the Battlefield.
     * @return Item
     */
    public Item getItem() {
        return this.item;
    }
    
    /**
     * Removes a Unit from the Battlefield
     * @param unit Unit to remove.
     */
    public void removeUnit(Unit unit) {
        this.units.remove(unit);
    }
    
    /**
     * Adds a unit to the board.
     * Nothing will happen if there already are 4 units there.
     * @param unit Unit
     */
    public void addUnit(Unit unit) {
        if(unit != null && units.size() < 4) {
            this.units.add(unit);
        }
    }
    
    /**
     * Places the units in the corners of the Battlefield.
     */
    public void positionUnits() {
        ListIterator<Unit> unitIterator = this.units.listIterator();
        while(unitIterator.hasNext()) {
            Unit unit = unitIterator.next();
            if(unitIterator.nextIndex() == 0) {
                unit.setPosition(2, 2);
            } else if (unitIterator.nextIndex() == 1) {
                unit.setPosition(this.size.getX()-2, 2);
            } else if (unitIterator.nextIndex() == 2) {
                unit.setPosition(2, this.size.getX()-2);
            } else if (unitIterator.nextIndex() == 3) {
                unit.setPosition(this.size.getX()-2, this.size.getX()-2);
            }
        }
    }
    
    /**
     * Clears the Battlefield.
     * Removes all Units
     */
    public void removeUnits() {
        this.units.clear();
    }
    
    /**
     * Removes Item from Battlefield.
     */
    public void removeItem() {
        this.item = null;
    }
    
    /**
     * Adds Item to Battlefield.
     * But not if there already is one there.
     */
    public void addItem() {
        if(this.item == null) {
            this.item = ItemFactory.createNewItem(this.getSize());
        }
    }
    
    /**
     * Compares Battlefield to another Battlefield with respect to the size.
     * @param obj Another Battlefield
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        
        if(obj.getClass() != this.getClass()) {
            return false;
        }
        
        Battlefield bField = (Battlefield)obj;
        return this.size.equals(bField.getSize());
    }

    /**
     * Hash code for this Battlefield.
     * @return Hash code.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.units != null ? this.units.hashCode() : 0);
        hash = 47 * hash + (this.item != null ? this.item.hashCode() : 0);
        hash = 47 * hash + (this.size != null ? this.size.hashCode() : 0);
        return hash;
    }
    
}
