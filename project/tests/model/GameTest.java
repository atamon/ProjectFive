/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.visual.Battlefield;
import model.unit.Unit;
import model.tools.Vector;
import com.jme3.asset.DesktopAssetManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import view.GraphicalUnit;
import java.beans.PropertyChangeListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johnhu
 */
public class GameTest {
    
    public GameTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of getBattlefieldSize method, of class Game.
     */
    @Test
    public void testGetBattlefieldSize() {
        System.out.println("getBattlefieldSize");
        Game instanceTwo = new Game(new Battlefield(200, 200), 1, 2);
        assertTrue(instanceTwo.getBattlefieldSize().equals(new Vector(200,200)));
    }

//    /**
//     * Test of startRound method, of class Game.
//     */
//    @Test
//    public void testStartRound() {
//        Game instance = new Game();
//        instance.startRound();
//        
//    }

    /**
     * Test of placeUnit method, of class Game.
     */
    @Test
    public void testPlaceUnit() {
        System.out.println("placeUnit");
        Game instance = new Game();
        Unit unit = new Unit(new Vector(1,1), new Vector(2,2));
        Player player = new Player(1, unit);
        instance.placeUnit(1, new Vector(0, 0));
        assertTrue(instance.getPlayerPosition(1).equals(new Vector(0, 0)));
    }

    /**
     * Test of endRound method, of class Game.
     */
//    @Test
//    public void testEndRound() {
//        System.out.println("endRound");
//        Game instance = new Game();
//        
//    }

    /**
     * Test of getNbrOfPlayers method, of class Game.
     */
    @Test
    public void testGetNbrOfPlayers() {
        System.out.println("getNbrOfPlayers");
        Game instance = new Game(new Battlefield(), 1, 2);
        assertTrue(instance.getNbrOfPlayers() == 2);
    }

    /**
     * Test of getPlayerPosition method, of class Game.
     */
    @Test
    public void testGetPlayerPosition() {
        System.out.println("getPlayerPosition");
        Game instance = new Game();
        assertTrue(instance.getPlayerPosition(1).equals(new Vector(1, 1)));
    }
}
