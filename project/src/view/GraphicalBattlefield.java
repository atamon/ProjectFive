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

/**
 *
 * @author jnes
 */
public class GraphicalBattlefield {
    Geometry groundGeometry;
    public GraphicalBattlefield(Vector3f size, AssetManager assetManager){
        Box groundShape = new Box(Vector3f.ZERO,
                size.getX(), size.getY(), size.getZ());
        groundGeometry = new Geometry("Box", groundShape);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        groundGeometry.setMaterial(mat);
        groundGeometry.setLocalTranslation(0f,-5f,0f);
    }
    public Geometry getGeometry(){
        return this.groundGeometry;
    }
    
}
