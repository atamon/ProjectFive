package model;

/**
 *  Represents different game states.
 **/
public enum GameState {
    ACTIVE (1),
    INACTIVE (0),
    STATS (2);
    
    private final int state;
    
    private GameState(int state) {
        this.state = state;
    }
    
    private int getValue() {
        return state;
    }
}
