package model;

import unit.Unit;
import util.Direction;
import util.Vector;
import java.beans.PropertyChangeListener;

/**
 * A simple class thats meant to bind a unit for each player.
 * @author John Hult
 * @tested Victor Lindhé
 * @modified Victor Lindhé
 */
public class Player {
    private final int playerId;
    private Unit playerUnit;
    private int score;
    
    /**
     * Creates a player with a specific number 1-4.
     * @param int playerNumber
     */
    public Player(int playerNumber) {
        this.playerId = playerNumber;
        this.score = 0;
    }
    
    /**
     * Gets the unit for a specific player.
     * @return Unit
     */
    public Unit getUnit() {
        return this.playerUnit;
    }
    
    public Vector getUnitPosition() {
        return this.playerUnit.getPosition();
    }
    
    /**
     * Sets the unit of the boat to a specific player.
     * @param Unit boat 
     */
    public void setUnit(Unit boat) {
        if(boat != null) {
            this.playerUnit = boat;
        }
    }
    
    /**
     * Accelerates this player's unit.
     * @param accelUp Should unit accelerate up or retardate?
     */
    public void accelerateUnit(boolean accelUp) {
        if(this.playerUnit != null) {
            this.playerUnit.setIsAccelerating(accelUp);
        }
    }
    
    /**
     * Increments the score
     */
    public void incrementScore() {
        this.score++;
    }
    
    /**
     * Updates the units position with a 
     */
    public void updateUnitPosition(float tpf) {
        this.playerUnit.updateUnit(tpf);
    }
    
    /**
     * Getter for the score.
     * @return int
     */
    public int getScore() {
        return this.score;
    }
    
    /**
     * Steers a unit.
     * @param dir Direction
     * @param tpf Time per frame
     */
    public void steerUnit(Direction dir, float tpf) {
        if(this.playerUnit != null) {
            this.playerUnit.steer(dir, tpf);
        }
    }
    
    /**
     * Gets the number of the player.
     * @return 
     */
    public int getId() {
        return this.playerId;
    }
    
    public void addUnitListener(PropertyChangeListener pl) {
        this.playerUnit.addPropertyChangeListener(pl);
    }
    
    public void removeUnitListener(PropertyChangeListener pl) {
        this.playerUnit.removePropertyChangeListener(pl);
    }
    
    @Override
    public String toString() {
        if(this.playerUnit != null) {
            return "Player: " + this.playerId + "Unit:" + this.playerUnit.toString();
        } else {
            return "Player: " + this.playerId + "Unit: NONE";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (this.playerId != other.playerId) {
            return false;
        }
        if (this.playerUnit != other.playerUnit && (this.playerUnit == null || !this.playerUnit.equals(other.playerUnit))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.playerId;
        hash = 73 * hash + (this.playerUnit != null ? this.playerUnit.hashCode() : 0);
        return hash;
    }
    
    
}
