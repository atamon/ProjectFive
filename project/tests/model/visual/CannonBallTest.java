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
        posOfBall = new Vector(1, 1, 1);
        dirOfBall = new Vector(-1, 1, -1);
        sizeOfBall = new Vector(0.1f, 0.1f, 0.1f);
        massOfBall = 1f;
        speedOfBall = 20;
        owner = new Unit(new Vector(1, 1, 1), new Vector(1, 0, 1), new Vector(3, 3, 3), 5f);
        ball = new CannonBall(posOfBall, dirOfBall, sizeOfBall, massOfBall, speedOfBall, owner);
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
        ball.announceRemoval();
        assertTrue(this.removeWorked);
    }

    /**
     * Tests if damage is positive.
     */
    @Test
    public void testGetDamage() {
        assert (ball.getDamage() > 0);
    }

    /**
     * Tests if the owner is the one we set.
     */
    @Test
    public void testGetOwner() {
        assert (ball.getOwner() == owner);
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
