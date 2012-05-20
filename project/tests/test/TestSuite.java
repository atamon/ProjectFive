package test;

import model.*;
import model.math.VectorTest;
import model.player.PlayerTest;
import model.powerup.*;
import model.round.RoundTest;
import model.round.SimpleRoundModelTest;
import model.visual.*;
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
                     GameTest.class,
                     VectorTest.class,
                     PUDrunkTest.class,
                     PUEmptyTest.class,
                     PUHealthTest.class,
                     PUSpeedTest.class,
                     PUTurnTest.class,
                     ItemTest.class,
                     MolotovTest.class,
                     RoundTest.class,
                     SimpleRoundModelTest.class,
                     PiratePlayerModelTest.class,
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
