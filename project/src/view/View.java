/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Game;
import com.jme3.app.SimpleApplication;

/**
 *
 * @author atamon
 */
public class View {

    private final SimpleApplication jme3;
    private final Game game;
    
    public View(SimpleApplication jme3, Game game) {
        this.jme3 = jme3;
        this.game = game;
        
    }
    
    /**
     * Sets up the graphical world.
     */
    public void createScene() {
        
    }
}
