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
        Item testItem = iFac.createNewItem(new Vector(100,1,100));
        assertTrue(testItem != null);
        assertTrue(testItem.getPosition().getX() > 0 && testItem.getPosition().getX() < 100);
        assertTrue(testItem.getPosition().getY() > 0 && testItem.getPosition().getY() < 100);
    }
}
