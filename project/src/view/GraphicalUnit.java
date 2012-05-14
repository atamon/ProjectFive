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
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.noise.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * A class to hold a Mesh.
 *
 * @author Victor Lindhé
 */
public class GraphicalUnit extends GraphicalAbstract implements PropertyChangeListener {

    public GraphicalUnit(int id, ColorRGBA color,
            Vector3f pos,
            Vector3f dir,
            Vector3f size,
            AssetManager assetManager,
            Node blenderModel) {
        
        this.node = blenderModel;
        blenderModel.setLocalScale(size);
        Quaternion rot = new Quaternion();
        rot.lookAt(dir, Vector3f.UNIT_Y);
        System.out.println("" + this.node.getChildren().size());
        
        //We know that node only contains the model of the boat.
        Node boatModelNode = (Node) this.node.getChild(0);
        Geometry sailGeometry = (Geometry) boatModelNode.getChild(1);
        setSailColor(id, sailGeometry);        
        
        updatePosition(pos);
        updateRotation(rot);
    }
    
    public void propertyChange(PropertyChangeEvent pce) {
        
        if ("Physical Update".equals(pce.getPropertyName())) {
            Vector3f pos = (Vector3f) pce.getOldValue();
            Quaternion dir = (Quaternion) pce.getNewValue();
            
            updateRotation(dir);
            updatePosition(pos);
        }
        
        if ("Unit removed".equals(pce.getPropertyName())) {
            node.removeFromParent();
        }
    }
    
    private static void setSailColor(int id, Geometry sail) {
        switch(id) {
            case 0:
                sail.getMaterial().setColor("Ambient", ColorRGBA.Cyan);
                break;
            case 1:
                sail.getMaterial().setColor("Ambient", ColorRGBA.Magenta);
                break;
            case 2:
                sail.getMaterial().setColor("Ambient", ColorRGBA.Orange);
                break;
            case 3:
                sail.getMaterial().setColor("Ambient", ColorRGBA.Pink);
                break;
        }
    }
}
