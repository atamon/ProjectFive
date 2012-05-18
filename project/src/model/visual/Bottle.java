/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeSupport;
import math.Vector;
import model.settings.Settings;
import physics.IPhysicalBody;
import physics.PhysicalItem;

/**
 *
 * @author atamon
 */
public abstract class Bottle extends MoveableAbstract {

    private float lifeTime = Settings.getInstance().getSetting("itemLifeTime");
    private final Vector pointingDir = new Vector(1, 1, 1);
    private final Vector size = new Vector(2f, 2f, 2f);
    private final float mass = 0.1f;
    private final PropertyChangeSupport pcs;

    public Bottle(Vector position) {
        super.setBody(new PhysicalItem(this, position, pointingDir, size, mass));
        System.out.println("BOTTLE CREATED" + getPhysicalObject());
        pcs = getPropertyChangeSupport();
    }

    @Override
    public void announceRemoval() {
        pcs.firePropertyChange("Bottle Removed", null, this);
    }

    public float getLifeTime() {
        return lifeTime;
    }

    public void update(final float tpf) {

        lifeTime -= tpf;
        if (lifeTime <= 0) {
            announceRemoval();
        }
    }
}