package physics;

/**
 *
 * @author atamon
 */
public interface ICollideable {

    public void collidedWith(ICollideable obj, float objImpactSpeed);
}
