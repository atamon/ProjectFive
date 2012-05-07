/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.tools.Vector;
import model.visual.Item;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * ItemFactory still a WIP so test will be updated.
 * @author johnhu
 */
public class ItemFactoryTest {
    
    /**
     * Test to see if new items are created.
     * 
     */
    @Test
    public void testCreateNewItem() {
        Item testItem = ItemFactory.createNewItem(new Vector(1, 1));
        assertTrue(testItem != null);
    }
}
