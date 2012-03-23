
package Model;

/**
 * A simple class thats meant to bind a unit for each player.
 * @author John Hult
 * Tested and modified by Victor Lindhé
 */
public class Player {
    private final int playerId;
    
    private Unit playerUnit;
    
    /**
     * Creates a player with a specific number 1-4.
     * @param int playerNumber
     */
    public Player(int playerNumber) {
        this.playerId = playerNumber;
    }
    
    /**
     * Gets the unit for a specific player.
     * @return Unit
     */
    public Unit getUnit() {
        return this.playerUnit;
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
    
    public void steerUnit(Direction dir, float tpf) {
        if(dir == Direction.ANTICLOCKWISE) {
            
        }
    }
    
    /**
     * Gets the number of the player.
     * @return 
     */
    public int getId() {
        return this.playerId;
    }
    
    @Override
    public String toString() {
        return ("Player: " + this.playerId + "Unit:" + this.playerUnit.toString());
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
