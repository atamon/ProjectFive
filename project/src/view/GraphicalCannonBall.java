/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.tools.Vector;
import util.Util;

/**
 *
 * @author victorlindhe
 */
public class GraphicalCannonBall implements PropertyChangeListener {
    
    private Node node;
    private float yPosition;
    
    public GraphicalCannonBall(ColorRGBA color,
                         Vector3f pos,
                         Vector3f size,
                         AssetManager assetManager,
                         Node blenderModel) {

        Sphere ball = new Sphere(10, 10, 1.0f);
        Geometry ballGeo = new Geometry("CannonBall", ball);
        Material mat = new Material(assetManager, 
                                    "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        ballGeo.setMaterial(mat);
        
        this.node = new Node();
        this.node.attachChild(ballGeo);
        
        yPosition = GraphicalBattlefield.BATTLEFIELD_THICKNESS+size.z;
        this.updatePosition(pos.setY(yPosition));
    }
    
    public Vector3f getLocation() {
        return this.node.getLocalTranslation();
    }
    
    public Node getNode() {
        return this.node;
    }
    
    public void propertyChange(PropertyChangeEvent pce) {
        if("Updated Position".equals(pce.getPropertyName())) {
            this.updatePosition(Util.convertToMonkey3D((Vector)pce.getNewValue()).setY(yPosition));
        }
        if("Remove".equals(pce.getPropertyName()) && this.node.getParent() != null) {
            this.node.getParent().detachChild(node);
            
        }
    }

    private void updatePosition(Vector3f setY) {
        this.node.setLocalTranslation(setY);
    }
    
}
