/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.tools.IObservable;
import model.tools.Direction;
import model.tools.Vector;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Victor Lindhé
 */
public interface IGame extends IObservable {
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
