/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import util.Util;

/**
 * A class to hold a Mesh.
 * @author Victor Lindhé
 */
public class GraphicalUnit implements PropertyChangeListener {

    
    private Node node;
    private float yPosition;
    public static final float UNIT_HEIGHT = 1f;
//    private Node trackerNode;
    public GraphicalUnit(ColorRGBA color,
                         Vector3f pos,
                         Vector3f dir,
                         Vector3f size,
                         AssetManager assetManager,
                         Node blenderModel) {

        this.node = blenderModel;
//        this.trackerNode = trackerNode;
        yPosition = GraphicalBattlefield.BATTLEFIELD_THICKNESS+UNIT_HEIGHT;
        size.setY(UNIT_HEIGHT);
        blenderModel.setLocalScale(size);
        this.updatePosition(pos.setY(yPosition));
        this.updateRotation(dir);
    }

    public Node getNode() {
        return this.node;
    }

    public Vector3f getLocation() {
        return this.node.getLocalTranslation();
    }

    private void updatePosition(Vector3f pos) {
//        Vector3f trackMovement = node.getLocalTranslation().add(pos);
//        trackMovement = trackMovement.subtract(65f, 0, 65f);
        this.node.setLocalTranslation(pos);
//        this.trackerNode.move(trackMovement.x, 0, trackMovement.z);
    }

    private void updateRotation(Vector3f dir) {
        Quaternion newRotation = new Quaternion();
        newRotation.lookAt(dir, Vector3f.UNIT_Y);
        this.node.setLocalRotation(newRotation);
    }

    public void propertyChange(PropertyChangeEvent pce) {

        if ("Physical Update".equals(pce.getPropertyName())) {
            // Pos == old
            
            // Dir == new
            
            Vector3f pos = (Vector3f)pce.getOldValue();
            Vector3f dir = (Vector3f)pce.getNewValue();
            
            this.updatePosition(pos);
            this.updateRotation(dir);
        }
        
        if (pce.getNewValue() != null && pce.getNewValue().getClass() == Vector.class) {
            Vector3f direction = Util.convertToMonkey3D((Vector) pce.getNewValue());

            if (pce.getPropertyName().equals("Updated Position")) {
//                this.updatePosition(direction.setY(this.yPosition));
            }
            
            if (pce.getPropertyName().equals("Updated Direction")) {
//                this.updateRotation(direction);
            }
        }
    }
}
