package model.powerup;

/**
 *
 * @author jnes
 */
public class PUSpeed extends AbstractPowerUp {

    private final String name = "Speed up";
    private final String message = "Sppeeeeeeeeeeeeeddddd";
    
    public PUSpeed(){
        super();
        this.maxSpeed = 10;
        this.acceleration = 15;
    }
    
    public void update(float tpf) {
        this.lifeTime -= tpf;
    }

    public String getMessage() {
        return this.message;
    }

    public String getName() {
        return this.name;
    }
    
}
