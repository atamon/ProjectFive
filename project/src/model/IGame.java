/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.util.List;

/**
 *
 * @author Victor Lindhé
 */
public interface IGame {
    public void placeUnit(int id, Vector vector);
    public void acceleratePlayerUnit(int id, boolean accel);
    public void steerPlayerUnit(Direction direction, int playerID, float tpf);
    public int getNbrOfPlayers();
    public void startRound();
    public void update(float tpf);
    public List<Player> getPlayers();
    public void addUnitListener(int playerID, PropertyChangeListener pl);
    public void removeUnitListener(int playerID, PropertyChangeListener pl);
    public List<Player> getPlayers();
    public Vector getBattlefieldSize();
}
