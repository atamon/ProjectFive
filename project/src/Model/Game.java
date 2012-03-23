package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Game consisting of rounds and players that compete to win!
 * @author Anton Lindgren
 */
public class Game {
    
    // Instances
    private final Battlefield battlefield;
    private final List<Player> players;
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
    }
    
    /**
     * Creates and returns a list with the players for this game.
     * @param nOfPlayers Is the amount of players we create.
     * @return Returns an ArrayList of the players.
     */
    private List<Player> createPlayers(int nOfPlayers) {
        List<Player> list = new ArrayList<Player>();
        
        for (int i = 0; i<nOfPlayers; i++) {
            list.add(new Player(i+1));
        }
        return list;
    }
    
    /**
     * Start a new round.
     * Sets position of all players,
     * cleans up after (possible) previous round // TODO Anything else?
     */
    private void startRound() {
//        battlefield.positionPlayers();
    }
}
