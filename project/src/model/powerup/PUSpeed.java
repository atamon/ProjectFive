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
        maxSpeed = 20;
        acceleration = 10000;
        lifeTime = 10;
    }

    private PUSpeed(PUSpeed that) {
        super(that);
    }
    
    public void update(final float tpf) {
        if(lifeTime <= 0){
            active = false;
        } else {
            lifeTime -= tpf;
        }
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    @Override
    public IPowerUp clone() {
        return new PUSpeed(this);
    }

}
