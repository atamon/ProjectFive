package model;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import model.player.Player;
import model.round.*;
import model.settings.Settings;
import math.Vector;
import model.settings.SettingsLoader;
import model.visual.Battlefield;
import model.visual.Bottle;
import model.visual.Molotov;
import model.visual.StatusBox;

/**
 * Represents a Game consisting of rounds and players that compete to win!
 *
 */
public class Game implements IGame {

    
    // A game is never startable without 2 players at this point
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
        Settings.getInstance().loadSettings(SettingsLoader.readSettings("assets/settings"));

        
        this.battlefield = battlefield;
        this.itemFactory = new ItemFactory();
        roundModel = new SimpleRoundModel();
        playerModel = new PiratePlayerModel(battlefield);
    }

    /**
     * Creates a Game with a default 100x100 Battlefield, 1 round and 1 player.
     */
    public Game() {
        this(new Battlefield());
    }

    public GameState getState() {
        return gameState;
    }

    public RoundState getRoundState() {
        return roundModel.getRoundState();
    }

    public Vector getBattlefieldCenter() {
        return battlefield.getCenter();
    }

    /**
     * Returns the size of the logical battlefield
     *
     * @return The size as a Vector
     */
    public void update(float tpf) {
        if (gameState == GameState.ACTIVE
                && roundModel.getRoundState() == RoundState.PLAYING
                && (roundModel.getCountDown() <= 0 || roundModel.getCountDown() 
                == Settings.getInstance().getSetting("roundDelay"))) {
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
        if (roundModel.getCountDown() > 0) {
            roundModel.setCountDown(roundModel.getCountDown() - tpf);
            pcs.firePropertyChange("Round Countdown", null, roundModel.getCountDown());
        }
        

    }

    private void createItem(){
        Bottle bottle;
        if (Math.random() * 100 < Settings.getInstance().getSetting("molotovPercent")) {
            bottle = new Molotov(ItemFactory.randomizeBottlePosition(battlefield.getSize()));
        } else {
            bottle = itemFactory.createNewItem(getBattlefieldSize());
        }
        battlefield.addToBattlefield(bottle);
        pcs.firePropertyChange("Bottle Created", null, bottle);
        StatusBox.getInstance().message(Color.ORANGE," A strange bottle appeared..");
    }
    
    public Vector getBattlefieldPosition() {
        return battlefield.getPosition();
    }

    public void removePlayer(int id) {
        playerModel.removePlayer(id);
        pcs.firePropertyChange("Player Removed", null, id);
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
        StatusBox.getInstance().message(Color.GREEN,"Round "+(roundModel.playedRounds()+1)+" started!");
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
        roundModel.setCountDown(Settings.getInstance().getSetting("roundDelay"));
    }

    /**
     * (Un)Pauses the game.
     */
    @Override
    public void switchPauseState() {
        RoundState roundState = getRoundState();
        if (gameState == GameState.ACTIVE && (roundState == RoundState.PLAYING
                || roundState == RoundState.PAUSED)) {
            if (getRoundState() == RoundState.PAUSED) {
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
        Player winner = playerModel.findRoundWinner();
        roundModel.endRound(winner);

        Color color = roundModel.getWinner().getColor();
        String message;
        if (winner != Player.NONE) {
            message = "Winner: Player "+roundModel.getWinner().getId()+" !";
        } else {
            message = "The game is a Tie, You're all losers!";
        }
        StatusBox.getInstance().message(color, message);
        if (Settings.getInstance().getSetting("numberOfRounds") 
                <= roundModel.playedRounds()) {
            endGame();
        }
    }

    private void endGame() {
        StatusBox.getInstance().message("Game over!");

        // Calculate statistics
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
            StatusBox.getInstance().message(player.getId() + " won " + playerWins.get(player) + " rounds!");
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
        pcs.addPropertyChangeListener(pl);
        playerModel.addPropertyChangeListener(pl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pl) {
        pcs.addPropertyChangeListener(pl);
        playerModel.addPropertyChangeListener(pl);
    }
}
