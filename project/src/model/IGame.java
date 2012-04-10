/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.util.IObservable;
import model.util.Direction;
import model.util.Vector;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Victor Lindhé
 */
public interface IGame extends IObservable {
    public void createPlayer(int id);
    public void placeUnit(int id, Vector vector);
    public void acceleratePlayerUnit(int id, boolean accel);
    public void steerPlayerUnit(Direction direction, int playerID, float tpf);
    public int getNbrOfPlayers();
    public void startRound();
    public void update(float tpf);
    public Player getPlayer(int playerID);
    public void addUnitListener(int playerID, PropertyChangeListener pl);
    public void removeUnitListener(int playerID, PropertyChangeListener pl);
    public Vector getBattlefieldSize();
}
