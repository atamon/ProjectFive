/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import math.Vector;
import model.player.Player;
import model.tools.Settings;
import model.visual.Battlefield;
import model.visual.Unit;

/**
 *
 * @author victorlindhe
 */
public class PiratePlayerModel implements PlayerModel {
    
    // A game is never startable without 2 players at this setVelocity
    public static final int VALID_PLAYER_AMOUNT = 2;
    public static final int LAST_MAN_STANDING = 1;
    
    private final Battlefield battlefield;
    private final Map<Integer, Player> playerMap = new HashMap<Integer, Player>();
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public PiratePlayerModel(Battlefield battlefield) {
        this.battlefield = battlefield;
    }
    
    public void removePlayer(int id) {
        if (!playerMap.containsKey(id) || playerMap.get(id) == null) {
            throw new RuntimeException("ERROR: Tried to remove invalid player: " + id);
        }
        Unit unit = playerMap.get(id).getUnit();
        unit.setPosition(Vector.NONE_EXISTANT);
        unit.announceRemoval();
        battlefield.removeFromBattlefield(unit);
        playerMap.remove(id);
    }
    
    public boolean hasPlayer(int id) {
        return playerMap.containsKey(id);
    }
    
    public Player getPlayer(int playerID) {
        Player player = this.playerMap.get(playerID);
        if (player == null) {
            throw new IllegalArgumentException("ERROR getPlayer: player with id "
                    + playerID + " does not exist! ;/");
        }
        return player;
    }
    
    public void createPlayer(int id) {
        if (playerMap.get(id) != null) {
            System.out.println("Warning!: playerMap had object: " + playerMap.get(id) + " set to supplied key");
            throw new RuntimeException("AddPlayer: player with id: " + id + " does already exist! Sorry :(");
        }
        Player player = new Player(id);
        player.addPropertyChangeListener(battlefield);
        this.playerMap.put(id, player);
        
        // Add unit
        Vector position = Battlefield.getStartingPosition(id, battlefield.getSize());
        Vector direction = Battlefield.getStartingDir(id);
        int unitSize = Settings.getInstance().getSetting("unitSize");
        Unit unit = new Unit(position,
                direction,
                new Vector(unitSize, unitSize, unitSize),
                Settings.getInstance().getSetting("unitMass"));
        player.setUnit(unit);
        
        this.battlefield.addToBattlefield(unit);
        
        // Let listeners (views) know that we've created a player
        this.pcs.firePropertyChange("Player Created", null, player);
    }
    
    private void placeUnit(int id, Vector position, Vector direction) {
        Unit unit = this.playerMap.get(id).getUnit();
        unit.setPosition(position);
        unit.setDirection(direction);
    }
    
    /**
     * Makes sure all units are at the default starting state. This includes,
     * position, direction, steering, steeringDirection, HP
     */
    public void resetUnits() {
        Collection<Player> players = playerMap.values();
        for (Player player : players) {
            int id = player.getId();
            this.placeUnit(player.getId(),
                    Battlefield.getStartingPosition(id, battlefield.getSize()),
                    Battlefield.getStartingDir(id));
            Unit unit = player.getUnit();
            unit.setIsAccelerating(false);
            unit.setHitPoints(unit.getHitPointsMax());
            unit.removePowerUp();
        }
    }

    public Map<Integer, Player> getPlayerMap() {
        return playerMap;
    }

    public void addPropertyChangeListener(PropertyChangeListener pl) {
        this.pcs.addPropertyChangeListener(pl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pl) {
        this.pcs.removePropertyChangeListener(pl);
    }
    
    public boolean gameOver() {
        return lookForDeadUnits();
    }

    private boolean lookForDeadUnits() {
        // Check for Units with 0 HP
        Collection<Player> players = playerMap.values();
        for (Player player : players) {
            Unit unit = player.getUnit();
            if (unit.getHitPoints() <= 0
                    && !unit.isDeadAndBuried()) {
                unit.hide();
                return lookForLastManStanding(players);
            }
        }
        return false;
    }

    private boolean lookForLastManStanding(Collection<Player> players) {
        int alivePlayers = 0;
        for (Player player : players) {
            if (!player.getUnit().isDeadAndBuried()) {
                alivePlayers++;
            }
        }
        if (alivePlayers == LAST_MAN_STANDING) {
            return true;
        }
        return false;
    }
    
    public void haltPlayers() {
        for (Player player : playerMap.values()) {
            player.getUnit().halt();
        }
    }
}
