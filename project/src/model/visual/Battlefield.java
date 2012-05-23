package model.visual;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import physics.JMEPhysicsHandler;
import math.Vector;
import model.settings.Settings;
import physics.ICollideable;
import physics.IPhysicsHandler;

/**
 * A class to represent a Battlefield.
 */
public class Battlefield implements PropertyChangeListener, ICollideable {

    private final Vector size;
    private final Vector pos = new Vector(0, 0, 0);
    private final IPhysicsHandler physHandler = new JMEPhysicsHandler();
    private final List<IMoveable> moveables = new LinkedList<IMoveable>();
    private final List<IMoveable> hiddenMoveables = new LinkedList<IMoveable>();
    private final List<IMoveable> removeBuffer = new LinkedList<IMoveable>();

    /**
     * Creates a Battlefield with default size (100,100) and an Item.
     */
    public Battlefield() {
        this(new Vector(120.0f, 1.0f, 120.0f));
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

        // Set up ocean floor
        physHandler.createGround(size, this);
    }

    public void addToBattlefield(final IMoveable mov) {
        if (moveables.contains(mov)) {
            removeFromBattlefield(mov);
        }
        //Add to our physical world which controls movement
        physHandler.addToWorld(mov.getPhysicalObject());

        // Save it for keepsake and listen for removal
        mov.addPropertyChangeListener(this);
        moveables.add(mov);
    }

    public void removeFromBattlefield(final IMoveable mov) {
        physHandler.removeFromWorld(mov.getPhysicalObject());
        mov.removePropertyChangeListener(this);
        moveables.remove(mov);
    }

    public void update(final float tpf) {
        clearRemoveBuffer();
        Iterator<IMoveable> iterator = moveables.iterator();
        while (iterator.hasNext()) {

            final IMoveable next = iterator.next();
            next.update(tpf);
            if (next.getClass() == Unit.class && isOutOfBounds(next.getPosition())) {
                doMagellanJourney(next);
            }
        }
        physHandler.update(tpf);

    }

    private void clearRemoveBuffer() {
        Iterator<IMoveable> iterator = removeBuffer.iterator();
        while (iterator.hasNext()) {
            final IMoveable next = iterator.next();
            removeFromBattlefield(next);
        }
        removeBuffer.clear();
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
        return size;
    }

    public Vector getPosition() {
        return new Vector(pos);
    }

    private boolean isOutOfBounds(final Vector position) {
        return position.getX() < 0
                || position.getX() > size.getX()
                || position.getZ() < 0
                || position.getZ() > size.getZ();
    }

    public void clearForNewRound() {
        for (IMoveable mov : hiddenMoveables) {
            mov.announceShow();
            addToBattlefield(mov);
        }
        hiddenMoveables.clear();

        final Iterator<IMoveable> iterator = moveables.iterator();
        while (iterator.hasNext()) {
            final IMoveable mov = iterator.next();
            // Keep units but remove everything else
            if (mov.getClass() != Unit.class) {
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
        return new Vector(size.getX() / 2, size.getY(), size.getZ() / 2);
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
        if (size != other.size && (size == null || !size.equals(other.size))) {
            return false;
        }
        if (pos != other.pos && (pos == null || !pos.equals(other.pos))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (size != null ? size.hashCode() : 0);
        hash = 37 * hash + (pos != null ? pos.hashCode() : 0);
        return hash;
    }

    @Override
	public void collidedWith(ICollideable obj, float objImpactSpeed) {
        // Nothing to do here yet, all other objects handle collision with BF atm
    }

    @Override
	public void propertyChange(final PropertyChangeEvent evt) {

        if ("CannonBall Created".equals(evt.getPropertyName())) {
            IMoveable cb = (IMoveable) evt.getNewValue();
            addToBattlefield(cb);
        }

        if ("Visual Removed".equals(evt.getPropertyName())) {
            IMoveable mov = (IMoveable) evt.getNewValue();
            removeBuffer.add(mov);
        }

        if ("Hide Moveable".equals(evt.getPropertyName())) {
            IMoveable mov = (IMoveable) evt.getNewValue();
            hiddenMoveables.add(mov);
            removeBuffer.add(mov);
        }
    }

    public static Vector getStartingPosition(int playerID, Vector bfSize) {
        float margin = 15f;
        float maxX = bfSize.getX();
        float height = bfSize.getY()+Settings.getInstance().getSetting("unitSize");
        float maxZ = bfSize.getZ();
        
        Vector upLeft = new Vector(maxX-margin,height,maxZ-margin);
        Vector downLeft = new Vector(maxX-margin, height, margin);
        Vector upRight = new Vector(margin, height, maxZ- margin);
        Vector downRight = new Vector(margin, height, margin);

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
