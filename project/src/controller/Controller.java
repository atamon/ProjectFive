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
 *
 * @author Victor Lindhé @modified johnhu
 */
public class Controller {

    private IGame game;
    private View view;
    private InputManager inputManager;

    public Controller(InputManager inpManager, View view, IGame game) {
        // Save instances
        this.view = view;
        this.game = game;
        this.inputManager = inpManager;

        // clear inpManager's pre-defined triggers and keys
        this.inputManager.clearMappings();

        // Load game settings and send it to model
        game.setSettings(SettingsLoader.readSettings("assets/settings"));

        // Register view with our game as a listener
        this.game.addPropertyChangeListener(view);

        // Create listeners
        new JoinPlayerListener(game, inputManager);
        new GlobalListener(game, inputManager);
        new LeavePlayerListener(game, inputManager);
        

        // Build our graphical scene
        this.view.createScene();
    }

    public void update(float tpf) {
        this.game.update(tpf);
    }
}
