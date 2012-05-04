/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.player.Player;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Direction;
import model.visual.Battlefield;
import math.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johnhu
 */
public class GameTest {
    private Battlefield testBf = new Battlefield(200);
    private Game testGame = new Game(testBf);
    private float tpf = test.Utils.simulateTpf();
    
    private void createPlayerZero(){
        try{
            testGame.createPlayer(0);
        } catch(IllegalArgumentException e){
            // ok player with id 0 already exist.
        }
    }
    
    @Test(expected = RuntimeException.class)
    public void testCreatePlayer(){
        testGame.createPlayer(6);
        
        assertTrue(testGame.getPlayer(6) instanceof Player);
        
        testGame.createPlayer(6); // exception here
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testGetPlayer(){
        testGame.createPlayer(0);
        
        testGame.getPlayer(1123); //exception here
    }
    

    @Test
    public void testAddUnitListener(){
        createPlayerZero();
        PropertyChangeListener pl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                throw new UnsupportedOperationException("Test");
            }
            
        };
        testGame.addUnitListener(0, pl);
    }
    
    @Test
    public void testRemoveUnitListener(){
        createPlayerZero();
        PropertyChangeListener pl = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
        testGame.addUnitListener(0, pl);
        testGame.removePropertyChangeListener(pl);
    }
    
    @Test(expected = RuntimeException.class) // some kind of runtime exception

    public void testStartRound(){
        Game instance = new Game();
        
        instance.nextRound(); // exception here, no players -> no round
        instance.createPlayer(0);
        instance.createPlayer(1);
        
        instance.nextRound();
    }
    
    @Test
    public void testEndRound(){
        Game instance = new Game();
        float rounds = instance.getNbrOfRounds();
        instance.endRound(); // no round started.
        assertTrue(rounds == instance.getNbrOfRounds());
        instance.nextRound();
        instance.endRound();
        assertTrue(rounds-1 == instance.getNbrOfRounds());
        
    }
    
    @Test
    public void testGetBattlefieldPosition() {
        assertTrue(testBf.getPosition().equals(testGame.getBattlefieldPosition()));
    }
    
    /**
     * Test of getBattlefieldSize method, of class Game.
     */
    @Test
    public void testGetBattlefieldSize() {
        System.out.println("getBattlefieldSize");
        Game instance = new Game(new Battlefield(200));
        assertTrue(instance.getBattlefieldSize() == this.testGame.getBattlefieldSize());
    }

    @Test
    public void testSteerPlayerUnit(){
        this.createPlayerZero();
        
        testGame.getPlayer(0).getUnit().setSpeed(10);
        Vector oldDir = testGame.getPlayer(0).getUnitDirection();
        testGame.steerPlayerUnit(Direction.CLOCKWISE, 0, tpf); // id = 0
        
        assertFalse(oldDir.equals(testGame.getPlayer(0).getUnitDirection()));
    }
    
    @Test
    public void testAccelerateUnit(){
        this.createPlayerZero();
        testGame.getPlayer(0).getUnit().setSpeed(0);
        float oldSpeed = testGame.getPlayer(0).getUnit().getVelocity();
        
        testGame.acceleratePlayerUnit(0, true);
        testGame.update(tpf);
        assertTrue(oldSpeed < testGame.getPlayer(0).getUnit().getVelocity());
        
    }
    
    @Test
    public void testUpdate(){
        this.createPlayerZero();
        Vector oldPos = testGame.getPlayerPosition(0);
        this.testGame.getPlayer(0).getUnit().setSpeed(10);
        
        testGame.update(tpf);
        
        assertFalse(oldPos.equals(testGame.getPlayerPosition(0)));   
    }

    @Test
    public void testGetBattlefieldCenter(){
        
        Vector center = testGame.getBattlefieldCenter();
        Vector expected = new Vector(testGame.getBattlefieldPosition());
        expected.add(new Vector(
                testGame.getBattlefieldSize(),testGame.getBattlefieldSize()));
        expected.mult(0.5f);
        assertTrue(center.equals(expected));
        
    }
    
    @Test
    public void testPlaceUnit(){
        this.createPlayerZero();
        
        Vector oldPos = testGame.getPlayerPosition(0);
        
        testGame.placeUnit(0, new Vector(0,0));
        
        assertFalse(oldPos.equals(testGame.getPlayerPosition(0)));
    }
    
    /**
     * Test of getNbrOfPlayers method, of class Game.
     */
    @Test
    public void testGetNbrOfPlayers() {
        System.out.println("getNbrOfPlayers");
        Game instance = new Game(new Battlefield());
        assertTrue(instance.getNbrOfPlayers() == 0);
        instance.createPlayer(0);
        assertTrue(instance.getNbrOfPlayers() == 1);
    }

    /**
     * Test of getPlayerPosition method, of class Game.
     */
    @Test
    public void testGetPlayerPosition() {
        System.out.println("getPlayerPosition");
        Game instance = new Game();
        instance.createPlayer(0);
        Vector oldVector = instance.getPlayerPosition(0);
        assertTrue(oldVector.getClass() == Vector.class);
        instance.getPlayer(0).getUnit().place((float)Math.random(), (float)Math.random());
        assertFalse(oldVector.equals(instance.getPlayerPosition(0)));
    }
}
