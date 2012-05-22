/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * A class to hold a Mesh.
 *
 * @author Victor Lindhé
 */
public class GraphicalUnit extends GraphicalAbstract implements PropertyChangeListener {
    
    private float depthSunk = 0;
    
    public GraphicalUnit(int id, ColorRGBA color,
            Vector3f pos,
            Vector3f dir,
            Vector3f size,
            AssetManager assetManager,
            Node blenderModel) {
        
        node = blenderModel;
        blenderModel.setLocalScale(size);
        Quaternion rot = new Quaternion();
        rot.lookAt(dir, Vector3f.UNIT_Y);
        
        //We know that node only contains the model of the boat.
        Node boatModelNode = (Node) this.node.getChild(0);
        Geometry sailGeometry = (Geometry) boatModelNode.getChild(1);
        setSailColor(color, sailGeometry);        
        
        updatePosition(pos);
        updateRotation(rot);
    }
    
    @Override
    public void updatePosition(Vector3f pos) {
        super.updatePosition(pos.subtract(0, depthSunk, 0));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        super.propertyChange(pce);
        
        if("Unit sinking".equals(pce.getPropertyName())) {
            depthSunk += (Float) pce.getNewValue() * 5;
        }
    }
    
    private static void setSailColor(final ColorRGBA color, final Geometry sail) {
        sail.getMaterial().setColor("Ambient", color);
    }
}
