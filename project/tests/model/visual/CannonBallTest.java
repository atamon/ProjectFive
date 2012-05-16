/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import model.settings.SettingsLoader;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import model.settings.Settings;
import org.junit.Test;
import static org.junit.Assert.*;
import physics.IPhysicalModel;

/**
 *
 * @author victorlindhe
 */
public class CannonBallTest {

    private CannonBall ball;
    private Vector posOfBall;
    private Vector dirOfBall;
    private Vector sizeOfBall;
    private int speedOfBall;
    private float massOfBall;
    private Unit owner;
    private boolean removeWorked = false;
    boolean collidedAndRemoved = false;

    public CannonBallTest() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
        this.posOfBall = new Vector(1, 1, 1);
        this.dirOfBall = new Vector(-1, 1, -1);
        this.sizeOfBall = new Vector(0.1f, 0.1f, 0.1f);
        this.massOfBall = 1f;
        this.speedOfBall = 20;
        this.owner = new Unit(new Vector(1, 1, 1), new Vector(1, 0, 1), new Vector(3, 3, 3), 5f);
        this.ball = new CannonBall(posOfBall, dirOfBall, sizeOfBall, massOfBall, speedOfBall, owner);
    }

    /**
     * Tests if position changes from starting position when updated.
     */
    @Test
    public void testUpdate() {
        assertTrue(true);
        // TODO UNUSED METHOD ATM, WILL BE NEEDED FOR FURTHER IMPLEMENTATION
    }

    /**
     * Tests if a PropertyChangeLister gets a remove message.
     */
    @Test
    public void testAnnounceRemoval() {
        PropertyChangeListener pListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                if ("CannonBall Removed".equals(pce.getPropertyName())) {
                    removeWorked = true;
                }
            }
        };

        this.ball.addPropertyChangeListener(pListener);
        this.ball.announceRemoval();
        assertTrue(this.removeWorked);
    }

    /**
     * Tests if damage is positive.
     */
    @Test
    public void testGetDamage() {
        assert (this.ball.getDamage() > 0);
    }

    /**
     * Tests if the owner is the one we set.
     */
    @Test
    public void testGetOwner() {
        assert (this.ball.getOwner() == this.owner);
    }

    @Test
    public void testCollidedWith() {
        PropertyChangeListener pListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                if ("CannonBall Removed".equals(pce.getPropertyName())) {
                    collidedAndRemoved = true;
                }
            }
        };

        IPhysicalModel obj = owner;
        float objImpactSpeed = 0.35f;
        
        ball.addPropertyChangeListener(pListener);
        ball.collidedWith(obj, objImpactSpeed);

        assertTrue(collidedAndRemoved);
    }
}
