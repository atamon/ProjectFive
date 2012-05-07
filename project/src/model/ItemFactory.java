/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import model.tools.PowerUp;
import model.tools.Vector;
import model.visual.Item;

/**
 * A factory to class that creates a new Item.
 * @author Victor Lindhé
 */
public class ItemFactory {
    
    private List<PowerUp> powerUps = new ArrayList<PowerUp>();
    
    public static Item createNewItem(Vector fieldSize) {
        String powerName = "";
        final int randNumber = (int)(Math.random()*2+1);
        
        
        float randXPos = (float)Math.random() * fieldSize.getX();
        float randYPos = (float)Math.random() * fieldSize.getY();
        
        return new Item(powerUps.get(randNumber), new Vector(randXPos, randYPos));
    }
}
