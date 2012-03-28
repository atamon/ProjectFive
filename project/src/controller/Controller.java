/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Game;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import model.IGame;
import model.Player;
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
    private List<PlayerAdapter> playerAdapters;
    
    public Controller(SimpleApplication jme3, View view, IGame game) {
        this.jme3 = jme3;
        this.view = view;
        this.game = game;
        
        this.jme3.getInputManager().clearMappings();
        this.game.placeUnit(1, new Vector(0,0));
        
        this.playerAdapters = new ArrayList<PlayerAdapter>();
        List<Player> players = this.game.getPlayers();
        Iterator<Player> playerIterator = players.listIterator();
        while(playerIterator.hasNext()) {
            PlayerAdapter playAdapt = new PlayerAdapter(playerIterator.next());
            playAdapt.setKeyBoardListener(new KeyBoardListener(playAdapt, 
                                                  this.jme3.getInputManager()));
            this.playerAdapters.add(playAdapt);
        }
        
        this.view.createScene();
        this.game.startRound();
    }
    
    public void update(float tpf) {
        this.game.update(tpf);
    }
    
}
