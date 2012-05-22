package physics;

/**
 * Interface for a collideable body.
 */
public interface ICollideable {

    public void collidedWith(ICollideable obj, float objImpactSpeed);
}
