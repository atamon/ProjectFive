/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Box;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.tools.Vector;
import util.Util;

/**
 * A class to hold a Mesh.
 * @author Victor Lindhé
 */
public class GraphicalUnit implements PropertyChangeListener {

    private Geometry box;
    private float yPosition;
    public GraphicalUnit(ColorRGBA color,
                         Vector3f pos,
                         Vector3f dir,
                         float size,
                         AssetManager assetManager) {
        yPosition = GraphicalBattlefield.BATTLEFIELD_THICKNESS+size;
        Box boxShape = new Box(size,size,size);
        this.box = new Geometry("Box", boxShape);
        Material boxMat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setColor("Color", color);
        this.box.setMaterial(boxMat);
        
        this.updatePosition(pos.setY(yPosition));
        this.updateRotation(dir);
    }

    public Geometry getGeometry() {
        return this.box;
    }

    public Vector3f getLocation() {
        return this.box.getLocalTranslation();
    }

    private void updatePosition(Vector3f pos) {
        this.box.setLocalTranslation(pos);
    }

    private void updateRotation(Vector3f dir) {
        Quaternion newRotation = new Quaternion();
        newRotation.lookAt(dir, Vector3f.UNIT_Y);
        this.box.setLocalRotation(newRotation);
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
