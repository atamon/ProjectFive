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
 *
 * @author Victor Lindhé
 */
public class GraphicalUnit implements PropertyChangeListener {

    private Node node;
//    private Node trackerNode;

    public GraphicalUnit(ColorRGBA color,
            Vector3f pos,
            Vector3f dir,
            Vector3f size,
            AssetManager assetManager,
            Node blenderModel) {

        this.node = blenderModel;
        blenderModel.setLocalScale(size);
        Quaternion rot = new Quaternion();
        rot.lookAt(dir, Vector3f.UNIT_Y);
        
        this.updatePosition(pos);
        this.updateRotation(rot);
    }

    public Node getNode() {
        return this.node;
    }

    private void updatePosition(Vector3f pos) {
        this.node.setLocalTranslation(pos);
    }

    private void updateRotation(Quaternion rot) {
        this.node.setLocalRotation(rot);
    }

    public void propertyChange(PropertyChangeEvent pce) {

        if ("Physical Update".equals(pce.getPropertyName())) {
            Vector3f pos = (Vector3f) pce.getOldValue();
            Quaternion dir = (Quaternion) pce.getNewValue();
                        
            this.updateRotation(dir);
            this.updatePosition(pos);
        }
    }
}
