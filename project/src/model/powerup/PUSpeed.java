package model.powerup;

/**
 *
 * @author jnes
 */
public final class PUSpeed extends AbstractPowerUp {

    private final String name = "Speed";
    private final String message = "Sppeeeeeeeeeeeeeddddd";
    
    public PUSpeed(){
        super();
        this.maxSpeed = 20;
        this.acceleration = 10;
        this.lifeTime = 10;
    }
    
    public void update(final float tpf) {
        if(lifeTime <= 0){
            this.isActive = false;
        } else {
            this.lifeTime -= tpf;
        }
    }

    public String getMessage() {
        return this.message;
    }

    public String getName() {
        return this.name;
    }

}
