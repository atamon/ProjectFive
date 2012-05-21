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
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author jnes
 */
public class GraphicalBottle extends GraphicalAbstract implements PropertyChangeListener{

    public GraphicalBottle(Vector3f pos,
                         Node blenderModel) {

        node = blenderModel;
        updatePosition(pos);
    }
    
    
    public void propertyChange(PropertyChangeEvent pce) {
        if("Physical Update".equals(pce.getPropertyName())) {
            updatePosition((Vector3f)pce.getOldValue());
            updateRotation((Quaternion)pce.getNewValue());
        }
        if("Bottle Removed".equals(pce.getPropertyName()) && node.getParent() != null) {
            node.getParent().detachChild(node);
        }
    }    
}
