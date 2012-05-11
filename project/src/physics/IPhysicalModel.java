package physics;

/**
 *
 * @author atamon
 */
public interface IPhysicalModel extends ICollideable {
    public IPhysicalBody getPhysicalObject();
    public int getAcceleration();
}
