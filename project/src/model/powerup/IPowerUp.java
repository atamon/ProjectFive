
package model.powerup;

/**
 *
 * @author jnes
 */
public interface IPowerUp {
    public void update(final float tpf); // i.e. update lifetime
    public String getMessage();
    public String getName();

    public boolean isActive();

    public int getAcceleration();

    public int getHitPoints();

    public int getHitPointsMax();

    public float getLifeTime();

    public float getMaxSpeed();

    public int getRetardation();

    public int getSteerAngle();
    
    public void setActive(final boolean active);
        
}
