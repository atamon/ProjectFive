/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.physics.PhysType;
import model.tools.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victorlindhe
 */
public class CannonBallTest {
    private CannonBall ball;
    private Vector posOfBall;
    private Vector dirOfBall;
    private int owner;
    private int speed;
    private boolean removeWorked = false;
    
    public CannonBallTest() {
        this.posOfBall = new Vector(1,1);
        this.dirOfBall = new Vector(-1, -1);
        this.owner = 1;
        this.speed = 20;
        this.ball = new CannonBall(owner, posOfBall, dirOfBall, speed);
    }
    
    @Before
    public void setUp() {
        
    }

    /**
     * Tests if position changes from starting position when updated.
     */
    @Test
    public void testUpdate() {
        this.ball.update(0.2f);
        assertFalse(this.posOfBall.equals(this.ball.getPosition()));
    }
    
    /**
     * Tests if returned size is null. It shouldn't be.
     * Tests if size of CannonBall is immutable.
     */
    @Test
    public void testGetSize() {
        Vector size = this.ball.getSize();
        assert(size != null);
        size.setX(0.0f);
        size.setY(0.0f);
        assertFalse(size.equals(this.ball.getSize()));
    }
    
    /**
     * Tests if mass is at or below 0.
     */
    @Test
    public void testGetMass() {
        assert(this.ball.getMass() > 0.0f);
    }
    
    /**
     * Tests if the PhysType is a PhysType.CANNONBALL
     */
    @Test
    public void testGetType() {
        assert(this.ball.getType() == PhysType.CANNONBALL);
    }
    
    /**
     * Tests if a PropertyChangeLister gets a remove message.
     */
    @Test
    public void testRemove() {
        PropertyChangeListener pListener = new PropertyChangeListener() {
            
            public void propertyChange(PropertyChangeEvent pce) {
                if(pce.getPropertyName().equals("CannonBall Removed")) {
                    CannonBallTest.this.removeWorked = true;
                }
            }
            
        };
        
        this.ball.addPropertyChangeListener(pListener);
        this.ball.remove();
        assertTrue(this.removeWorked);
    }
    
    /**
     * Tests if damage is positive.
     */
    @Test
    public void testGetDamage() {
        assert(this.ball.getDamage() > 0);
    }
    
    /**
     * Tests if the owner is the one we set.
     */
    @Test
    public void testGetOwner() {
        assert(this.ball.getOwner() == this.owner);
    }
}
