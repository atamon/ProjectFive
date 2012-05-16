/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.settings.SettingsLoader;
import model.settings.Settings;
import math.Vector;
import model.visual.Item;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * ItemFactory test.
 * @author johnhu
 */
public class ItemFactoryTest {
    
    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
    }
    
    /**
     * Test to see if new items are created. Also tests the private method populateUs
     * 
     */
    @Test
    public void testCreateNewItem() {
        ItemFactory iFac = new ItemFactory();
        Item testItem = iFac.createNewItem(Vector.ZERO_VECTOR);
        assertTrue(testItem != null && testItem.getPosition().equals(Vector.ZERO_VECTOR));
    }
}
