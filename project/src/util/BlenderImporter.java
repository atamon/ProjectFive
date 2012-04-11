/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.jme3.asset.AssetManager;
import com.jme3.asset.BlenderKey;
import com.jme3.asset.BlenderKey.LoadingResults;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.blender.BlenderModelLoader;
import java.util.List;

/**
 *
 * @author Anton Lindgren
 */
public class BlenderImporter {

    public static void registerBlender(AssetManager assetManager) {
        assetManager.registerLoader(BlenderModelLoader.class, "blend");
    }

    public static Node loadModel(AssetManager assetManager, String path) {

        BlenderKey key = new BlenderKey(path);
        Object obj = assetManager.loadAsset(key);
        if (!(obj instanceof Node)) {
            throw new RuntimeException("ERROR: BlenderImporter.loadModel()" + 
                                       " got invalid path, no Spatial loaded." +
                                       "Object recieved was of type: "
                                        + obj.getClass());
        }
        Node result = (Node) obj;
        return result;
    }
}
