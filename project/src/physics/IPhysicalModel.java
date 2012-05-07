package physics;

/**
 *
 * @author atamon
 */
public interface IPhysicalModel {
    public IPhysicalBody getGameObject();
    public void collidedWith(IPhysicalModel obj, float objImpactSpeed);
    public int getAcceleration();
}
