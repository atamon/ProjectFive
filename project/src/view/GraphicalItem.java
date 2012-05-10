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
public class GraphicalItem implements PropertyChangeListener{

    private Node node;
    
    public GraphicalItem(ColorRGBA color,
                         Vector3f pos,
                         Vector3f size,
                         AssetManager assetManager,
                         Node blenderModel) {

        Box box = new Box(Vector3f.ZERO, size);
        Geometry boxGeo = new Geometry("Item", box);
        Material mat = new Material(assetManager, 
                                    "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        boxGeo.setMaterial(mat);
        
        this.node = new Node();
        this.node.attachChild(boxGeo);
        this.updatePosition(pos);
        System.out.println("added item");
    }
    
    private void updatePosition(Vector3f pos) {
        this.node.setLocalTranslation(pos);
    }

    private void updateRotation(Quaternion rot) {
        this.node.setLocalRotation(rot);
    }
    
    public Node getNode() {
        return this.node;
    }
    
    public void propertyChange(PropertyChangeEvent pce) {
        if("Physical Update".equals(pce.getPropertyName())) {
            this.updatePosition((Vector3f)pce.getOldValue());
            this.updateRotation((Quaternion)pce.getNewValue());
        }
        if("Item Removed".equals(pce.getPropertyName()) && this.node.getParent() != null) {
            this.node.getParent().detachChild(node);
        }
    }    
}
