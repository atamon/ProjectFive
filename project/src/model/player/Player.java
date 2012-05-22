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
 **/
public class Player implements IObservable {
    
    public static final Player NONE = new Player();

    public final static List<Color> PLAYER_COLORS = new ArrayList<Color>() {

        {
            add(Color.CYAN);    // player 0
            add(Color.MAGENTA); // -''- 1
            add(Color.ORANGE);  // 2
            add(Color.PINK);    // 3
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
        color = (PLAYER_COLORS.size() >= playerID) ? // player should be created with any id
                PLAYER_COLORS.get(playerID) : Color.WHITE; // but only id 0-3 have color that isn't white

    }
    
    private Player() {
        playerId = -1;
        color = Color.RED;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Gets the unit for a specific player.
     *
     * @return Unit
     */
    public Unit getUnit() {
        return playerUnit;
    }

    /**
     * Sets the unit of the boat to a specific player.
     *
     * @param Unit boat
     */
    public void setUnit(Unit boat) {
        if (boat != null) {
            playerUnit = boat;
        }
    }

    public void increaseFirePowerLeft(float value) {
        if (value < 0) {
            throw new IllegalArgumentException("ERROR: Tried to increase firepower with negative value! Exiting");
        }
        firePowerLeft += value;

        if (firePowerLeft > 2) {
            firePowerLeft = Settings.getInstance().getSetting("cannonBallMaximumFirePower");
        }
    }

    public void increaseFirePowerRight(float value) {
        if (value < 0) {
            throw new IllegalArgumentException("ERROR: Tried to increase firepower with negative value! Exiting");
        }
        firePowerRight += value;

        if (firePowerRight > 2) {
            firePowerRight = Settings.getInstance().getSetting("cannonBallMaximumFirePower");
        }
    }

    // TODO COMBINE FIRELEFT AND FIRERIGHT INTO ONE!
    public void fireLeft() {
        Vector unitDirection = playerUnit.getDirection();
        Vector ballDirection = new Vector(unitDirection.getZ(), 0,
                unitDirection.getX() * -1);
        fire(ballDirection, firePowerLeft);
        firePowerLeft = Settings.getInstance().getSetting("cannonBallMinimumFirePower");
    }

    public void fireRight() {
        Vector unitDirection = playerUnit.getDirection();
        Vector ballDirection = new Vector(unitDirection.getZ() * -1, 0,
                unitDirection.getX());
        fire(ballDirection, firePowerRight);
        firePowerRight = Settings.getInstance().getSetting("cannonBallMinimumFirePower");
    }

    private void fire(Vector direction, float firePower) {
        // Magical 10f is to reduce size from int >= 1 in settings
        float size = Settings.getInstance().getSetting("cannonBallSize") / 10f;

        CannonBall cBall = new CannonBall(getCannonBallPos(direction),
                direction,
                new Vector(size, size, size),
                (float) (Settings.getInstance().getSetting("cannonBallMass")),
                (float) (Settings.getInstance().getSetting("cannonBallSpeed")) * firePower, playerUnit);

        pcs.firePropertyChange("CannonBall Created", null, cBall);
        playerUnit.reload(Settings.getInstance().getSetting("fireDelay"));
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
        if (playerUnit != null) {
            playerUnit.setIsAccelerating(accelUp);
        }
    }

    /**
     * Steers a unit.
     *
     * @param dir Direction
     * @param tpf Time per frame
     */
    public void steerUnitClockWise(boolean bool) {
        if (playerUnit != null) {
            playerUnit.steerClockWise(bool);
        }
    }

    public void steerUnitAntiClockWise(boolean bool) {
        if (playerUnit != null) {
            playerUnit.steerAntiClockWise(bool);
        }
    }

    /**
     * Gets the number of the player.
     *
     * @return
     */
    public int getId() {
        return playerId;
    }

    public boolean canUnitFire() {
        return playerUnit.canFire();
    }

    public void addUnitListener(PropertyChangeListener pl) {
        playerUnit.addPropertyChangeListener(pl);
    }

    public void removeUnitListener(PropertyChangeListener pl) {
        playerUnit.removePropertyChangeListener(pl);
    }

    @Override
    public String toString() {
        if (playerUnit != null) {
            return "Player: " + playerId + " Unit: " + playerUnit.toString();
        } else {
            return "Player: " + playerId + " Unit: NONE";
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
        if (playerId != other.playerId) {
            return false;
        }
        if (playerUnit != other.playerUnit && (playerUnit == null || !playerUnit.equals(other.playerUnit))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + playerId;
        hash = 73 * hash + (playerUnit != null ? playerUnit.hashCode() : 0);
        return hash;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }
}
