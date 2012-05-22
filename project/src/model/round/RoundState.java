package model.round;

/**
 * Enumeration for round states.
 **/
public enum RoundState {
    NONE_EXISTANT (-1),
    PLAYING (0),
    PAUSED (1),
    POST (2);
    
    private final int state;
    
    private RoundState(int state) {
        this.state = state;
    }
}
