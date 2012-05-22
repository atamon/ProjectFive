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
import java.beans.PropertyChangeListener;

/**
 * Class to represent a graphical cannonball.
 */
public class GraphicalCannonBall extends GraphicalAbstract implements PropertyChangeListener {
    
    private final AssetManager assetManager;
    
    public GraphicalCannonBall(ColorRGBA color,
                         Vector3f pos,
                         Vector3f size,
                         AssetManager assetManager,
                         Node blenderModel) {
        this.assetManager = assetManager;
        Sphere ball = new Sphere(10, 10, size.getX());
        Geometry ballGeo = new Geometry("CannonBall", ball);
        Material mat = new Material(assetManager, 
                                    "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        ballGeo.setMaterial(mat);
        
        node = new Node();
        node.attachChild(ballGeo);
        updatePosition(pos);
    }
}
