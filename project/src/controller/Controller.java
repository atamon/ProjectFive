/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Game;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import model.IGame;
import model.Vector;
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
    private int nbrOfPlayers;
    
    public static int[][] keyLayouts = {
        {KeyInput.KEY_W, KeyInput.KEY_A, KeyInput.KEY_D},
        {KeyInput.KEY_UP, KeyInput.KEY_LEFT, KeyInput.KEY_RIGHT}};
    
    public Controller(SimpleApplication jme3, View view, IGame game) {
        this.jme3 = jme3;
        this.view = view;
        this.game = game;
        
        this.jme3.getInputManager().clearMappings();

        this.nbrOfPlayers = this.game.getNbrOfPlayers();
        for(int i=0; i<this.nbrOfPlayers; i++) {
            
            this.initKeys(i);
        }
        
        this.view.createScene();
        this.game.startRound();
    }
    
    private void initKeys(int playerIndex) {
        KeyBoardListener kbListener = new KeyBoardListener(this.game, playerIndex);
        
        this.jme3.getInputManager().addMapping("Forward" + (playerIndex), 
                                    new KeyTrigger(keyLayouts[playerIndex][0]));
        this.jme3.getInputManager().addMapping("Left" + (playerIndex), 
                                    new KeyTrigger(keyLayouts[playerIndex][1]));
        this.jme3.getInputManager().addMapping("Right" + (playerIndex), 
                                    new KeyTrigger(keyLayouts[playerIndex][2]));
        
        this.jme3.getInputManager().addListener(kbListener, "Forward" + (playerIndex));
        this.jme3.getInputManager().addListener(kbListener, "Left" + (playerIndex));
        this.jme3.getInputManager().addListener(kbListener, "Right" + (playerIndex));
    }
    
    public void update(float tpf) {
        this.game.update(tpf);
    }
    
}
