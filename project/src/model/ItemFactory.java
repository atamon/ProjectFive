/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import math.Vector;
import model.powerup.IPowerUp;
import model.powerup.PUHealth;
import model.powerup.PUSpeed;
import model.powerup.PUTurn;
import model.visual.Item;

/**
 * A factory to class that creates a new Item.
 * @author Victor Lindhé
 */
public class ItemFactory {
    
    private List<IPowerUp> powerUps = new ArrayList<IPowerUp>();
    
    public ItemFactory(){
        populatePUs();
    }
    
    private void populatePUs(){
        powerUps.add(new PUSpeed());
        powerUps.add(new PUTurn());
        powerUps.add(new PUHealth());
    }
    
    public Item createNewItem(final Vector fieldSize) {
        final int randNumber = (int)(Math.random()*this.powerUps.size());
        
        
        final float randXPos = (float)Math.random() * fieldSize.getX();
        final float randZPos = (float)Math.random() * fieldSize.getZ();
        // TODO REMOVE STATIC UGLY NUMBER
        Item powerUp = new Item(powerUps.get(randNumber), new Vector(randXPos, 3.9f, randZPos));
        powerUp.getPowerUp().setActive(true);
        return powerUp;
    }
}
