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
import com.jme3.scene.shape.Box;
import util.Util;

/**
 *
 * @author jnes
 */
public class GraphicalBattlefield {
    Geometry groundGeometry;
    public static final float BATTLEFIELD_THICKNESS =  1f;
    
    public GraphicalBattlefield(float size, Vector3f position, AssetManager assetManager){
        
        Vector3f sizeVector = new Vector3f(size,BATTLEFIELD_THICKNESS,size);
        
        // We want jmonkey to put origin in middle of battlefield
        position.subtract(Util.getCenterPosition(sizeVector)).setY(0);
        
        Box groundShape = new Box(sizeVector.getX(),sizeVector.getY(),sizeVector.getZ());
        
        groundGeometry = new Geometry("Box", groundShape);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        groundGeometry.setMaterial(mat);
        this.groundGeometry.setLocalTranslation(position);
        
    }
    public Geometry getGeometry(){
        return this.groundGeometry;
    }
    
}
