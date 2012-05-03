/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.tools.PowerUp;
import model.tools.Vector;
import model.visual.Item;

/**
 * A factory to class that creates a new Item.
 * @author Victor Lindhé
 */
public class ItemFactory {
    
    public static Item createNewItem(Vector fieldSize) {
        double randNumber = Math.random();
//        ItemTypes type;
//        if(randNumber > 0.00 && randNumber < 0.25) {
//            type = ItemTypes.GOLD;
//        } else if (randNumber >= 0.25 && randNumber < 0.50) {
//            type = ItemTypes.MERMAID;
//        } else if (randNumber >= 0.50 && randNumber < 0.75) {
//            type = ItemTypes.POWERUP;
//        } else {
//            type = ItemTypes.RUM;
//        }
        
        float randXPos = (float)Math.random() * fieldSize.getX();
        float randYPos = (float)Math.random() * fieldSize.getY();
        
        return new Item(new PowerUp(), new Vector(randXPos, randYPos));
    }
}
