/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Map;
import model.player.Player;
import observable.IObservable;

/**
 *  Interface with necessary methods to control Players.
 **/
public interface IPlayerModel extends IObservable {
    public void removePlayer(int id);
    public boolean hasPlayer(int id);
    public Player getPlayer(int playerID);
    public void createPlayer(int id);
    public void resetUnits();
    public boolean gameOver();
    public void haltPlayers();
    public Player findRoundWinner();
    public Map<Integer, Player> getPlayerMap();
}
