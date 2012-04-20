/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import model.IGame;
import view.View;

/**
 * A class to represent a Controller
 * @author Victor Lindhé
 * @modified johnhu
 */
public class Controller {
    private IGame game;
    private View view;
    private SimpleApplication jme3;
    private InputManager inputManager;
    
    public Controller(SimpleApplication jme3, View view, IGame game) {
        // Save instances
        this.jme3 = jme3;
        this.view = view;
        this.game = game;
        
        // Load game settings and send it to model
        game.setSettings(SettingsLoader.readSettings("assets/settings"));
        
        // Register view with our game as a listener
        this.game.addPropertyChangeListener(view);
        
        // Get inputManager and clear its pre-defined triggers and keys
        this.inputManager = jme3.getInputManager();
        this.inputManager.clearMappings();
        
        // Create listeners
        ActionListener joinPlayerListener = new JoinPlayerListener(game, inputManager);
        ActionListener globalListener = new GlobalListener(game, inputManager);
        
        // Build our graphical scene
        this.view.createScene();
    }
    
    public void update(float tpf) {
        this.game.update(tpf);
    }
    
}
