/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Game;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;

/**
 * A class to represent a Controller
 * @author Victor Lindhé
 */
public class Controller {
    private Game game;
    private View view;
    private SimpleApplication jme3;
    private double tpf;
    
    public static int[][] keyLayouts = {{KeyInput.KEY_W, KeyInput.KEY_A, KeyInput.KEY_B},
        {KeyInput.KEY_UP, KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT}};
    
    public Controller(SimpleApplication jme3, View view, Game game) {
        this.jme3 = jme3;
        this.view = view;
        this.game = game;
        
        for(int i=0; i<this.game.getNbrOfPlayers(); i++) {
            this.initKeys(i);
        }
    }
    
    // Not finished yet
    private void initKeys(int playerIndex) {
        KeyBoardListener kbListener = new KeyBoardListener(this.game, playerIndex);
        
        this.jme3.getInputManager().addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        this.jme3.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        this.jme3.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        
        this.jme3.getInputManager().addListener(kbListener, "Forward");
        this.jme3.getInputManager().addListener(kbListener, "Left");
        this.jme3.getInputManager().addListener(kbListener, "Right");
    }
    
    private void update(double tpf) {
        this.tpf = tpf;
    }
    
}
