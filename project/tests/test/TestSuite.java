/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import model.*;
import model.powerup.*;
import model.round.RoundTest;
import model.visual.BattlefieldTest;
import model.visual.CannonBallTest;
import model.visual.ItemTest;
import model.visual.StatusBoxTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Anton Lindgren
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({BattlefieldTest.class,
                     PlayerTest.class,
                     UnitTest.class,
                     VectorTest.class,
                     PUDrunkTest.class,
                     PUEmptyTest.class,
                     PUHealthTest.class,
                     PUSpeedTest.class,
                     PUTurnTest.class,
                     ItemTest.class,
                     RoundTest.class,
                     GameTest.class,
                     ItemFactoryTest.class,
                     BattlefieldTest.class,
                     CannonBallTest.class,
                     StatusBoxTest.class})
public class TestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
