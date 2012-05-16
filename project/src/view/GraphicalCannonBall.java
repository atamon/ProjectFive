/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import math.Vector;
import math.MonkeyConverter;

/**
 *
 * @author victorlindhe
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
        
        this.node = new Node();
        this.node.attachChild(ballGeo);
        this.updatePosition(pos);
    }
    
    public void propertyChange(PropertyChangeEvent pce) {
        if("Physical Update".equals(pce.getPropertyName())) {
            this.updatePosition((Vector3f)pce.getOldValue());
            this.updateRotation((Quaternion)pce.getNewValue());
        }
        if("CannonBall Removed".equals(pce.getPropertyName()) && this.node.getParent() != null) {
            this.node.getParent().detachChild(node);
        }
    }
}
