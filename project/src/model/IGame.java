/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;

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
    public void addUnitListener(int playerID, PropertyChangeListener pl);
    public void removeUnitListener(int playerID, PropertyChangeListener pl);
}
