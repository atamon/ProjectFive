/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.visual;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import model.ItemFactory;
import model.settings.Settings;
import model.settings.SettingsLoader;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author atamon
 */
public class MolotovTest {

    private Molotov mol;
    private Unit victim;
    private boolean passed = false;

    @Before
    public void setUp() {
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));
        float uSize = Settings.getInstance().getSetting("unitSize");
        mol = new Molotov(ItemFactory.randomizeBottlePosition(new Vector(100, 1, 100)));
        victim = new Unit(Battlefield.getStartingPosition(0, new Vector(100, 1, 100)),
                Battlefield.getStartingDir(0),
                new Vector(uSize, uSize, uSize),
                Settings.getInstance().getSetting("unitMass"));
    }

    /**
     * Test of getDamage method, of class Molotov.
     */
    @Test
    public void testGetDamage() {
        int expDamage = Settings.getInstance().getSetting("molotovDamage");
        assertTrue(expDamage == mol.getDamage());
        assertTrue(mol.getDamage() >= 0);
    }

    /**
     * Test of collidedWith method, of class Molotov.
     */
    @Test
    public void testCollidedWith() {
        victim.setIsAccelerating(true);
        PropertyChangeListener pcl = new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("Bottle Removed".equals(evt.getPropertyName()) && mol.equals(evt.getNewValue())) {
                    passed = true;
                }
            }
        };
        mol.addPropertyChangeListener(pcl);
        mol.collidedWith(victim, 0);
        assertTrue(passed);
    }
}
