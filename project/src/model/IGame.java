/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.player.Player;
import model.round.RoundState;
import model.tools.IObservable;
import math.Vector;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 *
 * @author Victor Lindhé
 */
public interface IGame extends IObservable {
    public void createPlayer(int id);
    public void removePlayer(int id);
    public Player getPlayer(int playerID);
    public boolean hasPlayer(int id);
    public boolean hasValidAmountOfPlayers();
    public GameState getState();
    public RoundState getRoundState();
    public Vector getBattlefieldSize();
    public Vector getBattlefieldCenter();
    public Vector getBattlefieldPosition();
    public void start();
    public void switchPauseState();
    public void nextRound();
    public void update(float tpf);
    public void clean();
    public void addUnitListener(int playerID, PropertyChangeListener pl);
    public void removeUnitListener(int playerID, PropertyChangeListener pl);
}
