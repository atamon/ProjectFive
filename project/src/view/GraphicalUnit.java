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
import com.jme3.scene.Spatial;
import controller.BlenderImporter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.tools.Vector;
import util.Util;

/**
 * A class to hold a Mesh.
 * @author Victor Lindhé
 */
public class GraphicalUnit implements PropertyChangeListener {

    public static final Vector3f GUNIT_SIZE = new Vector3f(1f, 1f, 1f);
    public static final String BLEND_PATH = "Blends/P5Ship_export.blend";
    private Node node;
    

    private float yPosition;
    public GraphicalUnit(ColorRGBA color,
                         Vector3f pos,
                         Vector3f dir,
                         float size,
                         AssetManager assetManager) {

        Node blenderModel = BlenderImporter.loadModel(assetManager, BLEND_PATH);
        this.node = blenderModel;
        
        yPosition = GraphicalBattlefield.BATTLEFIELD_THICKNESS+size;
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
        this.node.setLocalTranslation(pos);
    }

    private void updateRotation(Vector3f dir) {
        Quaternion newRotation = new Quaternion();
        newRotation.lookAt(dir, Vector3f.UNIT_Y);
        this.node.setLocalRotation(newRotation);
    }

    public void propertyChange(PropertyChangeEvent pce) {

        if (pce.getNewValue().getClass() == Vector.class) {
            Vector3f direction = Util.convertToMonkey3D((Vector) pce.getNewValue());

            if (pce.getPropertyName().equals("Updated Position")) {
                this.updatePosition(direction.setY(this.yPosition));
            }
            
            if (pce.getPropertyName().equals("Updated Direction")) {
                this.updateRotation(direction);
            }
        }
    }
}
