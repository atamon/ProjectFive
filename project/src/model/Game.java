package model;

import model.tools.Settings;
import model.visual.Battlefield;
import model.tools.Vector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.visual.Unit;

/**
 * Represents a Game consisting of rounds and players that compete to win!
 * @author Anton Lindgren
 * @modified by John Hult, Victor Lindhé
 */
public class Game implements IGame {

    // A game is never startable without 2 players at this point
    public static final int VALID_PLAYER_AMOUNT = 2;
    public static final int LAST_MAN_STANDING = 1;
    // Instances
    private final Battlefield battlefield;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final Map<Integer, Player> playerMap = new HashMap<Integer, Player>();
    private final Map<Integer, Round> playedRounds = new HashMap<Integer, Round>();
    
    private Round currentRound;
    private boolean isRunning = false;
    private int numberOfRounds;

    /**
     * Create a game with given parameters.
     * A game consists of a number of rounds containing a given amount of players.
     * The Game class starts new rounds and keeps track of player-score and rules
     * set for this game and its rounds as well as speaks to controller and view. 
     * @param battlefield The battlefield passed from Model which we'll play on.
     */
    public Game(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    /**
     * Creates a Game with a default 100x100 Battlefield, 1 round and 1 player.
     */
    public Game() {
        this(new Battlefield());
    }

    /**
     * @return Returns true if the game has started, IE there is a current round.
     */
    public boolean roundStarted() {
        if (currentRound == null) {
            return false;
        }
        return currentRound.getActiveRound();
    }
    
    public boolean gameIsActive() {
        return !playedRounds.isEmpty();
    } 

    /**
     * Creates and places a unit according to its playerID.
     * @param playerID The playerID making sure we place the unit where the player should start.
     * @return Returns the unit created.
     */
    private Unit createUnit(int playerID) {
        Vector position = Game.getStartingPos(playerID, battlefield.getSize());
        Vector direction = Game.getStartingDir(playerID);
        return new Unit(position, direction);
    }

    /**
     * Updates the running game.
     * @param tpf Time since last update
     */
    public void update(float tpf) {
        if (isRunning) {
            lookForDeadUnits();


            // Update unit-positions
            // TODO Generalize to update moveablepositions
            for (Player player : this.playerMap.values()) {
                player.updateUnitPosition(tpf);
                if (this.isOutOfBounds(player.getUnitPosition())) {
                    this.doMagellanJourney(player);
                }
            }
        }
    }

    private void lookForDeadUnits() {
        // Check for Units with 0 HP
        Collection<Player> players = playerMap.values();
        for (Player player : players) {
            Unit unit = player.getUnit();
            if (unit.getHitPoints() <= 0
                    && !unit.getPosition().equals(Vector.NONE_EXISTANT)) {

                unit.setIsAccelerating(false);
                unit.setSteerAngle(0);
                unit.setPosition(Vector.NONE_EXISTANT);

                lookForLastManStanding(players);
            }
        }
    }

    private void lookForLastManStanding(Collection<Player> players) {
        int alivePlayers = 0;
        for (Player alivePlayer : players) {
            if (alivePlayer.getUnit().getHitPoints() > 0) {
                alivePlayers++;
            }
        }
        if (alivePlayers == LAST_MAN_STANDING) {
            this.endRound();
        }
    }

    private boolean isOutOfBounds(Vector position) {
        float size = this.getBattlefieldSize();
        return position.getX() < 0
                || position.getX() > size
                || position.getY() < 0
                || position.getY() > size;
    }

    private void doMagellanJourney(Player player) {
        Vector newPosition = new Vector(player.getUnitPosition());
        Vector direction = new Vector(player.getUnitDirection());

        direction.mult(-1.0f);
        newPosition.add(direction);
        while (!isOutOfBounds(newPosition)) {
            newPosition.add(direction);
        }

        direction.mult(-1.0f);
        newPosition.add(direction);
        player.setUnitPosition(newPosition.getX(), newPosition.getY());
    }

    /**
     * Accelerates a player's unit.
     * @param id The player's ID
     * @param tpf Time since last frame
     */
    @Override
    public void acceleratePlayerUnit(int id, boolean accel) {
        this.playerMap.get(id).accelerateUnit(accel);
    }

    public Vector getBattlefieldCenter() {
        return this.battlefield.getCenter();
    }

    /**
     * Returns the size of the logical battlefield
     * @return The size as a Vector
     */
    public float getBattlefieldSize() {
        return battlefield.getSize();
    }

    public Vector getBattlefieldPosition() {
        return battlefield.getPosition();
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
        Unit unit = this.createUnit(id);
        player.setUnit(unit);

        // Keep track of the unit by its id
        this.playerMap.put(id, player);

        // Let listeners (views) know that we've created a player
        this.pcs.firePropertyChange("Player Created", null, player);
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
    
    public void nextRound() {
        // Check if the game still has more rounds to play
        if (Settings.getInstance().getSetting("numberOfRounds") > playedRounds.size()) {
            startRound();
        } else {
        // Else we stop playing and head for stats and menu
            
            System.out.println("Game over!");
            
            printStats();
            
            playedRounds.clear();
            
            
        }
    }
    
    /**
     * Start a new round.
     * Sets position of all players
     */
    @Override
    public void startRound() {
        // Since we are not sure the units are correctly placed we do so now
        this.resetUnits();
        this.haltPlayers();

        this.isRunning = true;
        this.currentRound = new Round();
        this.currentRound.start();
        System.out.println("Round started!");

    }

    /**
     * Makes sure all units are at the default starting state.
     * This includes, position, direction, steering, steeringDirection, HP
     */
    private void resetUnits() {
        Collection<Player> players = playerMap.values();
        for (Player player : players) {
            int id = player.getId();
            this.placeUnit(player.getId(),
                    Game.getStartingPos(id, battlefield.getSize()),
                    Game.getStartingDir(id));
            Unit unit = player.getUnit();
            unit.setIsAccelerating(false);
            unit.setHitPoints(unit.getHitPointsMax());
            unit.setSteerAngle(Settings.getInstance().getSetting("steerAngle"));
        }
    }

    /**
     * Places a unit for the specified player on the given position with the given direction.
     * @param id The playerID used to locate which unit to be placed.
     * @param position The position to place the unit on.
     * @param direction The direction the unit should use.
     */
    private void placeUnit(int id, Vector position, Vector direction) {
        Unit unit = this.playerMap.get(id).getUnit();
        unit.setPosition(position);
        unit.setDirection(direction);
    }

    /**
     * Call when the round ends, ie one player is last man standing or the
     * clock runs out.
     * It delivers statistics from played round, sets score to the winner
     * and clears the battlefield.
     */
    public void endRound() {
        // TODO Handle statistics at the end of each round
        int roundNumber = playedRounds.size();
        this.playedRounds.put(roundNumber, currentRound);
                
        this.currentRound.end(findRoundWinner());
        System.out.println("This rounds winner is .... "
                + this.currentRound.getWinner());

    }

    /**
     * Returns the winner, I.E. the player with the highest score.
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
    
    private void printStats() {
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

            playerWins.put(roundWinner, wonRounds+1);

        }

        for (Player player : playerWins.keySet()) {
            System.out.println(player.getId() + " won " + playerWins.get(player) + "rounds!");
        }
    }

    private void haltPlayers() {
        for (Player player : playerMap.values()) {
            player.getUnit().setSpeed(0);
        }
    }

    /*
     * Returns number of players.
     */
    @Override
    public int getNbrOfPlayers() {
        return this.playerMap.size();
    }

    /**
     * Pauses the game.
     * @param runState 
     */
    @Override
    public void switchPauseState() {
        if (currentRound.getActiveRound() && roundStarted()) {
            this.isRunning = !this.isRunning;
        }
    }

    public boolean hasValidAmountPlayers() {
        return playerMap.size() >= VALID_PLAYER_AMOUNT;
    }

    /**
     * Returns a player's unitposition.
     * @param playerID The player's ID
     * @return A Vector representing the position of a player's unit.
     */
    public Vector getPlayerPosition(int playerID) {
        return playerMap.get(playerID).getUnitPosition();
    }

    public void addPropertyChangeListener(PropertyChangeListener pl) {
        this.pcs.addPropertyChangeListener(pl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pl) {
        this.pcs.removePropertyChangeListener(pl);
    }

    public static Vector getStartingPos(int playerID, float bfSize) {
        Vector position;
        switch (playerID) {
            case 0:
                position = new Vector(bfSize, bfSize);
                break;
            case 1:
                position = new Vector(0, 0);
                break;
            case 2:
                position = new Vector(bfSize, 0);
                break;
            case 3:
                position = new Vector(0, bfSize);
                break;
            default:
                throw new IllegalArgumentException("ERROR: Tried to get startingPos of invalid player with ID: "
                        + playerID);
        }
        return position;
    }

    public static Vector getStartingDir(int playerID) {
        Vector direction;
        switch (playerID) {
            case 0:
                direction = new Vector(-1, -1);
                break;
            case 1:
                direction = new Vector(1, 1);
                break;
            case 2:
                direction = new Vector(-1, 1);
                break;
            case 3:
                direction = new Vector(1, -1);
                break;
            default:
                throw new IllegalArgumentException("ERROR: Tried to get startingPos of invalid player with ID: "
                        + playerID);
        }
        return direction;
    }
}
