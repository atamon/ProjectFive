/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    public void nextRound();
    public boolean gameIsActive();
    public void createPlayer(int id);
    public boolean roundStarted();
    public void acceleratePlayerUnit(int id, boolean accel);
    public int getNbrOfPlayers();
    public void startRound();
    public boolean hasValidAmountPlayers();
    public void update(float tpf);
    public Player getPlayer(int playerID);
    public void addUnitListener(int playerID, PropertyChangeListener pl);
    public void removeUnitListener(int playerID, PropertyChangeListener pl);
    public float getBattlefieldSize();
    public Vector getBattlefieldCenter();
    public Vector getBattlefieldPosition();
    public void switchPauseState();
}
