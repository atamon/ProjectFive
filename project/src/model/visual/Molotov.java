/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import math.Vector;
import model.settings.Settings;
import physics.ICollideable;

/**
 *
 * Class to represent a molotov cocktail on the battlefield.
 */
public class Molotov extends Bottle implements IProjectile {
    
    private Vector pointingDir = new Vector(1,1,1);
    private int damage = Settings.getInstance().getSetting("molotovDamage");
    
    public Molotov(Vector position) {
        super(position);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void collidedWith(ICollideable obj, float objImpactSpeed) {
        if (obj instanceof Unit) {
            Unit unit = (Unit)obj;
            Vector bombPosition = getPosition();
            bombPosition.setY(-0.5f);
            unit.getPhysicalObject().applyForce(bombPosition, new Vector(0, Settings.getInstance().getSetting("molotovForce"), 0));
            announceRemoval();
        }
    }
    
}
