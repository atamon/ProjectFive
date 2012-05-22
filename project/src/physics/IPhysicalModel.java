package physics;

/**
 *  Interface for abstract entities holding physical bodies.
 */
public interface IPhysicalModel extends ICollideable {
    public IPhysicalBody getPhysicalObject();
    public int getAcceleration();
}
