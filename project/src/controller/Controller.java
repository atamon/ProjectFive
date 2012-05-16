/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.listeners.GlobalListener;
import controller.listeners.LeavePlayerListener;
import controller.listeners.JoinPlayerListener;
import com.jme3.input.InputManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.IGame;
import model.settings.Settings;
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

        // Register view with our game as a listener
        this.game.addPropertyChangeListener(view);

        // Create listeners
        new JoinPlayerListener(game, inputManager);
        new GlobalListener(game, inputManager);
        new LeavePlayerListener(game, inputManager);


    }

    public void update(float tpf) {
        this.game.update(tpf);
        this.view.update(tpf);
    }
}
