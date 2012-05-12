/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Map;
import math.Vector;
import model.player.Player;
import model.tools.IObservable;

/**
 *
 * @author victorlindhe
 */
public interface PlayerModel extends IObservable {
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
