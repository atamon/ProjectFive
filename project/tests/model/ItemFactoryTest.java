/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

<<<<<<< HEAD
import controller.SettingsLoader;
import model.tools.Settings;
import model.tools.Vector;
=======
import math.Vector;
>>>>>>> phys-refactor
import model.visual.Item;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * ItemFactory still a WIP so test will be updated.
 * @author johnhu
 */
public class ItemFactoryTest {
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }
    
    /**
     * Test to see if new items are created.
     * 
     */
    @Test
    public void testPopulateUs() {
        ItemFactory iFac = new ItemFactory();
        Item testItem = iFac.createNewItem(Vector.ZERO_VECTOR);
        assertTrue(testItem != null && testItem.getPosition().equals(new Vector(Vector.ZERO_VECTOR)));
    }
}
