package Model;

import Model.Round;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a Game consisting of rounds and players that compete to win!
 * @author Anton Lindgren
 * @modified by John Hult, Victor Lindhé
 */
public class Game {

    // Instances
    private final Battlefield battlefield;
    private final List<Player> players;
    private final int numberOfRounds;
    private List<Round> comingRounds;
    private Round currentRound;

    /**
     * Create a game with given parameters.
     * A game consists of a number of rounds containing a given amount of players.
     * The Game class starts new rounds and keeps track of player-score and rules
     * set for this game and its rounds. 
     * @param battlefield The battlefield passed from Model which we'll play on.
     * @param numberOfRounds Number of rounds to be played.
     * @param numberOfPlayers Number of players in this game.
     */
    public Game(Battlefield battlefield, int numberOfRounds, int numberOfPlayers) {
        this.battlefield = battlefield;
        this.players = this.createPlayers(numberOfPlayers);
        this.numberOfRounds = numberOfRounds;
        this.comingRounds = this.createRounds(numberOfRounds);
    }
    
    /**
     * Creates a Game with a default 100x100 Battlefield, 1 round and 1 player.
     */
    public Game() {
        this(new Battlefield(), 1, 1);
    }

    /**
     * Updates the running game. Gets called each frame
     */
    public void update() {
        
    }

    public void acceleratePlayer(int id, float tpf) {
        this.players.get(id-1).accelerateUnit(tpf);
    }
    
    /**
     * Creates a list of all the rounds this game includes
     * @param numberOfRounds Number of rounds
     */
    private List<Round> createRounds(int numberOfRounds) {
        List<Round> list = new LinkedList<Round>();

        for (int i = 0; i < numberOfRounds; i++) {
            Round round = new Round(); //TODO Maybe needs an argument?
            list.add(round);
        }
        return list;
    }

    /**
     * Creates and returns a list with the players for this game.
     * @param nOfPlayers Is the amount of players we create.
     * @return Returns an ArrayList of the players.
     */
    private List<Player> createPlayers(int nOfPlayers) {
        List<Player> list = new ArrayList<Player>();

        for (int i = 0; i < nOfPlayers; i++) {
            list.add(new Player(i + 1));
        }
        return list;
    }

    /**
     * Start a new round.
     * Sets position of all players
     */
    private void startRound() {
        // TODO Insert stastics-handling for each round here in the future maybe?
        currentRound = comingRounds.remove(0);
        battlefield.positionUnits();
        battlefield.addItem();
    }

    /**
     * Call when the round ends, ie one player is last man standing or the
     * clock runs out.
     * It delivers statistics from played round, sets score to the winner
     * and clears the battlefield.
     */
    private void endRound() {
        battlefield.removeItem();
        // TODO Add score for the winner here   
    }
    
    /*
     * Returns number of players.
     */
    public int getNbrOfPlayers() {
        return this.players.size();
    }
}
