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
        this.acceleration = 10000;
        this.lifeTime = 10;
    }

    private PUSpeed(PUSpeed that) {
        super(that);
    }
    
    public void update(final float tpf) {
        if(lifeTime <= 0){
            this.active = false;
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

    @Override
    public IPowerUp clone() {
        return new PUSpeed(this);
    }

}
