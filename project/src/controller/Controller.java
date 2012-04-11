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
        this.jme3 = jme3;
        this.view = view;
        this.game = game;
        
        this.game.addPropertyChangeListener(view);
        
        this.jme3.getInputManager().clearMappings();
        this.inputManager = jme3.getInputManager();
        
        ActionListener globalListener = new GlobalKeyListener(game, inputManager);
        this.view.createScene();
        this.game.startRound();
    }
    
    public void update(float tpf) {
        this.game.update(tpf);
    }
    
}
