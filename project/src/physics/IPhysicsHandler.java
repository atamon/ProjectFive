package physics;

import math.Vector;

/**
 * Necessary methods for using a physics world.
 */
public interface IPhysicsHandler {

    void addToWorld(IPhysicalBody object);

    void createGround(Vector size, ICollideable model);

    void removeFromWorld(IPhysicalBody object);

    void update(float tpf);
    
}
