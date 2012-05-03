package model.visual;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import model.physics.IPhysicsHandler;
import model.physics.JMEPhysicsHandler;
import model.physics.PhysType;
import model.tools.Vector;
import model.visual.Unit;

/**
 * A class to represent a Battlefield.
 * @author Victor Lindhé
 * @modified johnnes
 */
public class Battlefield implements IVisualisable, PropertyChangeListener{
    private final Vector size;
    private final Vector pos = new Vector(0,0);
    private final IPhysicsHandler physHandler = new JMEPhysicsHandler();
    private final List<IMoveable> moveables = new LinkedList<IMoveable>();
    
    /**
     * Creates a Battlefield with default size (100,100) and an Item.
     */
    public Battlefield() {
        this(new Vector(100.0f,100.0f));
    }
    
    /**
     * Creates a Battlefield with a size x-wise and size y-wise.
     * @param xWidth 
     * @param yWidth 
     * @throws NumberFormatException
     */
    public Battlefield(final Vector size) throws NumberFormatException {
        if(size.getX() > 0 && size.getY() > 0) {
            this.size = new Vector(size);
        } else {
            throw new NumberFormatException("Size must be > 0");
        }
        
        physHandler.addPropertyChangeListener(this);
    }
    public void removeFromBattlefield(final IMoveable mov) {
        mov.removeFromView();
    }
    
    public void addToBattlefield(final IMoveable mov){
        if (moveables.contains(mov)) {
            throw new IllegalArgumentException("ERROR: We tried to add a moveable to battlefield that already exists: "+mov);
        }
        //init physcz
        physHandler.addToWorld(mov);

        this.moveables.add(mov);
    }
    
    public void update(final float tpf){
        final Iterator<IMoveable> iterator = moveables.iterator();
        while(iterator.hasNext()){
            final IMoveable next = iterator.next();
            next.update(tpf);
            this.physHandler.setRigidVelocity(next, next.getVelocity());
            this.physHandler.setRigidForce(next, next.getDirection(), next.getSpeed());
            this.physHandler.setRigidPosition(next, next.getPosition());
            if (next.getType() == PhysType.BOAT && this.isOutOfBounds(next.getPosition())) {
                this.doMagellanJourney(next);
            }
        }
        this.physHandler.update(tpf);

    }
    private void doMagellanJourney(final IMoveable moveable) {
        final Vector newPosition = moveable.getPosition();
        final Vector direction = moveable.getDirection();
        direction.mult(-1.0f);
        newPosition.add(direction);
        while (!isOutOfBounds(newPosition)) {
            newPosition.add(direction);
        }

        direction.mult(-1.0f);
        newPosition.add(direction);
        moveable.setPosition(newPosition);
    }
    /**
     * Returns a copy of the size Vector.
     * @return Vector
     */
    public Vector getSize() {
        return this.size;
    }
    
    public Vector getPosition() {
        return new Vector(this.pos);
    }
    
    
    private boolean isOutOfBounds(final Vector position) {
        return position.getX() < 0
                || position.getX() > this.size.getX()
                || position.getY() < 0
                || position.getY() > this.size.getY();
    }


    public void clear(){
        final Iterator<IMoveable> iterator = this.moveables.iterator();
        while(iterator.hasNext()){
            final IMoveable mov = iterator.next();
            if(mov.getClass() == Unit.class){
                this.hideMoveable(mov);
            } else {
                this.removeFromBattlefield(mov); // completely remove cannonballs. out boats will just be hidden because they will be reused.
                iterator.remove();
            }
        }
    }
    /**
     * Returns the position of the center.
     * @return Vector
     */
    public Vector getCenter() {
        return new Vector(this.size.getX()/2, this.size.getY()/2);
    }


    /**
     * Compares Battlefield to another Battlefield with respect to the size.
     * @param obj Another Battlefield
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Battlefield other = (Battlefield) obj;
        if (this.size != other.size && (this.size == null || !this.size.equals(other.size))) {
            return false;
        }
        if (this.pos != other.pos && (this.pos == null || !this.pos.equals(other.pos))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.size != null ? this.size.hashCode() : 0);
        hash = 37 * hash + (this.pos != null ? this.pos.hashCode() : 0);
        return hash;
    }

 
    public void propertyChange(final PropertyChangeEvent evt) {
        if ("Collision CannonBalls".equals(evt.getPropertyName())) {
            this.removeFromBattlefield((CannonBall) evt.getOldValue());
            this.removeFromBattlefield((CannonBall) evt.getNewValue());
        }
        if ("Collision Boats".equals(evt.getPropertyName())) {
            final Unit unit1 = (Unit) evt.getOldValue();
            final Vector newDir1 = this.physHandler.getRigidDirection(unit1);
            final float speed1 = this.physHandler.getRigidSpeed(unit1);

            newDir1.add(unit1.getDirection());
            unit1.setDirection(newDir1);
            unit1.setSpeed(speed1);

            final Unit unit2 = (Unit) evt.getNewValue();
            final Vector newDir2 = this.physHandler.getRigidDirection(unit2);
            final float speed2 = this.physHandler.getRigidSpeed(unit2);

            newDir2.add(unit2.getDirection());
            unit2.setDirection(newDir2);
            unit2.setSpeed(speed2);
        }
        if ("Collision CannonBallBoat".equals(evt.getPropertyName())) {
            boatHitByCannonBall((Unit) evt.getNewValue(), (CannonBall) evt.getOldValue());
        }

        if ("Collision BoatCannonBall".equals(evt.getPropertyName())) {
            boatHitByCannonBall((Unit) evt.getOldValue(), (CannonBall) evt.getNewValue());
        }
        if("CannonBall Created".equals(evt.getPropertyName())) {
            this.addToBattlefield((CannonBall)evt.getNewValue());
        }
    }
    

    private void boatHitByCannonBall(Unit boat, CannonBall cBall) {
        boat.damage(cBall.getDamage());
        this.removeFromBattlefield(cBall);
    }

    public void removeFromView() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Vector getStartingPosition(int playerID, Vector bfSize) {
        Vector upLeft = new Vector(bfSize);
        Vector downLeft = new Vector(upLeft.getX(), 0);
        Vector upRight = new Vector(0, upLeft.getX());
        Vector downRight = new Vector(15f, 15f);
        
        // We want the starting positions a bit more towards the center
        upLeft.add(new Vector(-15f, -15f));
        downLeft.add(new Vector(-15f, 15f));
        upRight.add(new Vector(15f, -15f));
        
        Vector position;
        switch (playerID) {
            case 0:
                position = new Vector(upLeft);
                break;
            case 1:
                position = new Vector(downRight);
                break;
            case 2:
                position = new Vector(downLeft);
                break;
            case 3:
                position = new Vector(upRight);
                break;
            default:
                throw new IllegalArgumentException("ERROR: Tried to get startingPos of invalid player with ID: "
                        + playerID);
        }
        return position;
    }

    public static Vector getStartingDir(int playerID) {
        Vector direction;
        switch (playerID) {
            case 0:
                direction = new Vector(-1, -1);
                break;
            case 1:
                direction = new Vector(1, 1);
                break;
            case 2:
                direction = new Vector(-1, 1);
                break;
            case 3:
                direction = new Vector(1, -1);
                break;
            default:
                throw new IllegalArgumentException("ERROR: Tried to get startingPos of invalid player with ID: "
                        + playerID);
        }
        return direction;
    }

    private void hideMoveable(IMoveable mov) {
        mov.hide();
    }
    
}
