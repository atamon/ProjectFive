package model.visual;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import physics.JMEPhysicsHandler;
import math.Vector;
import physics.ICollideable;
import physics.IPhysicalModel;

/**
 * A class to represent a Battlefield.
 *
 * @author Victor Lindhé @modified johnnes
 */
public class Battlefield implements PropertyChangeListener, ICollideable {

    private final Vector size;
    private final Vector pos = new Vector(0, 0, 0);
    private final JMEPhysicsHandler physHandler = new JMEPhysicsHandler();
    private final List<IMoveable> moveables = new LinkedList<IMoveable>();
    private final List<IMoveable> removeBuffer = new LinkedList<IMoveable>();
    
    /**
     * Creates a Battlefield with default size (100,100) and an Item.
     */
    public Battlefield() {
        this(new Vector(100.0f, 1.0f, 100.0f));
    }

    /**
     * Creates a Battlefield with a size x-wise and size y-wise.
     *
     * @param xWidth
     * @param yWidth
     * @throws NumberFormatException
     */
    public Battlefield(final Vector size) throws NumberFormatException {
        if (size.getX() > 0 && size.getY() > 0 && size.getZ() > 0) {
            this.size = new Vector(size);
        } else {
            throw new NumberFormatException("Size must be > 0");
        }

        physHandler.addPropertyChangeListener(this);

        // Set up ocean floor
        physHandler.createGround(this.size, this);
    }

    public void addToBattlefield(final IMoveable mov) {
        if (moveables.contains(mov)) {
            throw new IllegalArgumentException("ERROR: We tried to add a moveable to battlefield that already exists: " + mov);
        }
        //Add to our physical world which controls movement
        physHandler.addToWorld(mov.getPhysicalObject());

        // Save it for keepsake and listen for removal
        mov.addPropertyChangeListener(this);
        this.moveables.add(mov);
    }

    public void removeFromBattlefield(final IMoveable mov) {
        this.physHandler.removeFromWorld(mov.getPhysicalObject());
        mov.removePropertyChangeListener(this);
        moveables.remove(mov);
    }
    
    public void update(final float tpf) {
        Iterator<IMoveable> iterator = moveables.iterator();
        while (iterator.hasNext()) {
            
            final IMoveable next = iterator.next();
            next.update(tpf);
            if (next.getClass() == Unit.class && this.isOutOfBounds(next.getPosition())) {
                this.doMagellanJourney(next);
            }
        }
        this.physHandler.update(tpf);
        
        clearRemoveBuffer();
    }

    private void clearRemoveBuffer(){
        Iterator<IMoveable> iterator  = this.removeBuffer.iterator();
        while (iterator.hasNext()){
            final IMoveable next = iterator.next();
            this.removeFromBattlefield(next);
        }
        this.removeBuffer.clear();
    }
    private void doMagellanJourney(final IMoveable moveable) {
        Vector moddedPos = new Vector(moveable.getPosition());
        moddedPos.setX(moddedPos.getX() % size.getX());
        moddedPos.setZ(moddedPos.getZ() % size.getZ());
        if (moddedPos.getX() < 0) {
            moddedPos.setX(size.getX());
        }
        if (moddedPos.getZ() < 0) {
            moddedPos.setZ(size.getZ());
        }

        moddedPos.setY(moveable.getPosition().getY());
        moveable.setPosition(moddedPos);
    }

    /**
     * Returns a copy of the size Vector.
     *
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
                || position.getZ() < 0
                || position.getZ() > this.size.getZ();
    }

    public void clearForNewRound() {
        final Iterator<IMoveable> iterator = this.moveables.iterator();
        while(iterator.hasNext()) {
            final IMoveable mov = iterator.next();
            // Keep units but remove everything else
            if (mov.getClass() == Unit.class) {
                mov.hide();
            } else {
                mov.announceRemoval();
            }
        }
    }

    /**
     * Returns the position of the center.
     *
     * @return Vector
     */
    public Vector getCenter() {
        return new Vector(this.size.getX() / 2, this.size.getY(), this.size.getZ() / 2);
    }

    /**
     * Compares Battlefield to another Battlefield with respect to the size.
     *
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
    
    public void collidedWith(ICollideable obj, float objImpactSpeed) {
        // Nothing to do here yet, all other objects handle collision with BF atm
    }

    public void propertyChange(final PropertyChangeEvent evt) {

        if ("CannonBall Created".equals(evt.getPropertyName())) {
            CannonBall cb = (CannonBall) evt.getNewValue();
            this.addToBattlefield(cb);
        }

        if ("CannonBall Removed".equals(evt.getPropertyName())) {
            CannonBall cb = (CannonBall) evt.getNewValue();
            this.removeBuffer.add(cb);
        }
        
        if ("Item Removed".equals(evt.getPropertyName())){
            Item item = (Item)(evt.getNewValue());
            this.removeBuffer.add(item);
        }
    }

    public static Vector getStartingPosition(int playerID, Vector bfSize) {
        float corrHeight = bfSize.getY() + 1.9f;
        Vector upLeft = new Vector(bfSize);
        Vector downLeft = new Vector(upLeft.getX(), upLeft.getY(), 0);
        Vector upRight = new Vector(0, upLeft.getY(), upLeft.getX());
        Vector downRight = new Vector(15f, upLeft.getY() + corrHeight, 15f);

        // We want the starting positions a bit more towards the center
        upLeft.add(new Vector(-15f, corrHeight, -15f));
        downLeft.add(new Vector(-15f, corrHeight, 15f));
        upRight.add(new Vector(15f, corrHeight, -15f));

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
                direction = new Vector(-1, 0, -1);
                break;
            case 1:
                direction = new Vector(1, 0, 1);
                break;
            case 2:
                direction = new Vector(-1, 0, 1);
                break;
            case 3:
                direction = new Vector(1, 0, -1);
                break;
            default:
                throw new IllegalArgumentException("ERROR: Tried to get startingPos of invalid player with ID: "
                        + playerID);
        }
        return direction;
    }
}
