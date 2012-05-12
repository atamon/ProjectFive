package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import model.player.Player;
import model.round.*;
import model.tools.Settings;
import math.Vector;
import model.visual.Battlefield;
import model.visual.Item;
import model.visual.Unit;

/**
 * Represents a Game consisting of rounds and players that compete to win!
 *
 */
public class Game implements IGame {

    
    // A game is never startable without 2 players at this setVelocity
    public static final int VALID_PLAYER_AMOUNT = 2;
    public static final int LAST_MAN_STANDING = 1;
    
    // Instances
    private final Battlefield battlefield;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private GameState gameState = GameState.INACTIVE;

    private final RoundModel roundModel;
    private final PlayerModel playerModel;
    
    private final ItemFactory itemFactory;
    private float itemTimeout = 5f;
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
        this.itemFactory = new ItemFactory();
        this.roundModel = new SimpleRoundModel();
        this.playerModel = new PiratePlayerModel(battlefield);
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
        return roundModel.getRoundState();
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
                && roundModel.getRoundState() == RoundState.PLAYING) {
            if(playerModel.gameOver()) {
                endRound();
            }

            this.battlefield.update(tpf);
            this.itemTimeout -= tpf;
            if(itemTimeout <= 0 ){
                this.createItem();
                this.itemTimeout = 10f;
            }
        }
    }

    private void createItem(){
        Item item = this.itemFactory.createNewItem(this.getBattlefieldSize());
        this.battlefield.addToBattlefield(item);
        pcs.firePropertyChange("Item Created", null, item);
    }
    
    public Vector getBattlefieldPosition() {
        return battlefield.getPosition();
    }

    public void removePlayer(int id) {
        playerModel.removePlayer(id);
    }

    public boolean hasPlayer(int id) {
        return playerModel.hasPlayer(id);
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
        return playerModel.getPlayer(playerID);
    }

    public void createPlayer(int id) {
        playerModel.createPlayer(id);
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
        Unit unit = playerModel.getPlayerMap().get(id).getUnit();
        unit.setPosition(position);
        unit.setDirection(direction);
    }

    @Override
    public void addUnitListener(int playerID, PropertyChangeListener pl) {
        playerModel.getPlayerMap().get(playerID).addUnitListener(pl);
    }

    @Override
    public void removeUnitListener(int playerID, PropertyChangeListener pl) {
        playerModel.getPlayerMap().get(playerID).removeUnitListener(pl);
    }


    public boolean hasValidAmountOfPlayers() {
        return playerModel.getPlayerMap().size() >= VALID_PLAYER_AMOUNT;
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

        battlefield.clearForNewRound();
        roundModel.newRound();
        roundModel.startRound();

        // Since we are not sure the units are correctly placed we do so now
        playerModel.resetUnits();
        playerModel.haltPlayers();
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
                roundModel.unPause();
            } else {
                roundModel.pause();
            }
        }
    }

    /**
     * Call when the round ends, ie one player is last man standing or the clock
     * runs out. It delivers statistics from played round, sets score to the
     * winner and clears the battlefield.
     */
    private void endRound() {
        roundModel.endRound(findRoundWinner());

        System.out.println("This rounds winner is .... "
                + roundModel.getWinner());
        if (Settings.getInstance().getSetting("numberOfRounds") 
                <= roundModel.playedRounds()) {
            endGame();
        }
    }
    
    /**
     * Returns the winner, I.E. the player last man standing.
     */
    private Player findRoundWinner() {
        Player winner = null;
        for (Player player : playerModel.getPlayerMap().values()) {
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
        for (Player player : playerModel.getPlayerMap().values()) {
            playerWins.put(player, 0);
        }

        for (Round round : roundModel.getPlayedRounds()) {
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
        playerModel.resetUnits();
    }
    
    @Override
    /**
     * All stats removed but joined players are kept. We now have a "clean"
     * game.
     */
    public void clean() {
        roundModel.clearPlayedRounds();
        gameState = GameState.INACTIVE;
    }

    public void addPropertyChangeListener(PropertyChangeListener pl) {
        playerModel.addPropertyChangeListener(pl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pl) {
        playerModel.addPropertyChangeListener(pl);
    }
}
