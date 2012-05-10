package physics;

/**
 *
 * @author atamon
 */
public interface IPhysicalModel {
    public IPhysicalBody getPhysicalObject();
    public void collidedWith(IPhysicalModel obj, float objImpactSpeed);
    public int getAcceleration();
}
