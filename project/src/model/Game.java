package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.player.Player;
import model.round.*;
import model.tools.Settings;
import math.Vector;
import model.visual.Battlefield;
import model.visual.CannonBall;
import model.visual.Unit;

/**
 * Represents a Game consisting of rounds and players that compete to win!
 *
 * @author Anton Lindgren @modified by John Hult, Victor Lindhé
 */
public class Game implements IGame {

    // A game is never startable without 2 players at this setVelocity
    public static final int VALID_PLAYER_AMOUNT = 2;
    public static final int LAST_MAN_STANDING = 1;
    // Instances
    private final Battlefield battlefield;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private GameState gameState = GameState.INACTIVE;
    private final Map<Integer, Player> playerMap = new HashMap<Integer, Player>();
    private final Map<Integer, Round> playedRounds = new HashMap<Integer, Round>();
    private Round currentRound;

    /**
     * Create a game with given parameters. A game consists of a number of
     * rounds containing a given amount of players. The Game class starts new
     * rounds and keeps track of player-score and rules set for this game and
     * its rounds as well as speaks to controller and view.
     *
     * @param battlefield The battlefield passed from Model which we'll play on.
     */
    public Game(final Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    /**
     * Creates a Game with a default 100x100 Battlefield, 1 round and 1 player.
     */
    public Game() {
        this(new Battlefield());
    }

    public GameState getState() {
        return this.gameState;
    }

    public RoundState getRoundState() {
        if (currentRound == null) {
            return RoundState.NONE_EXISTANT;
        }
        return this.currentRound.getState();
    }

    public Vector getBattlefieldCenter() {
        return this.battlefield.getCenter();
    }

    /**
     * Returns the size of the logical battlefield
     *
     * @return The size as a Vector
     */
    public void update(float tpf) {
        if (gameState == GameState.ACTIVE
                && currentRound.getState() == RoundState.PLAYING) {
            lookForDeadUnits();

            this.battlefield.update(tpf);

        }
    }

    public Vector getBattlefieldPosition() {
        return battlefield.getPosition();
    }

    public void removePlayer(int id) {
        if (playerMap.get(id) == null || !playerMap.containsKey(id)) {
            throw new RuntimeException("ERROR: Tried to remove invalid player: " + id);
        }
        playerMap.get(id).getUnit().setPosition(Vector.NONE_EXISTANT);
        playerMap.remove(id);
    }

    public boolean hasPlayer(int id) {
        return playerMap.containsKey(id);
    }

    /**
     * Returns the size of the logical battlefield
     *
     * @return The size as a Vector
     */
    public Vector getBattlefieldSize() {
        return battlefield.getSize();
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
                Settings.getInstance().getSetting("unitMass"),
                id);
        player.setUnit(unit);
        
        this.battlefield.addToBattlefield(unit);
        
        // Let listeners (views) know that we've created a player
        this.pcs.firePropertyChange("Player Created", null, player);
    }

    /**
     * Places a unit for the specified player on the given position with the
     * given direction.
     *
     * @param id The playerID used to locate which unit to be placed.
     * @param position The position to place the unit on.
     * @param direction The direction the unit should use.
     */
    private void placeUnit(int id, Vector position, Vector direction) {
        Unit unit = this.playerMap.get(id).getUnit();
        unit.setPosition(position);
        unit.setDirection(direction);
    }

    @Override
    public void addUnitListener(int playerID, PropertyChangeListener pl) {
        this.playerMap.get(playerID).addUnitListener(pl);
    }

    @Override
    public void removeUnitListener(int playerID, PropertyChangeListener pl) {
        this.playerMap.get(playerID).removeUnitListener(pl);
    }

    /**
     *
     * @param settings
     */
    public void setSettings(Map<String, Integer> settings) {
        Settings.getInstance().loadSettings(settings);
    }

    public boolean hasValidAmountOfPlayers() {
        return playerMap.size() >= VALID_PLAYER_AMOUNT;
    }

    public void start() {
        gameState = GameState.ACTIVE;
        nextRound();
    }

    /**
     * Start a new round.
     */
    @Override
    public void nextRound() {

        this.battlefield.clearForNewRound();
        this.currentRound = new Round();
        try {
            this.currentRound.start();
        } catch (RoundAlreadyStartedException e) {
            System.out.println("WARNING: " + e.getMessage());
            return;
        }

        // Since we are not sure the units are correctly placed we do so now
        this.resetUnits();
        this.haltPlayers();
        System.out.println("Round started!");
    }

    /**
     * (Un)Pauses the game.
     */
    @Override
    public void switchPauseState() {
        RoundState roundState = this.getRoundState();
        if (gameState == GameState.ACTIVE && (roundState == RoundState.PLAYING
                || roundState == RoundState.PAUSED)) {
            if (this.getRoundState() == RoundState.PAUSED) {
                currentRound.unPause();
            } else {
                currentRound.pause();
            }
        }
    }

    private void lookForDeadUnits() {
        // Check for Units with 0 HP
        Collection<Player> players = playerMap.values();
        for (Player player : players) {
            Unit unit = player.getUnit();
            if (unit.getHitPoints() <= 0
                    && !unit.isDeadAndBuried()) {
                unit.hide();
                lookForLastManStanding(players);
            }
        }
    }

    private void lookForLastManStanding(Collection<Player> players) {
        int alivePlayers = 0;
        for (Player player : players) {
            if (!player.getUnit().isDeadAndBuried()) {
                alivePlayers++;
            }
        }
        if (alivePlayers == LAST_MAN_STANDING) {
            this.endRound();
        }
    }

    /**
     * Call when the round ends, ie one player is last man standing or the clock
     * runs out. It delivers statistics from played round, sets score to the
     * winner and clears the battlefield.
     */
    public void endRound() {
        try {
            this.currentRound.end(findRoundWinner());
        } catch (RoundAlreadyEndedException e) {
            System.out.println("WARNING: " + e.getMessage());
            return;
        }

        int roundNumber = playedRounds.size();
        this.playedRounds.put(roundNumber, currentRound);

        System.out.println("This rounds winner is .... "
                + this.currentRound.getWinner());
        if (Settings.getInstance().getSetting("numberOfRounds") <= playedRounds.size()) {
            endGame();
        }
    }

    /**
     * Returns the winner, I.E. the player last man standing.
     */
    private Player findRoundWinner() {
        Player winner = null;
        for (Player player : playerMap.values()) {
            if (player.getUnit().getHitPoints() > 0) {
                if (winner != null) {
                    throw new WinnerNotFoundException("Several players still alive, no winner declared!");
                }
                winner = player;
            }
        }
        if (winner == null) {
            throw new WinnerNotFoundException("No winner for the active round was found!");
        }
        return winner;
    }

    private void endGame() {
        // TODO Replace with real GUI
        System.out.println("Game over!");

        // Calculate statistics
        // TODO Add real GUI-listeners to this.
        Map<Player, Integer> playerWins = new HashMap<Player, Integer>();
        for (Player player : playerMap.values()) {
            playerWins.put(player, 0);
        }

        for (Round round : playedRounds.values()) {
            Player roundWinner = round.getWinner();
            Integer wonRounds = playerWins.get(roundWinner);
            if (wonRounds == null) {
                wonRounds = 0;
            }

            playerWins.put(roundWinner, wonRounds + 1);

        }

        for (Player player : playerWins.keySet()) {
            System.out.println(player.getId() + " won " + playerWins.get(player) + "rounds!");
        }
        // We have ended the game so it is now STATS
        gameState = GameState.STATS;
        // Reset units and disable moving, firing and all.
        this.resetUnits();
    }

    /**
     * Makes sure all units are at the default starting state. This includes,
     * position, direction, steering, steeringDirection, HP
     */
    private void resetUnits() {
        Collection<Player> players = playerMap.values();
        for (Player player : players) {
            int id = player.getId();
            this.placeUnit(player.getId(),
                    Battlefield.getStartingPosition(id, battlefield.getSize()),
                    Battlefield.getStartingDir(id));
            Unit unit = player.getUnit();
            unit.setIsAccelerating(false);
            unit.setHitPoints(unit.getHitPointsMax());
            unit.setSteerAngle(Settings.getInstance().getSetting("steerAngle"));
        }
    }

    @Override
    /**
     * All stats removed but joined players are kept. We now have a "clean"
     * game.
     */
    public void clean() {
        playedRounds.clear();
        gameState = GameState.INACTIVE;
    }

    private void haltPlayers() {
        for (Player player : playerMap.values()) {
            player.getUnit().halt();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener pl) {
        this.pcs.addPropertyChangeListener(pl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pl) {
        this.pcs.removePropertyChangeListener(pl);
    }
}
