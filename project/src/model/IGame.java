/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.player.Player;
import model.round.RoundState;
import model.tools.IObservable;
import model.tools.Vector;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 *
 * @author Victor Lindhé
 */
public interface IGame extends IObservable {
    public void setSettings(Map <String, Integer> settings);
    public void clean();
    public void start();
    public void createPlayer(int id);
    public void acceleratePlayerUnit(int id, boolean accel);
    public void nextRound();
    public boolean hasValidAmountOfPlayers();
    public void update(float tpf);
    public GameState getState();
    public RoundState getRoundState();
    public Player getPlayer(int playerID);
    public void addUnitListener(int playerID, PropertyChangeListener pl);
    public void removeUnitListener(int playerID, PropertyChangeListener pl);
    public Vector getBattlefieldSize();
    public Vector getBattlefieldCenter();
    public Vector getBattlefieldPosition();
    public void fireLeft(Player player);
    public void fireRight(Player player);
    public void switchPauseState();
    public void removePlayer(int id);
    public boolean hasPlayer(int id);
}
