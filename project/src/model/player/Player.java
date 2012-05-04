package model.player;

import math.Vector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import model.tools.Settings;
import model.visual.CannonBall;
import model.visual.Unit;

/**
 * A simple class thats meant to bind a unit for each player.
 *
 * @author John Hult @tested Victor Lindhé @modified Victor Lindhé
 */
public class Player {

    private final int playerId;
    private Unit playerUnit;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Creates a player with a specific number 1-4.
     *
     * @param int playerNumber
     */
    public Player(int playerID) {
        this.playerId = playerID;
    }

    /**
     * Gets the unit for a specific player.
     *
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
     *
     * @param Unit boat
     */
    public void setUnit(Unit boat) {
        if (boat != null) {
            this.playerUnit = boat;
        }
    }

    public void fireLeft() {
        Vector ballDirection = new Vector(getUnitDirection().getZ(), 0,
                getUnitDirection().getX() * -1);
        this.fire(ballDirection);
    }

    public void fireRight() {

        Vector ballDirection = new Vector(getUnitDirection().getZ() * -1, 0,
                getUnitDirection().getX());
        this.fire(ballDirection);
    }

    private void fire(Vector direction) {
        CannonBall cBall = new CannonBall(getUnitPosition(),
                                          direction,
                                          new Vector(0.1f, 0, 0.1f),
                                          0.1f,
                                          (float)(Settings.getInstance().getSetting("cannonBallMass")),
                                          (float)(Settings.getInstance().getSetting("cannonBallSpeed")));
        this.pcs.firePropertyChange("CannonBall Created", null, cBall);
    }

    /**
     * Accelerates this player's unit.
     *
     * @param accelUp Should unit accelerate up or retardate?
     */
    public void accelerateUnit(boolean accelUp) {
        if (this.playerUnit != null) {
            this.playerUnit.setIsAccelerating(accelUp);
        }
    }

    public Vector getUnitDirection() {
        return this.playerUnit.getDirection();
    }

    public void setUnitPosition(float x, float y, float z) {
        this.playerUnit.setPosition(x, y, z);
    }

    /**
     * Updates the units position with a
     */
    public void updateUnitPosition(float tpf) {
        this.playerUnit.update(tpf);
    }

    /**
     * Steers a unit.
     *
     * @param dir Direction
     * @param tpf Time per frame
     */
    public void steerUnitClockWise(boolean bool) {
        if (this.playerUnit != null) {
            this.playerUnit.steerClockWise(bool);
        }
    }

    public void steerUnitAntiClockWise(boolean bool) {
        if (this.playerUnit != null) {
            this.playerUnit.steerAntiClockWise(bool);
        }
    }

    /**
     * Gets the number of the player.
     *
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
        if (this.playerUnit != null) {
            return "Player: " + this.playerId + " Unit: " + this.playerUnit.toString();
        } else {
            return "Player: " + this.playerId + " Unit: NONE";
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

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        this.pcs.removePropertyChangeListener(pcl);
    }
}
