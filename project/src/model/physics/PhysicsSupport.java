/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.physics;

import model.physics.IPhysicsHandler;
import model.physics.PhysType;
import model.tools.Vector;

/**
 *
 * @author jnes
 */
public class PhysicsSupport {
    
    private boolean collision=false;
    private float collisionTimeout;
    private final static float DEFAULT_TIMEOUT = 1f;
    private final boolean useTimeout;
    private final IPhysicsHandler physHandler;
    private final PhysType physType = PhysType.BOAT;

    public PhysicsSupport(IPhysicsHandler physHandler, boolean useTimeout){
        this(physHandler, useTimeout, DEFAULT_TIMEOUT);
        
    }
    public PhysicsSupport(IPhysicsHandler physHandler, boolean useTimeout, float timeout){
        this.physHandler = physHandler;
        this.useTimeout = useTimeout;
        this.collisionTimeout = timeout;
    }
    
    public void updateTimeout(float tpf){
        this.collisionTimeout -= tpf;
        if(this.collisionTimeout <= 0){
            this.collision=false;
        }
    }
    
    public void handleCollision(){
        this.collision = true;
        this.collisionTimeout = 1f;
    }
    public boolean isCollided(){
        return this.collision;
    }
    
    public PhysType getPhysType() {
        return this.physType;
    }
    
    public void setRigidPosition(int owner, Vector pos){
        this.physHandler.setRigidPosition(physType, owner, pos);
    }
    
    public Vector getRigidPosition(int owner){
        return this.physHandler.getRigidPosition(owner);
    }
    
    public void setRigidVelocity(int owner, Vector vel){
        this.physHandler.setRigidVelocity(physType, owner, vel);
    }

}
