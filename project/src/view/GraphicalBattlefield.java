package view;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 * Class to represent a graphical battlefield.
 */
public class GraphicalBattlefield {

    Geometry groundGeometry;
    
    public GraphicalBattlefield(Vector3f size, Vector3f position, AssetManager assetManager){
        
        Vector3f sizeVector = new Vector3f(size.x,size.y,size.z);
        
        Box groundShape = new Box(Vector3f.ZERO, sizeVector);

        groundGeometry = new Geometry("Box", groundShape);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        mat.setColor("Ambient", new ColorRGBA(255, 0, 0, 50));
        groundGeometry.setMaterial(mat);
        groundGeometry.setLocalTranslation(position);
    }

    public Geometry getGeometry() {
        return groundGeometry;
    }
}
