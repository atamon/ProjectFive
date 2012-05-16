package model.player;

import java.awt.Color;
import math.Vector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import observable.IObservable;
import model.settings.Settings;
import model.visual.CannonBall;
import model.visual.Unit;

/**
 * A simple class thats meant to bind a unit for each player.
 *
 * @author John Hult @tested Victor Lindhé @modified Victor Lindhé
 */
public class Player implements IObservable {

    public final static List<Color> PLAYER_COLORS = new ArrayList<Color>() {

        {
            this.add(Color.CYAN);    // 0
            this.add(Color.MAGENTA); // 1
            this.add(Color.ORANGE);  // 2
            this.add(Color.PINK);    // 3
        }
    };
    private final int playerId;
    private Unit playerUnit;
    private float firePowerLeft = Settings.getInstance().getSetting("cannonBallMinimumFirePower");
    private float firePowerRight = Settings.getInstance().getSetting("cannonBallMinimumFirePower");
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final Color color;

    /**
     * Creates a player with a specific number 1-4.
     *
     * @param int playerNumber
     */
    public Player(final int playerID) {
        this.playerId = playerID;
        this.color = (PLAYER_COLORS.size() >= playerID) ? // player should be created with any id
                PLAYER_COLORS.get(playerID) : Color.WHITE; // but only id 0-3 have color

    }

    public Color getColor() {
        return this.color;
    }

    /**
     * Gets the unit for a specific player.
     *
     * @return Unit
     */
    public Unit getUnit() {
        return this.playerUnit;
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

    public void increaseFirePowerLeft(float value) {
        System.out.println("Value: " + value);
        if (value < 0) {
            throw new IllegalArgumentException("ERROR: Tried to increase firepower with negative value! Exiting");
        }
        firePowerLeft += value;

        System.out.println("FirePower: " + firePowerLeft);

        if (firePowerLeft > 2) {
            firePowerLeft = Settings.getInstance().getSetting("cannonBallMaximumFirePower");
        }
    }

    public void increaseFirePowerRight(float value) {
        System.out.println("Value: " + value);
        if (value < 0) {
            throw new IllegalArgumentException("ERROR: Tried to increase firepower with negative value! Exiting");
        }
        firePowerRight += value;

        System.out.println("FirePower: " + firePowerRight);

        if (firePowerRight > 2) {
            firePowerRight = Settings.getInstance().getSetting("cannonBallMaximumFirePower");
        }
    }

    // TODO COMBINE FIRELEFT AND FIRERIGHT INTO ONE!
    public void fireLeft() {
        Vector unitDirection = playerUnit.getDirection();
        Vector ballDirection = new Vector(unitDirection.getZ(), 0,
                unitDirection.getX() * -1);
        this.fire(ballDirection, firePowerLeft);
        firePowerLeft = Settings.getInstance().getSetting("cannonBallMinimumFirePower");
    }

    public void fireRight() {
        Vector unitDirection = playerUnit.getDirection();
        Vector ballDirection = new Vector(unitDirection.getZ() * -1, 0,
                unitDirection.getX());
        this.fire(ballDirection, firePowerRight);
        firePowerRight = Settings.getInstance().getSetting("cannonBallMinimumFirePower");
    }

    private void fire(Vector direction, float firePower) {
        // Magical 10f is to reduce size from int >= 1 in settings
        float size = Settings.getInstance().getSetting("cannonBallSize") / 10f;

        CannonBall cBall = new CannonBall(this.getCannonBallPos(direction),
                direction,
                new Vector(size, size, size),
                (float) (Settings.getInstance().getSetting("cannonBallMass")),
                (float) (Settings.getInstance().getSetting("cannonBallSpeed")) * firePower, this.playerUnit);

        this.pcs.firePropertyChange("CannonBall Created", null, cBall);
        this.playerUnit.reload(Settings.getInstance().getSetting("fireDelay"));
    }

    private Vector getCannonBallPos(Vector ballDir) {
        Vector pos = new Vector(playerUnit.getPosition());
        Vector dir = new Vector(ballDir);
        dir.normalize();
        dir.mult(5.0f);
        Vector newPosition = new Vector(pos.getX() + dir.getX(), pos.getY() + dir.getY(), pos.getZ() + dir.getZ());
        return newPosition;
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

    public boolean canUnitFire() {
        return playerUnit.canFire();
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
