package model;

import model.visual.Battlefield;
import model.tools.Direction;
import model.tools.Vector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.visual.Unit;

/**
 * Represents a Game consisting of rounds and players that compete to win!
 * @author Anton Lindgren
 * @modified by John Hult, Victor Lindhé
 */
public class Game implements IGame {

    // Instances
    private final Battlefield battlefield;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final Map<Integer, Player> playerMap = new HashMap<Integer, Player>();
    private final int numberOfRounds;
    private List<Round> comingRounds;
    private Round currentRound;
    private boolean isRunning = false;

    /**
     * Create a game with given parameters.
     * A game consists of a number of rounds containing a given amount of players.
     * The Game class starts new rounds and keeps track of player-score and rules
     * set for this game and its rounds as well as speaks to controller and view. 
     * @param battlefield The battlefield passed from Model which we'll play on.
     */
    public Game(Battlefield battlefield) {
        this.battlefield = battlefield;
        this.numberOfRounds = 1; // TODO
        this.comingRounds = this.createRounds(numberOfRounds);
    }
    /**
     * Creates a Game with a default 100x100 Battlefield, 1 round and 1 player.
     */
    public Game() {
        this(new Battlefield());
    }

    public int getNbrOfRounds(){
        return this.numberOfRounds;
    }
    private Unit createUnit(int playerID){
        Vector position;
        Vector direction;
        float bf = battlefield.getSize();
        switch(playerID){
            case 0:
                position = new Vector(bf,bf);
                direction = new Vector(-1,-1);
                break;
            case 1: 
                position = new Vector(0, 0);
                direction = new Vector(1,1);
                break;
            case 2: 
                position = new Vector(bf, 0);
                direction = new Vector(1,-1);
                break;
            case 3: 
                position = new Vector(0, bf);
                direction = new Vector(-1,1);
                break;
            default:
                position = new Vector(0,0);
                direction = new Vector(1,1);
                break;
        }
        return new Unit(position, direction);
    }
    
    /**
     * Updates the running game. Gets called each frame
     * @param tpf Time since last update
     */
    public void update(float tpf) {
        if (isRunning) {
            for (Player player : this.playerMap.values() ){
                player.updateUnitPosition(tpf);
                if(this.isOutOfBounds(player.getUnitPosition())) {
                    this.doMagellanJourney(player);
                }
            }
        }
    }
    
    private boolean isOutOfBounds(Vector position) {
        float size = this.getBattlefieldSize();
        return position.getX() < 0 ||
               position.getX() > size ||
               position.getY() < 0 ||
               position.getY() > size;
    }
    
    private void doMagellanJourney(Player player) {
        Vector newPosition = new Vector(player.getUnitPosition());
        Vector direction = new Vector(player.getUnitDirection());
        
        direction.mult(-1.0f);
        newPosition.add(direction);
        while(!isOutOfBounds(newPosition)) {
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
    public Vector getBattlefieldPosition(){
        return battlefield.getPosition();
    }
    
    public Player getPlayer(int playerID) {
        Player player = this.playerMap.get(playerID);
        if (player == null){
            throw new IllegalArgumentException("ERROR getPlayer: player with id "
                    + playerID + " does not exist! ;/");
        }
        return player;
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

    public void createPlayer(int id) {
        if(playerMap.get(id) != null){
            System.out.println("Warning!: playerMap had object: " + playerMap.get(id) + " set to supplied key");
            throw new RuntimeException("AddPlayer: player with id: "+id+" does already exist! Sorry :(");
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
     * Start a new round.
     * Sets position of all players
     */
    @Override
    public void startRound() {
        // TODO Insert stastics-handling for each round here in the future maybe?
        this.currentRound = this.comingRounds.remove(0);
        
        
        
        // Uncomment when we want items
        //battlefield.addItem();
    }
    
    @Override
    public void placeUnit(int id, Vector vector) {
        this.playerMap.get(id).getUnit().setPosition(vector);
    }

    /**
     * Call when the round ends, ie one player is last man standing or the
     * clock runs out.
     * It delivers statistics from played round, sets score to the winner
     * and clears the battlefield.
     */
    public void endRound() {
        // TODO Add score for the winner here
        // Clear battlefield?
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
        this.isRunning = !this.isRunning;
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

}
