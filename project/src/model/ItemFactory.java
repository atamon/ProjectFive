/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import math.Vector;
import model.powerup.*;
import model.visual.Battlefield;
import model.visual.Item;

/**
 * A factory to class that creates a new Item.
 * @author Victor Lindhé
 */
public class ItemFactory {
    private List<IPowerUp> powerUps = new ArrayList<IPowerUp>();
    private final static float ITEM_SPAWNHEIGHT = 3.9f;
    private final static float[] MARGINS = {5,5,5,5}; //xMax,xMin,yMax,yMin
    public ItemFactory(){
        populatePUs();
    }
    
    private void populatePUs(){
        powerUps.add(new PUSpeed());
        powerUps.add(new PUTurn());
        powerUps.add(new PUHealth());
        powerUps.add(new PUDrunk());
    }
    
    public Item createNewItem(final Vector fieldSize) {
        final int randNumber = (int)(Math.random()*this.powerUps.size());
        
        final float randXPos = (float)Math.random() * (fieldSize.getX()-MARGINS[0]-MARGINS[1]) + MARGINS[1];
        final float randZPos = (float)Math.random() * (fieldSize.getZ()-MARGINS[2]-MARGINS[3]) + MARGINS[3];
        // TODO REMOVE STATIC UGLY NUMBER
        final Item item = new Item(powerUps.get(randNumber), new Vector(randXPos, ITEM_SPAWNHEIGHT, randZPos));
        item.getPowerUp().setActive(true);
        return item;
    }
}
