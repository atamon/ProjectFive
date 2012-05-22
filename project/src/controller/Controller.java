/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.listeners.GlobalListener;
import controller.listeners.LeavePlayerListener;
import controller.listeners.JoinPlayerListener;
import com.jme3.input.InputManager;
import model.IGame;
import view.View;

/**
 * A class to represent a Controller.
 */
public class Controller {

    private IGame game;
    private View view;
    private InputManager inpManager;

    public Controller(InputManager inpManager, View view, IGame game) {
        // Save instances
        this.view = view;
        this.game = game;
        this.inpManager = inpManager;

        // clear inpManager's pre-defined triggers and keys
        inpManager.clearMappings();

        // Register view with our game as a listener
        game.addPropertyChangeListener(view);

        // Create listeners
        new JoinPlayerListener(game, inpManager);
        new GlobalListener(game, inpManager);
        new LeavePlayerListener(game, inpManager);


    }

    public void update(float tpf) {
        game.update(tpf);
        view.update(tpf);
    }
}
